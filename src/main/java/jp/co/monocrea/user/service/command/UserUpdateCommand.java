package jp.co.monocrea.user.service.command;

import java.time.LocalDateTime;

import jp.co.monocrea.user.repository.table.UserTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateCommand {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public UserTable convertToUserTable() {
        UserTable userTable = new UserTable();
        userTable.setId(id);
        userTable.setName(name);
        userTable.setEmail(email);
        userTable.setPhone(phone);
        userTable.setAddress(address);
        userTable.setUpdatedAt(LocalDateTime.now());
        return userTable;
    }
}
