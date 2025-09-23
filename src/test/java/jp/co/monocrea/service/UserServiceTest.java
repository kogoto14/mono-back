package jp.co.monocrea.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.dto.UserDetailDto;
import jp.co.monocrea.user.dto.UserSummariesDto;
import jp.co.monocrea.user.repository.UserRepository;
import jp.co.monocrea.user.repository.UserTable;
import jp.co.monocrea.user.service.UserService;

@QuarkusTest
class UserServiceTest {
    
    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Test
    @TestTransaction
    void testPagenatedUserSummariesWithoutNameLike() {
        // Arrange
        List<UserTable> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserTable user = new UserTable();
            user.name = "Test User " + i;
            user.email = "test" + i + "@example.com";
            user.phone = "123456789" + i;
            user.address = "Test Address " + i;
            user.createdAt = LocalDateTime.of(2025, i, 1, 0, 0, 0);
            user.updatedAt = LocalDateTime.of(2025, i, 1, 0, 0, 0);
            userRepository.persist(user);

            users.add(user);
        }

        // Act
        UserSummariesDto userSummaries = userService.getPagenatedUserSummaries(null, null, "name", OrderEnum.DESC, 1, 10);

        // Assert
        assertEquals(10, userSummaries.getTotalCount());
        assertEquals(10, userSummaries.getUsers().size());
    }

    @Test
    @TestTransaction
    void testPagenatedUserSummariesWithNameLike() {
        // Arrange
        List<UserTable> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserTable user = new UserTable();
            user.name = "Test User " + i;
            user.email = "test" + i + "@example.com";
            user.phone = "123456789" + i;
            user.address = "Test Address " + i;
            user.createdAt = LocalDateTime.of(2025, i, 1, 0, 0, 0);
            user.updatedAt = LocalDateTime.of(2025, i, 1, 0, 0, 0);
            userRepository.persist(user);
            users.add(user);
        }
    
        // Act
        UserSummariesDto userSummaries = userService.getPagenatedUserSummaries(null, "1", "name", OrderEnum.DESC, 1, 10);

        // Assert
        assertEquals(2, userSummaries.getTotalCount());
        assertEquals("Test User 10", userSummaries.getUsers().get(0).name);
        assertEquals("Test User 1", userSummaries.getUsers().get(1).name);
    }

    @Test
    @TestTransaction
    void testUserDetailById() {
        // Arrange
        UserTable user = new UserTable();
        user.name = "Test User 1";
        user.email = "test1@example.com";
        user.phone = "1234567891";
        user.address = "Test Address 1";
        user.createdAt = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        user.updatedAt = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        userRepository.persist(user);

        // Act
        UserDetailDto userDetail = userService.getUserDetailById(user.id);

        // Assert
        assertEquals(user.id, userDetail.id);
        assertEquals(user.name, userDetail.name);
        assertEquals(user.email, userDetail.email);
        assertEquals(user.phone, userDetail.phone);
        assertEquals(user.address, userDetail.address);
        assertEquals(user.createdAt, userDetail.createdAt);
        assertEquals(user.updatedAt, userDetail.updatedAt);
    }

    @Test
    @TestTransaction
    void testCreateUser() {
        // Arrange
        UserDetailDto userDetail = new UserDetailDto(
            null, "Test User 2", "test2@example.com", "1234567892", "Test Address 2", LocalDateTime.of(2025, 2, 1, 0, 0, 0), LocalDateTime.of(2025, 2, 1, 0, 0, 0)
        );

        // Act
        userService.createUser(userDetail);

        // Assert
        List<UserTable> actualUsers = userRepository.findAll().list();
        assertEquals(1, actualUsers.size());
        assertEquals(userDetail.name, actualUsers.get(0).name);
        assertEquals(userDetail.email, actualUsers.get(0).email);
        assertEquals(userDetail.phone, actualUsers.get(0).phone);
        assertEquals(userDetail.address, actualUsers.get(0).address);
        assertEquals(userDetail.createdAt, actualUsers.get(0).createdAt);
        assertEquals(userDetail.updatedAt, actualUsers.get(0).updatedAt);
    }

    @Test
    @TestTransaction
    void testUpdateUser() {
        // Arrange
        UserTable user = new UserTable();
        user.name = "Test User 3";
        user.email = "test3@example.com";
        user.phone = "1234567893";
        user.address = "Test Address 3";
        user.createdAt = LocalDateTime.of(2025, 3, 1, 0, 0, 0);
        user.updatedAt = LocalDateTime.of(2025, 3, 1, 0, 0, 0);
        userRepository.persist(user);

        // Act
        userService.updateUser(new UserDetailDto(
            user.id, "Updated User 3", "updated3@example.com", "1234567894", "Updated Address 3", user.createdAt, LocalDateTime.of(2025, 3, 2, 0, 0, 0)
        ));

        // Assert
        UserTable actualUser = userRepository.findById(user.id);
        assertEquals(user.id, actualUser.id);
        assertEquals("Updated User 3", actualUser.name);
        assertEquals("updated3@example.com", actualUser.email);
        assertEquals("1234567894", actualUser.phone);
        assertEquals("Updated Address 3", actualUser.address);
        assertEquals(user.createdAt, actualUser.createdAt);
        assertEquals(LocalDateTime.of(2025, 3, 2, 0, 0, 0), actualUser.updatedAt);
    }

    @Test
    @TestTransaction
    void testDeleteUser() {
        // Arrange
        UserTable user = new UserTable();
        user.name = "Test User 4";
        user.email = "test4@example.com";
        user.phone = "1234567894";
        user.address = "Test Address 4";
        user.createdAt = LocalDateTime.of(2025, 4, 1, 0, 0, 0);
        user.updatedAt = LocalDateTime.of(2025, 4, 1, 0, 0, 0);
        userRepository.persist(user);

        // Act
        userService.deleteUser(user.id);

        // Assert
        assertEquals(0, userRepository.findAll().list().size());
        assertEquals(null, userRepository.findById(user.id));
    }
}
