package dddhexarchexercise.splearn.application.provided;

import dddhexarchexercise.splearn.SplearnTestConfiguration;
import dddhexarchexercise.splearn.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
public record MemberRegistrationTest(MemberRegistration memberRegistration, EntityManager entityManager) {
    @Test
    void register() {
        Member member = memberRegistration.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertNotNull(member.getId());
        assertSame(member.getStatus(), MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        Member member = memberRegistration.register(MemberFixture.createMemberRegisterRequest());
        Assertions.assertThrows(DuplicateEmailException.class, () -> memberRegistration.register(MemberFixture.createMemberRegisterRequest()));

    }

    @Test
    void activate() {
        Member member = memberRegistration.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member activate = memberRegistration.activate(member.getId());
        entityManager.flush();

        Assertions.assertTrue(activate.isActive());
    }

    @Test
    void memberRegisterRequestFail() {

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                memberRegistration.register(
                        new MemberRegisterRequest("kim@splearn.app", "kim", "secret012345")))
        ;

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                memberRegistration.register(
                        new MemberRegisterRequest("kim@splearn.app", "kimabcabc", "secret")))
        ;

        Assertions.assertThrows(ConstraintViolationException.class, () ->
                memberRegistration.register(
                        new MemberRegisterRequest("kim", "kim123asd2", "secret012345")))
        ;
    }


}
