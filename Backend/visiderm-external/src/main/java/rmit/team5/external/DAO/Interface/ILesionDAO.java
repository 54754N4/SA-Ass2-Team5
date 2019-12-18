package rmit.team5.external.DAO.Interface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rmit.team5.external.Model.LesionInfo.Lesion;

@Repository
public interface ILesionDAO extends JpaRepository<Lesion, Long> {
    @Query("select lesion from Lesion lesion where lesion.visitID like ?1")
    Page<Lesion> getLesionsOfVisit(String visitID, Pageable pageable);

    @Query("select lesion from Lesion lesion")
    Page<Lesion> getLesions(Pageable pageable);

    @Query("select lesion from Lesion lesion order by date(timeTaken) desc")
    Page<Lesion> getLesionsByDateDesc(Pageable pageable);

    @Query("select lesion from Lesion lesion order by date(timeTaken)")
    Page<Lesion> getLesionsByDateAsc(Pageable pageable);

    @Query("select lesion from Lesion lesion order by size desc")
    Page<Lesion> getLesionsBySizeDesc(Pageable pageable);

    @Query("select lesion from Lesion lesion order by size")
    Page<Lesion> getLesionsBySizeAsc(Pageable pageable);

}
