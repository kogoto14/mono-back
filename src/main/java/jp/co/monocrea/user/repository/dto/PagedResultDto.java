package jp.co.monocrea.user.repository.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PagedResultDto {
    private final List<UserSummaryDto> list;
    private final Long totalCount;
}
