package dddhexarchexercise.splearn.application.provided;

import dddhexarchexercise.splearn.SplearnTestConfiguration;
import dddhexarchexercise.splearn.domain.Member;
import dddhexarchexercise.splearn.domain.MemberFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegistration memberRegister, EntityManager entityManager) {
    @Test
    void find() {
        Member register = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member found = memberFinder.find(register.getId());

        Assertions.assertEquals(register.getId(), found.getId());
    }

    @Test
    void findFail() {
        Member register = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberFinder.find(99999L));
    }
}