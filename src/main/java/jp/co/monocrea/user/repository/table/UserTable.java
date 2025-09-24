package jp.co.monocrea.user.repository.table;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class UserTable extends PanacheEntityBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String email;
    @Getter @Setter private String phone;
    @Getter @Setter private String address;
    @Getter @Setter private LocalDateTime createdAt;
    @Getter @Setter private LocalDateTime updatedAt;
}
