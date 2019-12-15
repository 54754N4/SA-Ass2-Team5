package rmit.team5.visiderm.DAO.Intereface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rmit.team5.visiderm.Model.PatientInfo.Patient;


@Repository
public interface IPatientDAO extends JpaRepository<Patient, Long> {
   // Page<Patient> getPatientList (Pageable pageable);
  //  Page<Patient> getGeneralPatientList (Pageable pageable);
    //            " or concat(p.lastName, ' ', p.firstName) like ?1 or concat(p.firstName, ' ', p.lastName) like ?1")
    @Query("select p from Patient p where p.firstName like concat(concat('%',?1),'%') or p.lastName like concat(concat('%',?1),'%')"
    +  " or concat(p.lastName, ' ', p.firstName) like ?1 or concat(p.firstName, ' ', p.lastName) like ?1")
    Page<Patient> findAllPatientWithName (String patientName, Pageable pageable);
}
