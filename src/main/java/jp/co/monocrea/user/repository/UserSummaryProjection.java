package jp.co.monocrea.user.repository;

import java.time.LocalDateTime;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jp.co.monocrea.user.dto.UserSummaryDto;

@RegisterForReflection
public class UserSummaryProjection {
    public final Long id;
    public final String name;
    public final LocalDateTime createdAt;
    public final LocalDateTime updatedAt;

    public UserSummaryProjection(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserSummaryDto convertToDto() {
        return new UserSummaryDto(id, name, createdAt, updatedAt);
    }
}
