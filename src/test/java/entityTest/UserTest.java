package entityTest;

import entity.User;

import org.junit.*;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUserEntity() {
        User user = new User(1, "test_user", "UNBLOCKED", "ROLE_USER", "Test User", "Тестировочный Пользователь");

        assertNotNull(user);

        user.setId(2);
        user.setLogin("Vlad");
        user.setBlockStatus("BLOCKED");
        user.setRole("ROLE_USER");
        user.setUserName("Vlad Pasichniuk");
        user.setUserNameRU("Влад Пасичнюк");

        assertEquals(user.getId(), 2);
        assertEquals(user.getLogin(), "Vlad");
        assertEquals(user.getBlockStatus(), "BLOCKED");
        assertEquals(user.getRole(), "ROLE_USER");
        assertEquals(user.getUserName(), "Vlad Pasichniuk");
        assertEquals(user.getUserNameRU(), "Влад Пасичнюк");
    }
}
