package dddhexarchexercise.splearn.application;

import dddhexarchexercise.splearn.application.provided.MemberFinder;
import dddhexarchexercise.splearn.application.provided.MemberRegistration;
import dddhexarchexercise.splearn.application.required.EmailSender;
import dddhexarchexercise.splearn.application.required.MemberRepository;
import dddhexarchexercise.splearn.domain.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class MemberModifyService implements MemberRegistration {
    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(@Valid MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);
        Member member = Member.register(registerRequest, passwordEncoder);
        memberRepository.save(member);
        sendWelcomeEmail(member);
        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);
        member.activate();
        return memberRepository.save(member); // JPA는 save 호출이 필요허지 않으나 공통적으로 적용하기 위해 save 호출
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if(memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다: " + registerRequest.email());
        }
    }
}
