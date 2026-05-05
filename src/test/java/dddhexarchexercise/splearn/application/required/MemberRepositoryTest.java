package dddhexarchexercise.splearn.application.required;

import dddhexarchexercise.splearn.domain.Member;
import dddhexarchexercise.splearn.domain.MemberFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());
        memberRepository.save(member);

        Assertions.assertTrue(member.getId() > 0);
        entityManager.flush();
    }

    @Test
    void duplicateEmailFail() {
        Member member = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());
        memberRepository.save(member);

        Member member2 = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> memberRepository.save(member2));
    }
}