package rmit.team5.visiderm.Model.UserInfo;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(name = "account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountID")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enable;     // what is this boolean for btw ? =o i saw in class diagram so i added it but i'm confused

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade({SAVE_UPDATE, MERGE})
    private List<UserRole> userRoleList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }
}
