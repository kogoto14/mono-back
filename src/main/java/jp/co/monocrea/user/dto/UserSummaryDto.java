package jp.co.monocrea.user.dto;

import java.time.LocalDateTime;

public class UserSummaryDto {
    public Long id;
    public String name;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public UserSummaryDto(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
