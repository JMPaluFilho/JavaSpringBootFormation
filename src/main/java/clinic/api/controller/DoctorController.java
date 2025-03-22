package clinic.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import clinic.api.domain.doctor.Doctor;
import clinic.api.domain.doctor.DoctorDetailsData;
import clinic.api.domain.doctor.DoctorListData;
import clinic.api.domain.doctor.DoctorPersistData;
import clinic.api.domain.doctor.DoctorRepository;
import clinic.api.domain.doctor.DoctorUpdatedData;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

  private final DoctorRepository doctorRepository;

  public DoctorController(DoctorRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }

  @PostMapping
  @Transactional
  public ResponseEntity<EntityModel<DoctorDetailsData>> create(
      @RequestBody @Valid DoctorPersistData doctorPersistData, UriComponentsBuilder uriBuilder) {
    var doctor = new Doctor(doctorPersistData);
    doctorRepository.save(doctor);
    var doctorDetails = new DoctorDetailsData(doctor);

    var resource =
        EntityModel.of(
            doctorDetails,
            linkTo(methodOn(DoctorController.class).getDetails(doctor.getId())).withSelfRel(),
            linkTo(DoctorController.class).withRel("doctors"));

    var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

    return ResponseEntity.created(uri).body(resource);
  }

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<DoctorListData>>> listAll(
      @PageableDefault(sort = {"name"}) Pageable pageable,
      PagedResourcesAssembler<DoctorListData> assembler) {
    var page = doctorRepository.findAllByIsActiveTrue(pageable).map(DoctorListData::new);
    var pagedModel = assembler.toModel(page);

    return ResponseEntity.ok(pagedModel);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<DoctorDetailsData> update(
      @RequestBody @Valid DoctorUpdatedData doctorUpdatedData) {
    var doctor = doctorRepository.getReferenceById(doctorUpdatedData.id());
    doctor.updateData(doctorUpdatedData);

    return ResponseEntity.ok(new DoctorDetailsData(doctor));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    var doctor = doctorRepository.getReferenceById(id);
    doctor.delete();

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DoctorDetailsData> getDetails(@PathVariable Long id) {
    var doctor = doctorRepository.getReferenceById(id);
    return ResponseEntity.ok(new DoctorDetailsData(doctor));
  }
}
