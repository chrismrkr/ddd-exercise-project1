package dddhexarchexercise.splearn.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.util.Assert;

import java.util.Objects;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache // NaturalId로 영속성 컨텍스트에 캐시함
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 식별자 (고유성, 불변성)

    @Embedded
    @NaturalId
    private Email email; // 값 객체 (고유성)

    private String nickname;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static Member register(MemberRegisterRequest request, PasswordEncoder passwordEncoder) {

        Member member = new Member();
        member.email = new Email(request.email());
        member.nickname = Objects.requireNonNull(request.nickname());
        member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(request.password()));
        member.status = MemberStatus.PENDING;
        return member;
    }

    public void activate() {
        Assert.state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        Assert.state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String inputPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(inputPassword, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = Objects.requireNonNull(nickname);
    }

    public void changPassword(String secret, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(secret));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
