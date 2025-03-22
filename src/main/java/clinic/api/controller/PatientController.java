package clinic.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import clinic.api.domain.patient.Patient;
import clinic.api.domain.patient.PatientDetailsData;
import clinic.api.domain.patient.PatientListDate;
import clinic.api.domain.patient.PatientPersistData;
import clinic.api.domain.patient.PatientRepository;
import clinic.api.domain.patient.PatientUpdatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

  private final PatientRepository patientRepository;

  public PatientController(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  @PostMapping
  @Transactional
  public ResponseEntity<PatientDetailsData> create(
      @RequestBody @Valid PatientPersistData patientPersistData, UriComponentsBuilder uriBuilder) {
    var patient = new Patient(patientPersistData);
    patientRepository.save(patient);
    var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

    return ResponseEntity.created(uri).body(new PatientDetailsData(patient));
  }

  @GetMapping
  public ResponseEntity<Page<PatientListDate>> listAll(
      @PageableDefault(sort = {"name"}) Pageable pageable) {
    var page = patientRepository.findAllByIsActiveTrue(pageable).map(PatientListDate::new);

    return ResponseEntity.ok(page);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<PatientDetailsData> update(
      @RequestBody @Valid PatientUpdatedDate patientUpdatedDate) {
    var patient = patientRepository.getReferenceById(patientUpdatedDate.id());
    patient.updateData(patientUpdatedDate);

    return ResponseEntity.ok(new PatientDetailsData(patient));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    var patient = patientRepository.getReferenceById(id);
    patient.delete();

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<PatientDetailsData> getDetails(@PathVariable Long id) {
    var patient = patientRepository.getReferenceById(id);
    return ResponseEntity.ok(new PatientDetailsData(patient));
  }
}
