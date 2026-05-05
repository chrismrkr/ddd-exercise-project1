package dddhexarchexercise.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        this.passwordEncoder = MemberFixture.createPasswordEncoder();
        MemberRegisterRequest req = MemberFixture.createMemberRegisterRequest("kangok@splearn.app");
        member = Member.register(req, this.passwordEncoder);
    }

    // IntelliJ 설정의 live template 활용해서 테스트코드 작성시간 줄여볼 것
    @Test
    void 회원생성() {

        assertEquals(member.getStatus(), MemberStatus.PENDING);
    }

    @Test
    void 생성자_NullCheck() {
        assertThrows(NullPointerException.class, () -> Member.register(MemberFixture.createMemberRegisterRequest(null), null));
    }

    @Test
    void activate() {
        member.activate();

        assertEquals(member.getStatus(), MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();

        assertThrows(IllegalStateException.class, member::activate);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertEquals(member.getStatus(), MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThrows(IllegalStateException.class, member::deactivate);

        member.activate();
        member.deactivate();

        assertThrows(IllegalStateException.class, member::deactivate);
    }

    @Test
    void verifyPassword() {
        assertTrue(member.verifyPassword("secret", passwordEncoder));
    }

    @Test
    void changeNickname() {
        assertEquals(member.getNickname(), "kangok");
        member.changeNickname("sohyun");
        assertEquals(member.getNickname(), "sohyun");
    }

    @Test
    void changePasword() {
        member.changPassword("verysecret", passwordEncoder);
        assertTrue(member.verifyPassword("verysecret", passwordEncoder));
    }

    @Test
    void isActive() {
        assertFalse(member.isActive());
        member.activate();
        assertTrue(member.isActive());
    }

    @Test
    void invalidEmail() {
        assertThrows(IllegalArgumentException.class, () ->
            Member.register(MemberFixture.createMemberRegisterRequest("aaa"), passwordEncoder)
        );
    }
}