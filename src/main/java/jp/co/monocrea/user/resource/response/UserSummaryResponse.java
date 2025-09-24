package jp.co.monocrea.user.resource.response;

import jp.co.monocrea.user.repository.dto.UserSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSummaryResponse {
    private Long id;
    private String name;

    public static UserSummaryResponse from(UserSummaryDto userSummary) {
        return new UserSummaryResponse(userSummary.getId(), userSummary.getName());
    }
}
