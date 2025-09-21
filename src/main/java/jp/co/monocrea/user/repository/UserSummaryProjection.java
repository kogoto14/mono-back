package jp.co.monocrea.user.repository;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jp.co.monocrea.user.dto.UserSummaryDto;

@RegisterForReflection
public class UserSummaryProjection {
    public final Long id;
    public final String name;

    public UserSummaryProjection(Long id, String name) {
        this.id = id;
        this.name = name;

    }

    public UserSummaryDto convertToDto() {
        return new UserSummaryDto(id, name);
    }
}
