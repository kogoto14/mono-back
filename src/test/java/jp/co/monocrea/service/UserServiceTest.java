package jp.co.monocrea.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.repository.dto.UserDetailDto;
import jp.co.monocrea.user.repository.UserRepository;
import jp.co.monocrea.user.repository.table.UserTable;
import jp.co.monocrea.user.resource.response.PagedUserSummariesResponse;
import jp.co.monocrea.user.resource.response.UserDetailResponse;
import jp.co.monocrea.user.service.UserService;
import jp.co.monocrea.user.service.command.UserCreateCommand;
import jp.co.monocrea.user.service.command.UserUpdateCommand;

@QuarkusTest
class UserServiceTest {
    
    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Test
    @TestTransaction
    void testPaginatedUserSummariesWithoutNameLike() {
        // Arrange
        List<UserTable> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserTable user = new UserTable();
            user.setName("Test User " + i);
            user.setEmail("test" + i + "@example.com");
            user.setPhone("123456789" + i);
            user.setAddress("Test Address " + i);
            user.setCreatedAt(LocalDateTime.of(2025, i, 1, 0, 0, 0));
            user.setUpdatedAt(LocalDateTime.of(2025, i, 1, 0, 0, 0));
            userRepository.persist(user);

            users.add(user);
        }

        // Act
        PagedUserSummariesResponse userSummaries = userService.getPaginatedUserSummaries(null, null, "name", OrderEnum.DESC, 1, 10);

        // Assert
        assertEquals(10, userSummaries.getTotalCount());
        assertEquals(10, userSummaries.getUsers().getUsers().size());
    }

    @Test
    @TestTransaction
    void testPaginatedUserSummariesWithNameLike() {
        // Arrange
        List<UserTable> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            UserTable user = new UserTable();
            user.setName("Test User " + i);
            user.setEmail("test" + i + "@example.com");
            user.setPhone("123456789" + i);
            user.setAddress("Test Address " + i);
            user.setCreatedAt(LocalDateTime.of(2025, i, 1, 0, 0, 0));
            user.setUpdatedAt(LocalDateTime.of(2025, i, 1, 0, 0, 0));
            userRepository.persist(user);
            users.add(user);
        }
    
        // Act
        PagedUserSummariesResponse userSummaries = userService.getPaginatedUserSummaries(null, "1", "name", OrderEnum.DESC, 1, 10);

        // Assert
        assertEquals(2, userSummaries.getTotalCount());
        assertEquals("Test User 10", userSummaries.getUsers().getUsers().get(0).getName());
        assertEquals("Test User 1", userSummaries.getUsers().getUsers().get(1).getName());
    }

    @Test
    @TestTransaction
    void testUserDetailById() {
        // Arrange
        UserTable user = new UserTable();
        user.setName("Test User 1");
        user.setEmail("test1@example.com");
        user.setPhone("1234567891");
        user.setAddress("Test Address 1");
        user.setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        user.setUpdatedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
        userRepository.persist(user);

        // Act
        UserDetailResponse userDetail = userService.getUserDetailById(user.getId());

        // Assert
        assertEquals(user.getId(), userDetail.getId());
        assertEquals(user.getName(), userDetail.getName());
        assertEquals(user.getEmail(), userDetail.getEmail());
        assertEquals(user.getPhone(), userDetail.getPhone());
        assertEquals(user.getAddress(), userDetail.getAddress());
        assertEquals(user.getCreatedAt(), userDetail.getCreatedAt());
        assertEquals(user.getUpdatedAt(), userDetail.getUpdatedAt());
    }

    @Test
    @TestTransaction
    void testCreateUser() {
        UserCreateCommand userDetail = new UserCreateCommand(
            "Test User 2", "test2@example.com", "1234567892", "Test Address 2"
        );

        // Act
        userService.createUser(userDetail);

        // Assert
        List<UserTable> actualUsers = userRepository.findAll().list();
        assertEquals(1, actualUsers.size());
        assertEquals(userDetail.getName(), actualUsers.get(0).getName());
        assertEquals(userDetail.getEmail(), actualUsers.get(0).getEmail());
        assertEquals(userDetail.getPhone(), actualUsers.get(0).getPhone());
        assertEquals(userDetail.getAddress(), actualUsers.get(0).getAddress());
        assertNotNull(actualUsers.get(0).getCreatedAt());
        assertNotNull(actualUsers.get(0).getUpdatedAt());
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
        UserUpdateCommand userUpdateCommand = new UserUpdateCommand(
            user.getId(), "Updated User 3", "updated3@example.com", "1234567894", "Updated Address 3"
        );
        userService.updateUser(userUpdateCommand);

        // Assert
        UserTable actualUser = userRepository.findById(user.getId());
        assertEquals(user.getId(), actualUser.getId());
        assertEquals(userUpdateCommand.getName(), actualUser.getName());
        assertEquals(userUpdateCommand.getEmail(), actualUser.getEmail());
        assertEquals(userUpdateCommand.getPhone(), actualUser.getPhone());
        assertEquals(userUpdateCommand.getAddress(), actualUser.getAddress());
        assertEquals(user.getCreatedAt(), actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
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
        userService.deleteUser(user.getId());

        // Assert
        assertEquals(0, userRepository.findAll().list().size());
        assertEquals(null, userRepository.findById(user.getId()));
    }
}
