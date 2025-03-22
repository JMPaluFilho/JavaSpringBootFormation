package clinic.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import clinic.api.domain.appointment.AppointmentDetailsData;
import clinic.api.domain.appointment.AppointmentSchedule;
import clinic.api.domain.appointment.AppointmentScheduleData;
import clinic.api.domain.doctor.Specialty;
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
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private AppointmentSchedule appointmentScheduleMock;
  @Autowired private JacksonTester<AppointmentScheduleData> appointmentScheduleDataJson;
  @Autowired private JacksonTester<AppointmentDetailsData> appointmentDetailsDataJson;

  @Test
  @DisplayName("Should return HTTP 400 when data is invalid.")
  @WithMockUser
  void scheduleScenario1() throws Exception {
    // WHEN OR ACT
    var response = mockMvc.perform(post("/appointments")).andReturn().getResponse();
    // THEN OR ASSERT
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Should return HTTP 200 when data is valid.")
  @WithMockUser
  void scheduleScenario2() throws Exception {
    // GIVEN OR ARRANGE
    var date = LocalDateTime.now().plusHours(1);
    var specialty = Specialty.CARDIOLOGY;
    var appointmentDetailsData = new AppointmentDetailsData(null, 1L, 1L, date);
    var appointmentScheduleData = new AppointmentScheduleData(1L, 1L, date, specialty);

    // WHEN OR ACT
    when(appointmentScheduleMock.schedule(appointmentScheduleData))
        .thenReturn(appointmentDetailsData);
    var response =
        mockMvc
            .perform(
                post("/appointments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(appointmentScheduleDataJson.write(appointmentScheduleData).getJson()))
            .andReturn()
            .getResponse();

    // THEN OR ASSERT
    var expectedJson = appointmentDetailsDataJson.write(appointmentDetailsData).getJson();
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(expectedJson);
  }
}
