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
    public boolean validate(String username, String password) {
        Pageable pageable = PageRequest.of(0, PAGE_LIMIT);
        Page<UserAccount> page = userDAO.findMatchingUsername(username.trim(), pageable);
        List<UserAccount> accounts = page.getContent();
        for (UserAccount account : accounts)
            if (account.getPassword().equals(password.trim()))
                return true;
        return false;
    }

    @Override
    public UserAccount getUser(long id) {
        Optional<UserAccount> result = userDAO.findById(id);
        if (result.isPresent()) return result.get();
        else return null;
    }

    @Override
    public HashMap<String, Object> getRoles(int page) {
        Pageable pageable = PageRequest.of(page-1, PAGE_LIMIT);
        Page<UserRole> pagedResults = roleDAO.getUserRoles(pageable);
        List<HashMap<String, Object>> results = new ArrayList<>();
        for (UserRole role : pagedResults.getContent())
            results.add(createRoleHashMap(role));
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

    private static HashMap<String, Object> createRoleHashMap(UserRole role) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", role.getName());
        return map;
    }
}
