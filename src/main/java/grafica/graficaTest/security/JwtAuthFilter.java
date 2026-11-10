package grafica.graficaTest.security;

import grafica.graficaTest.service.JwtService;
import io.jsonwebtoken.Claims;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;

        }

        String token = authHeader.substring(7);

        if (!jwtService.esTokenValido(token)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        }

        Claims claims = jwtService.extraerClaims(token);

        request.setAttribute(
                "idLocal",
                claims.get("idLocal", Integer.class));

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);

    }

}