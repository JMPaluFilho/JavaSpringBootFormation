package clinic.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import clinic.api.domain.doctor.Doctor;
import clinic.api.domain.doctor.DoctorDetailsData;
import clinic.api.domain.doctor.DoctorPersistData;
import clinic.api.domain.doctor.DoctorRepository;
import clinic.api.domain.doctor.Specialty;
import clinic.api.builders.DoctorTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DoctorControllerTest {

  public static final String HATEOAS_LINKS_OBJECT = "_links";
  @Autowired private MockMvc mockMvc;
  @MockBean private DoctorRepository doctorRepository;
  @Autowired private JacksonTester<DoctorPersistData> doctorPersistDataJson;
  @Autowired private JacksonTester<DoctorDetailsData> doctorDetailsDataJson;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final DoctorTestDataBuilder doctorTestDataBuilder = new DoctorTestDataBuilder();

  @Test
  @DisplayName("Should return HTTP 400 when data is invalid.")
  @WithMockUser
  void createScenario1() throws Exception {
    // WHEN OR ACT
    var response = mockMvc.perform(post("/doctors")).andReturn().getResponse();
    // THEN OR ASSERT
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Should return HTTP 201 when data is valid.")
  @WithMockUser
  void createScenario2() throws Exception {
    // GIVEN OR ARRANGE
    var doctorPersistData =
        doctorTestDataBuilder.doctorPersistData(
            "Doctor", "doctor@mail.com", "123456", Specialty.CARDIOLOGY);
    var doctorDetailsData = doctorTestDataBuilder.getDoctorDetailsData(doctorPersistData);

    // WHEN OR ACT
    when(doctorRepository.save(any())).thenReturn(new Doctor(doctorPersistData));
    var response =
        mockMvc
            .perform(
                post("/doctors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(doctorPersistDataJson.write(doctorPersistData).getJson()))
            .andReturn()
            .getResponse();

    // THEN OR ASSERT
    var actualJson = removeLinks(response.getContentAsString());
    var expectedJson = doctorDetailsDataJson.write(doctorDetailsData).getJson();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(actualJson).isEqualTo(expectedJson);
  }

  private String removeLinks(String response) throws Exception {
    var root = (ObjectNode) objectMapper.readTree(response);
    root.remove(HATEOAS_LINKS_OBJECT);
    return objectMapper.writeValueAsString(root);
  }
}
