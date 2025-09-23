package jp.co.monocrea.user.resource.response;

import java.util.List;
import jp.co.monocrea.user.repository.dto.UserSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSummariesResponse {
    private List<UserSummaryResponse> users;
    
    public static UserSummariesResponse from(List<UserSummaryDto> dto) {
        return new UserSummariesResponse(dto.stream()
                .map(UserSummaryResponse::from)
                .toList());
    }
}
