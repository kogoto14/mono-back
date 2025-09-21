package jp.co.monocrea.user.repository;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserTable extends PanacheEntityBase{
    @Id
    @GeneratedValue
    private Long id;
    public String name;
    public String email;
    public String phone;
    public String address;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}

