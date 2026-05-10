package dddhexarchexercise.splearn.application.provided;

import dddhexarchexercise.splearn.domain.Member;
import dddhexarchexercise.splearn.domain.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원 등록 관련 기능을 제공한다.
 */
public interface MemberRegistration {
    Member register(@Valid MemberRegisterRequest registerRequest);
    Member activate(Long memberId);
}
