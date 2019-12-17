package rmit.team5.visiderm.DTO;

import javax.validation.constraints.NotBlank;

public class RoleDTO {

    @NotBlank(message = "Role name cannot be blank")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
