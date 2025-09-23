package jp.co.monocrea.resource;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jp.co.monocrea.user.resource.UserResource;

@QuarkusTest
class UserResourceTest {
    
    @Inject
    UserResource userResource;

    @Test
    void testPagenatedUsers() {
        
    }

    @Test
    void testGetUserById() {
        
    }

    @Test
    void testCreateUser() {
        
    }

    @Test
    void testUpdateUser() {
        
    }

    @Test
    void testDeleteUser() {
    
    }
}
