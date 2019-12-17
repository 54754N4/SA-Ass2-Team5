package rmit.team5.visiderm.DAO.Intereface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rmit.team5.visiderm.Model.UserInfo.UserAccount;

@Repository
public interface IUserDAO extends JpaRepository<UserAccount, Long> {
    @Query("select account from UserAccount account where lower(account.username) like lower(?1)")
    Page<UserAccount> findMatchingUsername(String username, Pageable pageable);
}
