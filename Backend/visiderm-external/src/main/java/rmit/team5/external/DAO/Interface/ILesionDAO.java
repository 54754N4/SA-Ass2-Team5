package rmit.team5.external.DAO.Interface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rmit.team5.external.Model.LesionInfo.Lesion;

import java.util.List;

@Repository
public interface ILesionDAO extends JpaRepository<Lesion, Long> {

    @Query("select lesion from Lesion lesion where lesion.visitID like ?1")
    Page<Lesion> getLesionsOfVisit(String visitID, Pageable pageable);

    @Query("select leision from Lesion leision where leision.visitID = ?1 AND leision.location = ?2 " +
            "ORDER BY leision.date desc, leision.timeTaken desc ")
    List<Lesion> getLesionListOfLocationVisit (String visitID, String location);

    @Query("select l from Lesion l where " +
            "concat(l.size, '') like %?1% " +   // converts the char to str pre-comparison
            "or l.location like %?1% " +
            "or l.status like %?1% " +
            "or l.doctorNote like %?1%")
    Page<Lesion> getLesionsMatching(String keyword, Pageable pageable);

}
