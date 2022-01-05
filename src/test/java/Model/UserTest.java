package Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testUserConstructorShouldCreateObjectWithValidState() {
        User user = new User("Name", "1Ps#$@!%&*?.");
        assertSame("Name", user.getUsername());
        assertSame("1Ps#$@!%&*?.", user.getPassword());
    }



}