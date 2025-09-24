package jp.co.monocrea.user.resource.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jp.co.monocrea.user.service.command.UserCreateCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank @Size(max = 255)
    private String name;
    @NotBlank @Email @Size(max = 255)
    private String email;
    @NotBlank @Pattern(regexp = "^[0-9\\-+() ]{7,20}$")
    private String phone;
    @NotBlank @Size(max = 255)
    private String address;

    public UserCreateCommand convertToUserCreateCommand() {
        return new UserCreateCommand(name, email, phone, address);
    }
}
