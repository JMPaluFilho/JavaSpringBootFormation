package clinic.api.controller;

import clinic.api.domain.appointment.AppointmentCancelData;
import clinic.api.domain.appointment.AppointmentSchedule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import clinic.api.domain.appointment.AppointmentDetailsData;
import clinic.api.domain.appointment.AppointmentScheduleData;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

  private final AppointmentSchedule appointmentSchedule;

  public AppointmentController(AppointmentSchedule appointmentSchedule) {
    this.appointmentSchedule = appointmentSchedule;
  }

  @PostMapping
  @Transactional
  public ResponseEntity<AppointmentDetailsData> schedule(
      @RequestBody @Valid AppointmentScheduleData appointmentScheduleData) {
    var appointmentDetailsData = appointmentSchedule.schedule(appointmentScheduleData);
    return ResponseEntity.ok(appointmentDetailsData);
  }

  @DeleteMapping
  @Transactional
  public ResponseEntity<Void> cancel(
      @RequestBody @Valid AppointmentCancelData appointmentCancelData) {
    appointmentSchedule.cancel(appointmentCancelData);
    return ResponseEntity.noContent().build();
  }
}
