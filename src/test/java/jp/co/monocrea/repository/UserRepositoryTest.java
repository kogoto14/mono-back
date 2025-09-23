package jp.co.monocrea.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jp.co.monocrea.user.dto.UserSummaryDto;
import jp.co.monocrea.user.repository.PagedResult;
import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.dto.UserDetailDto;
import jp.co.monocrea.user.repository.UserRepository;
import jp.co.monocrea.user.repository.UserTable;

@QuarkusTest
class UserRepositoryTest {
    @Inject
    UserRepository userRepository;

    @Test
    @TestTransaction
    void testCreateUser() {
        // Arrange
        UserDetailDto newUser = new UserDetailDto(
            null, "Test User 1", "test1@example.com", "1234567891",
            "Test Address 1",
            LocalDateTime.of(2025, 1, 1, 0, 0, 0),
            LocalDateTime.of(2025, 1, 1, 0, 0, 0)
        );

        // Act
        userRepository.createUser(newUser);

        // Assert
        List<UserTable> actualUsers = userRepository.findAll().list();
        assertEquals(1, actualUsers.size());
        UserTable actualUser = actualUsers.get(0);
        assertNotNull(actualUser.id);
        assertEquals(newUser.name, actualUser.name);
        assertEquals(newUser.email, actualUser.email);
        assertEquals(newUser.phone, actualUser.phone);
        assertEquals(newUser.address, actualUser.address);
        assertEquals(newUser.createdAt, actualUser.createdAt);
        assertEquals(newUser.updatedAt, actualUser.updatedAt);
    }

    @Test
    @TestTransaction
    void testFindUserDetailById() {
        // Arrange
        UserTable user = new UserTable();
        user.name = "Test User 2";
        user.email = "test2@example.com";
        user.phone = "1234567892";
        user.address = "Test Address 2";
        user.createdAt = LocalDateTime.of(2025, 2, 1, 0, 0, 0);
        user.updatedAt = LocalDateTime.of(2025, 2, 1, 0, 0, 0);
        userRepository.persist(user);

        // Act
        UserTable actualUser = userRepository.findById(user.id);

        // Assert
        assertEquals(1, userRepository.count());
        assertEquals(user.id, actualUser.id);
        assertEquals(user.name, actualUser.name);
        assertEquals(user.email, actualUser.email);
        assertEquals(user.phone, actualUser.phone);
        assertEquals(user.address, actualUser.address);
        assertEquals(user.createdAt, actualUser.createdAt);
        assertEquals(user.updatedAt, actualUser.updatedAt);
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

        LocalDateTime newUpdatedAt = LocalDateTime.of(2025, 3, 2, 0, 0, 0);

        // Act
        userRepository.updateUser(
            new UserDetailDto(
                user.id,
                "Updated User 3",
                "updated3@example.com",
                "1234567894",
                "Updated Address 3",
                user.createdAt,
                newUpdatedAt
            )
        );

        // Assert
        UserTable actualUser = userRepository.findById(user.id);
        assertEquals(1, userRepository.count());
        assertEquals(user.id, actualUser.id);
        assertEquals("Updated User 3", actualUser.name);
        assertEquals("updated3@example.com", actualUser.email);
        assertEquals("1234567894", actualUser.phone);
        assertEquals("Updated Address 3", actualUser.address);
        assertEquals(user.createdAt, actualUser.createdAt);
        assertEquals(newUpdatedAt, actualUser.updatedAt);
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
        userRepository.deleteUser(user.id);

        // Assert
        assertEquals(0, userRepository.count());
        assertEquals(null, userRepository.findById(user.id));
    }

    @Test
    @TestTransaction
    void testFindPagedUserSummariesWithSort() {
        // Arrange
        List<UserTable> users = new ArrayList<>();
        for (int i = 5; i < 10; i++) {
            UserTable user = new UserTable();
            user.name = "Test User " + i;
            user.email = "test" + i + "@example.com";
            user.phone = "123456789" + i;
            user.address = "Test Address " + i;
            user.createdAt = LocalDateTime.of(2025, 5, 1, 0, 0, 0);
            user.updatedAt = LocalDateTime.of(2025, 5, 1, 0, 0, 0);
            userRepository.persist(user);

            users.add(user);
        }

        PagedResult<UserSummaryDto> expected = new PagedResult<>(
            users.stream()
                .map(u -> new UserSummaryDto(u.id, u.name))
                .toList(),
            (long) users.size()
        );

        // Act
        PagedResult<UserSummaryDto> actual = userRepository.findPagedUserSummariesWithSort(
            null, "id", OrderEnum.ASC, 1, 10
        );

        // Assert
        for (int i = 0; i < expected.list.size(); i++) {
            UserSummaryDto expectedDto = expected.list.get(i);
            UserSummaryDto actualDto = actual.list.get(i);
            assertEquals(expectedDto.id, actualDto.id);
            assertEquals(expectedDto.name, actualDto.name);
        }
        assertEquals(expected.totalCount, actual.totalCount);
    }
}
