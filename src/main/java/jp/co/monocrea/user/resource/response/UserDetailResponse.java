package jp.co.monocrea.user.resource.response;

import java.time.LocalDateTime;

import jp.co.monocrea.user.repository.dto.UserDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDetailResponse from(UserDetailDto userDetail) {
        return new UserDetailResponse(
            userDetail.getId(),
            userDetail.getName(),
            userDetail.getEmail(),
            userDetail.getPhone(),
            userDetail.getAddress(),
            userDetail.getCreatedAt(),
            userDetail.getUpdatedAt()
        );
    }
}
