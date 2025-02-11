package vn.hoidanit.jobhunter.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {

    @Value("${hoidanit.jwt.token-validity-in-seconds}")
    private String jwtExpiration;

    public void createToken(Authentication authentication) {

    }
}
