package rmit.team5.visiderm.DAO.Intereface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rmit.team5.visiderm.Model.VisitInfo.Visit;

@Repository
public interface IVisitDAO extends JpaRepository<Visit, Long> {

    @Query("select v from Visit v where v.patient.patientID = ?1")
    Page<Visit> getVisitByPatientID  (long patientID, Pageable pageable);
}
