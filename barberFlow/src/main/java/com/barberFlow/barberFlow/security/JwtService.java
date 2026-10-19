package com.barberFlow.barberFlow.security;

import java.time.Duration;
import java.time.Instant;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.barberFlow.barberFlow.config.JwtProperties;
import com.barberFlow.barberFlow.config.SecurityConfig;
import com.barberFlow.barberFlow.model.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;

    public String gerar(Usuario usuario) {
        Instant agora = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(SecurityConfig.ISSUER)
            .subject(String.valueOf(usuario.getId()))
            .issuedAt(agora)
            .expiresAt(agora.plus(Duration.ofMinutes(properties.expirationMinutes())))
            .claim("role", usuario.getFuncao().name())
            .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public long expiracaoEmSegundos() {
        return Duration.ofMinutes(properties.expirationMinutes()).toSeconds();
    }
}
