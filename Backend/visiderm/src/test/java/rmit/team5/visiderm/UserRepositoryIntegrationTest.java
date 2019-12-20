package rmit.team5.visiderm;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import rmit.team5.visiderm.DAO.Intereface.IRoleDAO;
import rmit.team5.visiderm.DAO.Intereface.IUserDAO;
import rmit.team5.visiderm.DTO.RoleDTO;
import rmit.team5.visiderm.DTO.UserDTO;
import rmit.team5.visiderm.Model.UserInfo.UserAccount;
import rmit.team5.visiderm.Model.UserInfo.UserRole;
import rmit.team5.visiderm.Service.Interface.IUserService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserRepositoryIntegrationTest {
    private static final int RESULTS = 100;
    private static final String USERNAME = "user", PASSWORD = "pass", ROLE = "role";

    @Autowired private IUserDAO userDAO;
    @Autowired private IRoleDAO roleDAO;
    @Autowired private IUserService userService;

    @Test
    public void whenAddedUser_thenReturnUser() {
        UserDTO newUser = new UserDTO();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);
        newUser.setEnable(true);
        newUser.setUserRoleList(createRoleList());
        userService.addUser(newUser);
        Page<UserAccount> results = userDAO.findMatchingUsername(USERNAME, getPageable());
        boolean found = false;
        for (UserAccount account : results.getContent())
            if (account.getUsername().equals(USERNAME))
                found = true;
        assertThat(found).isTrue();
        List<UserRole> roles = roleDAO.findAll();  // get all created roles
        found = false;
        for (UserRole role : roles)
            if (role.getName().equals(ROLE))
                found = true;
        assertThat(found).isTrue();
    }

    private static List<RoleDTO> createRoleList() {
        List<RoleDTO> roles = new ArrayList<>();
        RoleDTO role = new RoleDTO();
        role.setName(ROLE);
        roles.add(role);
        return roles;
    }

    private static Pageable getPageable() {
        return PageRequest.of(0, RESULTS);
    }
}
