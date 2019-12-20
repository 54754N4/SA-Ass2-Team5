package rmit.team5.visiderm.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CredentialsDTO {

    @NotBlank(message = "username cannot be blank")
    @NotNull(message = "username cannot be null")
    private String username;

    @NotBlank(message = "password cannot be blank")
    @NotNull(message = "password cannot be null")
    private String password;

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
}
