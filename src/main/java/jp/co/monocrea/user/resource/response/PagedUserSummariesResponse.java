package jp.co.monocrea.user.resource.response;

import jp.co.monocrea.user.repository.dto.PagedResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PagedUserSummariesResponse {
    private UserSummariesResponse users;
    private Long totalCount;

    public static PagedUserSummariesResponse from(PagedResultDto dto) {
        return new PagedUserSummariesResponse(
            UserSummariesResponse.from(dto.getList()),
            dto.getTotalCount()
        );
    }
}
