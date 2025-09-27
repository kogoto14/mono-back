package jp.co.monocrea.resource;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jp.co.monocrea.user.common.OrderEnum;
import jp.co.monocrea.user.resource.UserResource;
import jp.co.monocrea.user.resource.response.PagedUserSummariesResponse;
import jp.co.monocrea.user.resource.response.UserDetailResponse;
import jp.co.monocrea.user.resource.response.UserSummariesResponse;
import jp.co.monocrea.user.service.UserService;
import jp.co.monocrea.user.service.command.UserCreateCommand;
import jp.co.monocrea.user.service.command.UserUpdateCommand;

@QuarkusTest
class UserResourceTest {
    
    @Inject
    UserResource userResource;
    
    @InjectMock
    UserService userService;

    @Test
    void testPaginatedUsersReturns200() {
        doReturn(
            new PagedUserSummariesResponse(
                new UserSummariesResponse(new ArrayList<>()),
                0L
            )
        ).when(userService).getPaginatedUserSummaries(anyLong(), anyString(), anyString(), any(OrderEnum.class), anyInt(), anyInt());
        given().when().get("/users?id=1&_name_like=test&_page=1&_limit=10&_sort=name&_order=ASC").then().statusCode(200);
    }

    @Test
    void testPaginatedUsersReturns400WithInvalidId() {
        given().when().get("/users?id=-1&_name_like=test&_page=1&_limit=10&_sort=name&_order=ASC").then().statusCode(400);
    }

    @Test
    void testPaginatedUsersReturns400WithInvalidSortKey() {
        given().when().get("/users?id=1&_name_like=test&_page=1&_limit=10&_sort=invalid&_order=ASC").then().statusCode(400);
    }

    @Test
    void testGetUserByIdReturns200() {
        doReturn(new UserDetailResponse(1L, "Test User 1", "test1@example.com", "1234567891", "Test Address 1", LocalDateTime.of(2025, 1, 1, 0, 0, 0), LocalDateTime.of(2025, 1, 1, 0, 0, 0))).when(userService).getUserDetailById(anyLong());
        given().when().get("/users/{id}", 1L).then().statusCode(200);
    }

    @Test
    void testCreateUserReturns200() {
        doNothing().when(userService).createUser(any(UserCreateCommand.class));
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Test User 1\",\"email\":\"test1@example.com\",\"phone\":\"1234567891\",\"address\":\"Test Address 1\"}")
            .when().post("/users").then().statusCode(200);
    }

    @Test
    void testCreateUserReturns500() {
        doThrow(new jakarta.ws.rs.InternalServerErrorException()).when(userService).createUser(any(UserCreateCommand.class));
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Test User 1\",\"email\":\"test1@example.com\",\"phone\":\"1234567891\",\"address\":\"Test Address 1\"}")
            .when().post("/users").then().statusCode(500);
    }

    @Test
    void testCreateUserReturns400() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"Test User 1\",\"email\":\"test1@example.com\",\"phone\":\"1234567891\",\"address\":\"Test Address 1\"}")
            .when().post("/users/").then().statusCode(400);
    }

    @Test
    void testUpdateUserReturns200() {
        doNothing().when(userService).updateUser(any(UserUpdateCommand.class));
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Test User 1\",\"email\":\"test1@example.com\",\"phone\":\"1234567891\",\"address\":\"Test Address 1\"}")
            .when().put("/users/{id}", 1L).then().statusCode(200);
    }

    @Test
    void testUpdateUserReturns500() {
        doThrow(new jakarta.ws.rs.InternalServerErrorException()).when(userService).updateUser(any(UserUpdateCommand.class));
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\":\"Test User 1\",\"email\":\"test1@example.com\",\"phone\":\"1234567891\",\"address\":\"Test Address 1\"}")
            .when().put("/users/{id}", 1L).then().statusCode(500);
    }

    @Test
    void testUpdateUserReturns400() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"Test User 1\",\"email\":\"test1@example.com\",\"phone\":\"1234567891\",\"address\":\"Test Address 1\"}")
            .when().put("/users/{id}", 1L).then().statusCode(400);
    }

    @Test
    void testUpdateUserReturns405() {
        given().when().put("/users/").then().statusCode(405);
    }

    @Test
    void testDeleteUserReturns200() {        
        doNothing().when(userService).deleteUser(anyLong());
        given().when().delete("/users/{id}", 1L).then().statusCode(200);
    }

    @Test
    void testDeleteUserReturns500() {
        doThrow(new jakarta.ws.rs.InternalServerErrorException()).when(userService).deleteUser(anyLong());
        given().when().delete("/users/{id}", 1L).then().statusCode(500);
    }

    @Test
    void testDeleteUserReturns405() {
        given().when().delete("/users/").then().statusCode(405);
    }
}
