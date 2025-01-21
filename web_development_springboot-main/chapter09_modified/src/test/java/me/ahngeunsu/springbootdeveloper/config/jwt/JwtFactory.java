package me.ahngeunsu.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
public class JwtFactory {
    private String subject = "test@email.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() +
            Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = emptyMap(); //import static java.util.Collections.emptyMap;

    // 빌더 패턴을 사용해 설정이 필요한 데이터만 선택 설정
    @Builder
    public JwtFactory(String subject, Date isuuedAt,
                      Date expiration, Map<String, Object> claims) {
        this.subject = subject != null ? subject : this.subject;    // 삼항연산자
        this.issuedAt = issuedAt != null ? isuuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }

    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    // jjwt 라이브러리를 사용해 JWT 생성
    public String createToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}
/*
    빌더 패턴을 사용해 객체를 만들 때 테스트가 필요한 데이터만 선택.
    빌더패턴을 사용하지 않을 필드 기본값을 사용하게 됩니다.

        test/java/me.../config/jwt에 TokenProviderTest.java 파일을 만들겁니다.
 */
