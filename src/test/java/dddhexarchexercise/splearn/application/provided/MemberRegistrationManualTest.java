package dddhexarchexercise.splearn.application.provided;

import dddhexarchexercise.splearn.application.MemberModifyService;
import dddhexarchexercise.splearn.application.required.EmailSender;
import dddhexarchexercise.splearn.application.required.MemberRepository;
import dddhexarchexercise.splearn.domain.Email;
import dddhexarchexercise.splearn.domain.Member;
import dddhexarchexercise.splearn.domain.MemberFixture;
import dddhexarchexercise.splearn.domain.MemberStatus;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberRegistrationManualTest {
    @Test
    void registerTestStub() {
        MemberRegistration memberRegistration = new MemberModifyService(
                new MemberFinderStub(), new MemberRepositoryStub(), new EmailSenderStub(), MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegistration.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertNotNull(member.getId());
        assertSame(member.getStatus(), MemberStatus.PENDING);
    }

    @Test
    void registerTestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();
        MemberRegistration memberRegistration = new MemberModifyService(
                new MemberFinderStub(), new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegistration.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertNotNull(member.getId());
        assertSame(member.getStatus(), MemberStatus.PENDING);
        Assertions.assertTrue(emailSenderMock.getTos().size() > 0);
    }

    @Test
    void registerTestMockito() {
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);
        MemberRegistration memberRegistration = new MemberModifyService(
                new MemberFinderStub(), new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegistration.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertNotNull(member.getId());
        assertSame(member.getStatus(), MemberStatus.PENDING);

        Mockito.verify(emailSenderMock).send(ArgumentMatchers.eq(member.getEmail()), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    static class MemberFinderStub implements MemberFinder {

        @Override
        public Member find(Long memberId) {
            return null;
        }
    }

    static class MemberRepositoryStub implements MemberRepository {
        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }

        @Override
        public Optional<Member> findById(Long memberId) {
            return Optional.empty();
        }
    }

    static class EmailSenderStub implements EmailSender {
        @Override
        public void send(Email email, String subject, String body) {}
    }

    @Getter
    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();
        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }
    }
}