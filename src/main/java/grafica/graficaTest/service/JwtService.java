package grafica.graficaTest.service;

import grafica.graficaTest.model.ctrlUsuarios;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecretBase64;

    private final long EXPIRACION_MS = 1000L * 60 * 60 * 24 * 7; // 7 días

    private Key obtenerClave() {
        return Keys.hmacShaKeyFor(
                java.util.Base64.getDecoder().decode(jwtSecretBase64));
    }

    public String generarToken(ctrlUsuarios usuario) {

        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + EXPIRACION_MS);

        return Jwts.builder()
                .setSubject(String.valueOf(usuario.getIdUsuario()))
                .claim("idLocal", usuario.getIdLocal())
                .claim("nombreUsuario", usuario.getNombreUsuario())
                .claim("idNivel", usuario.getIdNivel())
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(obtenerClave(), SignatureAlgorithm.HS256)
                .compact();

    }

    public Claims extraerClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(obtenerClave())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public boolean esTokenValido(String token) {

        try {
            extraerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}