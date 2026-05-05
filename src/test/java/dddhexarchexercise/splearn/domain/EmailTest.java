package dddhexarchexercise.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    void equality() {
        var email1 = new Email("kangok@naver.com");
        var email2 = new Email("kangok@naver.com");

        assertEquals(email1, email2);
    }

}