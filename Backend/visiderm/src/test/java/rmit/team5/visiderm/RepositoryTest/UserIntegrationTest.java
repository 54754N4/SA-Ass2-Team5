package rmit.team5.visiderm.RepositoryTest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTest {
    private static final int RESULTS = 100;
    private static final String USERNAME = "user", PASSWORD = "pass", ROLE = "role";

    private static boolean createdDefaultUser = false;

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IRoleDAO roleDAO;
    @Autowired
    private IUserService userService;

    @Before
    public void verifyDefaultUserCreated() {
        // Create and store new user
        if (!createdDefaultUser) {
            userService.addUser(createDefaultUser());
            createdDefaultUser = true;
        }
    }

    /* User repository test case */

    @Test
    public void whenAddedUser_thenVerifyCreations() {
        // Verify that account exists
        Page<UserAccount> results = userDAO.findMatchingUsername(USERNAME, getPageable());
        boolean found = false;
        for (UserAccount account : results.getContent())
            if (account.getUsername().equals(USERNAME))
                found = true;
        assertThat(found).isTrue();
        // Verify that role also exists
        List<UserRole> roles = roleDAO.findAll();  // get all created roles
        found = false;
        for (UserRole role : roles)
            if (role.getName().equals(ROLE))
                found = true;
        assertThat(found).isTrue();
    }

    /* User service test cases */

    @Test
    public void whenValidated_thenReturnsRoles() {
        List<RoleDTO> roles = userService.validate(USERNAME, PASSWORD);
        boolean found = false;
        for (RoleDTO role : roles)
            if (role.getName().equals(ROLE))
                found = true;
        assertThat(found).isTrue();
    }

    @Test
    public void whenRequestCreatedRoles_thenRoleFound() {
        HashMap<String, Object> dict = userService.getRoles(1);
        List<HashMap<String, Object>> rolesMap = (List<HashMap<String, Object>>) dict.get("data");
        boolean found = false;
        for (HashMap<String, Object> role : rolesMap)
            if (role.get("name").equals(ROLE))
                found = true;
        assertThat(found).isTrue();
    }

    /* Convenience methods */

    private static UserDTO createDefaultUser() {
        UserDTO newUser = new UserDTO();
        newUser.setUsername(USERNAME);
        newUser.setPassword(PASSWORD);
        newUser.setEnable(true);
        newUser.setUserRoleList(createRoleList());
        return newUser;
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
