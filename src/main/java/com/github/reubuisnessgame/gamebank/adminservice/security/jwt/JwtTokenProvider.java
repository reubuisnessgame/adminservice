package com.github.reubuisnessgame.gamebank.adminservice.security.jwt;

import com.github.reubuisnessgame.gamebank.adminservice.model.UserModel;
import com.github.reubuisnessgame.gamebank.adminservice.repository.UserRepository;
import com.github.reubuisnessgame.gamebank.adminservice.security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.issued:hello}")
    private String ISSUED;

    @Value("${security.jwt.token.secretKey:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds; // 10h

    private final CustomUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    public JwtTokenProvider(CustomUserDetailsService userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;

        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role) {
        UserModel userModel = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username: " + username + " not found"));
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userModel.getId());
        claims.put("role", role);
        claims.setIssuer(ISSUED);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return getClaims(token).getBody().getSubject();
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);

            return !(claims.getBody().getExpiration().before(new Date())
                    && claims.getBody().getIssuer().equals(ISSUED));
        } catch (JwtException e) {
            throw new InvalidJwtAuthenticationException("Expired JWT token");
        } catch (IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Invalid JWT token");
        }
    }

}
