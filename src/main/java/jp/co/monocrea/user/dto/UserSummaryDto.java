package jp.co.monocrea.user.dto;

public class UserSummaryDto {
    public Long id;
    public String name;

    public UserSummaryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
