package jp.co.monocrea.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jp.co.monocrea.user.repository.dto.UserSummaryDto;
import org.junit.jupiter.api.Test;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.repository.dto.PagedResultDto;
import jp.co.monocrea.user.repository.UserRepository;
import jp.co.monocrea.user.repository.table.UserTable;

@QuarkusTest
class UserRepositoryTest {
    @Inject
    UserRepository userRepository;

    @Test
    @TestTransaction
    void testCreateUser() {
        // Arrange
        UserTable user = new UserTable();
        user.setName("Test User 1");
        user.setEmail("test1@example.com");
        user.setPhone("1234567891");
        user.setAddress("Test Address 1");
        user.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        user.setUpdatedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

        // Act
        Long id = userRepository.createUser(user);

        // Assert
        List<UserTable> actualUsers = userRepository.findAll().list();
        assertEquals(1, actualUsers.size());
        UserTable actualUser = actualUsers.get(0);
        assertEquals(id, actualUser.getId());
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getPhone(), actualUser.getPhone());
        assertEquals(user.getAddress(), actualUser.getAddress());
        assertEquals(user.getCreatedAt(), actualUser.getCreatedAt());
        assertEquals(user.getUpdatedAt(), actualUser.getUpdatedAt());
    }

    @Test
    @TestTransaction
    void testFindUserDetailById() {
        // Arrange
        UserTable user = new UserTable();
        user.setName("Test User 2");
        user.setEmail("test2@example.com");
        user.setPhone("1234567892");
        user.setAddress("Test Address 2");
        user.setCreatedAt(LocalDateTime.of(2025, 2, 1, 0, 0, 0));
        user.setUpdatedAt(LocalDateTime.of(2025, 2, 1, 0, 0, 0));
        userRepository.persist(user);

        // Act
        UserTable actualUser = userRepository.findById(user.getId());

        // Assert
        assertEquals(1, userRepository.count());
        assertEquals(user.getId(), actualUser.getId());
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getPhone(), actualUser.getPhone());
        assertEquals(user.getAddress(), actualUser.getAddress());
        assertEquals(user.getCreatedAt(), actualUser.getCreatedAt());
        assertEquals(user.getUpdatedAt(), actualUser.getUpdatedAt());
    }

    @Test
    @TestTransaction
    void testUpdateUser() {
        // Arrange
        UserTable user = new UserTable();
        user.setName("Test User 3");
        user.setEmail("test3@example.com");
        user.setPhone("1234567893");
        user.setAddress("Test Address 3");
        user.setCreatedAt(LocalDateTime.of(2025, 3, 1, 0, 0, 0));
        user.setUpdatedAt(LocalDateTime.of(2025, 3, 1, 0, 0, 0));
        userRepository.persist(user);

        // Act
        boolean result = userRepository.updateUser(user);

        // Assert
        UserTable actualUser = userRepository.findById(user.getId());
        assertEquals(true, result);
        assertEquals(1, userRepository.count());
        assertEquals(user.getId(), actualUser.getId());
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getPhone(), actualUser.getPhone());
        assertEquals(user.getAddress(), actualUser.getAddress());
        assertEquals(user.getCreatedAt(), actualUser.getCreatedAt());
        assertEquals(user.getUpdatedAt(), actualUser.getUpdatedAt());
    }

    @Test
    @TestTransaction
    void testDeleteUser() {
        // Arrange
        UserTable user = new UserTable();
        user.setName("Test User 4");
        user.setEmail("test4@example.com");
        user.setPhone("1234567894");
        user.setAddress("Test Address 4");
        user.setCreatedAt(LocalDateTime.of(2025, 4, 1, 0, 0, 0));
        user.setUpdatedAt(LocalDateTime.of(2025, 4, 1, 0, 0, 0));
        userRepository.persist(user);

        // Act
        boolean result = userRepository.deleteUser(user.getId());

        // Assert
        assertEquals(true, result);
        assertEquals(0, userRepository.count());
        assertEquals(null, userRepository.findById(user.getId()));
    }

    @Test
    @TestTransaction
    void testFindPagedUserSummariesWithSort() {
        // Arrange
        List<UserTable> users = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            UserTable user = new UserTable();
            user.setName("Test User " + i);
            user.setEmail("test" + i + "@example.com");
            user.setPhone("123456789" + i);
            user.setAddress("Test Address " + i);
            user.setCreatedAt(LocalDateTime.of(2025, 5, 1, 0, 0, 0));
            user.setUpdatedAt(LocalDateTime.of(2025, 5, 1, 0, 0, 0));
            userRepository.persist(user);

            users.add(user);
        }

        PagedResultDto expected = new PagedResultDto(
            users.stream()
                .map(u -> new UserSummaryDto(u.getId(), u.getName()))
                .toList(),
            (long) users.size()
        );

        // Act
        PagedResultDto actual = userRepository.findPagedUserSummariesWithSort(
            null, "id", OrderEnum.ASC, 1, 10
        );

        // Assert
        for (int i = 0; i < expected.getList().size(); i++) {
            assertEquals(expected.getList().get(i).getId(), actual.getList().get(i).getId());
            assertEquals(expected.getList().get(i).getName(), actual.getList().get(i).getName());
            assertEquals(expected.getTotalCount(), actual.getTotalCount());
            assertEquals(expected.getTotalCount(), actual.getTotalCount());
        }
        assertEquals(expected.getTotalCount(), actual.getTotalCount());
    }
}
