package dddhexarchexercise.splearn.application.provided;

import dddhexarchexercise.splearn.SplearnTestConfiguration;
import dddhexarchexercise.splearn.domain.*;
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
public record MemberRegistrationTest(MemberRegistration memberRegistration) {
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

}
