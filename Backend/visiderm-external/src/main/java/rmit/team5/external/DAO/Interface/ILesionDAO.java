package rmit.team5.external.DAO.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import rmit.team5.visiderm.Model.LesionInfo.Lesion;

public interface ILesionDAO extends JpaRepository<Lesion, Long> {

}
