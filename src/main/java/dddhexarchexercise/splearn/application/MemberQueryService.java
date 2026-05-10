package dddhexarchexercise.splearn.application;

import dddhexarchexercise.splearn.application.provided.MemberFinder;
import dddhexarchexercise.splearn.application.required.MemberRepository;
import dddhexarchexercise.splearn.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;
    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId)
        );
    }
}
