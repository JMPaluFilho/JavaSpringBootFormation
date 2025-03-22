package clinic.api.domain.doctor;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

  Page<Doctor> findAllByIsActiveTrue(Pageable pageable);

  @Query(
      """
      select d
      from Doctor d
      where d.isActive = true
      and d.specialty = :specialty
      and d.id not in(
        select a.doctor.id
        from Appointment a
        where a.appointmentDate = :date
        and a.cancelReason is null
      )
      order by rand()
      limit 1
      """)
  Optional<Doctor> getRandomDoctorWithoutScheduleOnDate(Specialty specialty, LocalDateTime date);

  @Query(
      """
      select d.isActive
      from Doctor d
      where d.id = :id
      """)
  Boolean findIsActiveById(Long id);
}
