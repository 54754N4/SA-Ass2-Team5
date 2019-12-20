package rmit.team5.visiderm.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmit.team5.visiderm.DAO.Intereface.IRoleDAO;
import rmit.team5.visiderm.DAO.Intereface.IUserDAO;
import rmit.team5.visiderm.DTO.RoleDTO;
import rmit.team5.visiderm.DTO.UserDTO;
import rmit.team5.visiderm.Model.UserInfo.UserAccount;
import rmit.team5.visiderm.Model.UserInfo.UserRole;
import rmit.team5.visiderm.Service.Interface.IUserService;

import javax.management.relation.Role;
import java.util.*;

@Service
public class UserService implements IUserService {
    private static final int PAGE_LIMIT = 10;
    private final IUserDAO userDAO;
    private final IRoleDAO roleDAO;

    @Autowired
    public UserService(IUserDAO userDAO, IRoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public boolean addUser(UserDTO userDTO) {
        try {
            userDAO.saveAndFlush(copyFromDTO(userDTO, new UserAccount()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(UserDTO userDTO, long ID) {
        Optional<UserAccount> userRequested = userDAO.findById(ID);
        if (userRequested.isPresent()) {
            userDAO.saveAndFlush(copyFromDTO(userDTO, userRequested.get()));
            return true;
        } return false;
    }

    private static UserAccount copyFromDTO(UserDTO userDTO, UserAccount user) {
        user.setUsername(userDTO.getUsername().trim());
        user.setPassword(userDTO.getPassword().trim());
        user.setEnable(userDTO.getEnable());
        for (RoleDTO roleDTO : userDTO.getUserRoleList()) {
            UserRole role = new UserRole();
            role.setName(roleDTO.getName());
            user.addUserRole(role);
        }
        return user;
    }

    @Override
    public List<RoleDTO> validate(String username, String password) {
        Pageable pageable = PageRequest.of(0, PAGE_LIMIT);
        Page<UserAccount> page = userDAO.findMatchingUsername(username.trim(), pageable);
        List<UserAccount> accounts = page.getContent();
        for (UserAccount account : accounts)
            if (account.getPassword().equals(password.trim()))
                return convertToDTO(account.getUserRoleList());
        return null;
    }

    private List<RoleDTO> convertToDTO(List<UserRole> roles) {
        List<RoleDTO> dtos = new ArrayList<>();
        for (UserRole role : roles) {
            RoleDTO dto = new RoleDTO();
            dto.setName(role.getName());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public UserAccount getUser(long id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public HashMap<String, Object> getRoles(int page) {
        Pageable pageable = PageRequest.of(page-1, PAGE_LIMIT);
        Page<String> pagedResults = roleDAO.getUserRoles(pageable);
        List<HashMap<String, Object>> results = new ArrayList<>();
        for (String role : pagedResults.getContent())
            results.add(createNameHashMap(role));
        return createQueryResultHashMap(results, pagedResults);
    }

    @Override
    public HashMap<String, Object> getUserRoles(long id, int page) {
        Pageable pageable = PageRequest.of(page-1, PAGE_LIMIT);
        Page<UserRole> pagedResults = roleDAO.getUserRoles(id, pageable);
        List<HashMap<String, Object>> results = new ArrayList<>();
        for (UserRole role : pagedResults.getContent())
            results.add(createRoleHashMap(role));
        return createQueryResultHashMap(results, pagedResults);
    }

    private static HashMap<String, Object> createQueryResultHashMap(List<?> data, Page<?> page) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalPage", page.getTotalPages());
        result.put("currentPage", page.getPageable().getPageNumber());
        return result;
    }

    private static HashMap<String, Object> createNameHashMap(String value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", value);
        return map;
    }

    private static HashMap<String, Object> createRoleHashMap(UserRole role) {
        return createNameHashMap(role.getName());
    }
}
