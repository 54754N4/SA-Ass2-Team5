package rmit.team5.visiderm.DAO.Intereface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rmit.team5.visiderm.Model.UserInfo.UserRole;

@Repository
public interface IRoleDAO extends JpaRepository<UserRole, Long> {
    @Query("select r from UserRole r where r.account.id = ?1")
    Page<UserRole> getUserRoles(long id, Pageable pageable);

    @Query("select distinct r.name from UserRole r")
    Page<UserRole> getUserRoles(Pageable pageable);
}
