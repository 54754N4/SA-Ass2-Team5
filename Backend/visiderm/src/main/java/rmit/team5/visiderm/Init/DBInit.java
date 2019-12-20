package rmit.team5.visiderm.Init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmit.team5.visiderm.DTO.RoleDTO;
import rmit.team5.visiderm.DTO.UserDTO;
import rmit.team5.visiderm.Service.Interface.IUserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBInit {

    private IUserService userService;

    @Autowired
    public DBInit(IUserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void initUsers() {
        UserDTO u0 = new UserDTO(),
            u1 = new UserDTO();
        u0.setUsername("derm0");
        u0.setPassword("derm0");
        u0.setEnable(true);
        u0.setUserRoleList(createRoleList(createRole("dermatologist")));
        userService.addUser(u0);
        u1.setUsername("doc0");
        u1.setPassword("doc0");
        u1.setEnable(true);
        u1.setUserRoleList(createRoleList(createRole("doctor")));
        userService.addUser(u1);
        UserDTO u3 = new UserDTO();
        u3.setUsername("general");
        u3.setPassword("general");
        u3.setEnable(true);
        u3.setUserRoleList(createRoleList(createRole("general")));
        userService.addUser(u3);
    }

    private static RoleDTO createRole(String name) {
        RoleDTO role = new RoleDTO();
        role.setName(name);
        return role;
    }

    private static List<RoleDTO> createRoleList(RoleDTO... givenRoles) {
        List<RoleDTO> roles = new ArrayList<>();
        for (RoleDTO role : givenRoles)
            roles.add(role);
        return roles;
    }
}
