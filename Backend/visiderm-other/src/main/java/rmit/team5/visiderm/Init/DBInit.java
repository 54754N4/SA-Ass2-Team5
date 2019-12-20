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
        u0.setUsername("derm1");
        u0.setPassword("derm1");
        u0.setEnable(true);
        u0.setUserRoleList(createRoleList(createRole("dermatologist")));
        userService.addUser(u0);
        u1.setUsername("doc1");
        u1.setPassword("doc1");
        u1.setEnable(true);
        u1.setUserRoleList(createRoleList(createRole("doctor")));
        userService.addUser(u1);
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
