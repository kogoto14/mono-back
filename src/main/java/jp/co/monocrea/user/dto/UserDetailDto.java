package jp.co.monocrea.user.dto;

import java.time.LocalDateTime;

public class UserDetailDto {
    public Long id;
    public String name;
    public String email;
    public String phone;
    public String address;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public UserDetailDto(Long id, String name, String email, String phone, String address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
