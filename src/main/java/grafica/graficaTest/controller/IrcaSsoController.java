package grafica.graficaTest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Controller
public class IrcaSsoController {

    @Value("${sso.secret}")
    private String ssoSecret;

    @GetMapping("/inicio")
    public String recibirLocal(@RequestParam String token, HttpServletRequest request) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(ssoSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

            Integer idLocal = claims.get("idLocal", Integer.class);

            request.getSession(true).setAttribute("idLocal", idLocal);

            System.out.println("SSO OK - idLocal recibido: " + idLocal);

            return "redirect:/"; // tu GraficaController ya maneja esta ruta

        } catch (JwtException e) {
            System.out.println("Token SSO inválido o expirado: " + e.getMessage());
            return "redirect:/";
        }
    }
}