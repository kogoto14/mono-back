package jp.co.monocrea.user.repository.dto;

import java.time.LocalDateTime;

import jp.co.monocrea.user.repository.table.UserTable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDetailDto from(UserTable userTable) {
        return new UserDetailDto(
            userTable.getId(),
            userTable.getName(),
            userTable.getEmail(),
            userTable.getPhone(),
            userTable.getAddress(),
            userTable.getCreatedAt(),
            userTable.getUpdatedAt()
        );
    }
}
