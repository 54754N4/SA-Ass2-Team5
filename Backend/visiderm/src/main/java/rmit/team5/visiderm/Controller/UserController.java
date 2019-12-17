package rmit.team5.visiderm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmit.team5.visiderm.DTO.CredentialsDTO;
import rmit.team5.visiderm.DTO.UserDTO;
import rmit.team5.visiderm.Service.Interface.IUserService;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private static final String MESSAGE = "message";
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/validate")
    public ResponseEntity<?> validateCredentials(@Valid @RequestBody CredentialsDTO credentials) {
        if (userService.validate(credentials.getUsername(), credentials.getPassword()))
            return okResponse();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(createMessage("Invalid username or password"));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody UserDTO userDTO) {
        if (userService.addUser(userDTO)) return okResponse();
        return badResponse();
    }

    @PutMapping(path = "/update/{ID}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long ID) {
        if (ID != null && userService.updateUser(userDTO, ID)) return okResponse();
        return badResponse();
    }

    @GetMapping(path = "/roles")
    public ResponseEntity<?> getRoles(@RequestParam(required = false, defaultValue = "1") Integer page) {
        return ResponseEntity.ok(userService.getRoles(page));
    }

    @GetMapping(path = "/roles/{ID}")
    public ResponseEntity<?> getUserRoles(@RequestParam(required = false, defaultValue = "1") Integer page, @PathVariable Long ID) {
        return ResponseEntity.ok(userService.getUserRoles(ID, page));
    }

    private static HashMap<String, String> createMessage(String msg) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MESSAGE, msg);
        return map;
    }

    private static HashMap<String, String> okMessage() {
        return createMessage("success");
    }

    private static ResponseEntity<?> okResponse() {
        return ResponseEntity.ok(okMessage());
    }

    private static ResponseEntity<?> badResponse() {
        return ResponseEntity.badRequest().body("Bad request");
    }
}
