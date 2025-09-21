package jp.co.monocrea.user.dto;

import java.util.List;

public class UserSummariesDto {
    private final List<UserSummaryDto> users;
    private final long totalCount;

    public UserSummariesDto(List<UserSummaryDto> users, long totalCount) {
        this.users = users;
        this.totalCount = totalCount;
    }

    public List<UserSummaryDto> getUsers() {
        return this.users;
    }

    public long getTotalCount() {
        return this.totalCount;
    }
}
