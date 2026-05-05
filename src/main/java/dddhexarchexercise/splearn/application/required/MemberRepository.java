package dddhexarchexercise.splearn.application.required;

import dddhexarchexercise.splearn.domain.Email;
import dddhexarchexercise.splearn.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * 회원 정보 저장 및 조회
 */
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member); // 수정 및 등록할 때 사용하는 메소드

    Optional<Member> findByEmail(Email email);
}
