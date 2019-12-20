package rmit.team5.visiderm.Service.Interface;

import rmit.team5.visiderm.DTO.PatientDTO;
import rmit.team5.visiderm.DTO.RoleDTO;
import rmit.team5.visiderm.DTO.UserDTO;
import rmit.team5.visiderm.Model.UserInfo.UserAccount;

import java.util.HashMap;
import java.util.List;

public interface IUserService {

    List<RoleDTO> validate(String username, String password);
    UserAccount getUser(long id);
    HashMap<String, Object> getRoles(int page);
    HashMap<String, Object> getUserRoles(long id, int page);

    boolean addUser(UserDTO userDTO);
    boolean updateUser(UserDTO userDTO, long ID);
}
