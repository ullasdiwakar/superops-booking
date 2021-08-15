package ai.superops.booking.contollers;

import ai.superops.booking.security.UserToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserAuthController {

    @PostMapping("login")
    public UserToken login(@RequestParam("user") String username, @RequestParam("passwd") String passwd) {
        return new UserToken(username, getJWTToken(username));
    }

    private String getJWTToken(String username) {
        String key = "superops";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        Date issueTime = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(issueTime);
        cal.add(Calendar.HOUR_OF_DAY, 12);

        String token = Jwts
                .builder()
                .setId("bookingJWT")
                .setSubject(username)
                .claim(
                        "authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(issueTime)
                .setExpiration(cal.getTime())
                .signWith(SignatureAlgorithm.HS512,
                        key.getBytes()).compact();
        return "Bearer " + token;
    }
}
