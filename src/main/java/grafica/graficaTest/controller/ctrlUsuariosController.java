package grafica.graficaTest.controller;

import grafica.graficaTest.DTO.LoginRequest;
import grafica.graficaTest.model.ctrlUsuarios;
import grafica.graficaTest.service.ctrlUsuariosService;
import grafica.graficaTest.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mobile/auth")
public class ctrlUsuariosController {

    @Autowired
    private ctrlUsuariosService ctrlUsuarioService;

    @Autowired
    private JwtService jwtService;
    
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        System.out.println("=======================================");
        System.out.println("INTENTO DE LOGIN MÓVIL");
        System.out.println("=======================================");

        // ==========================================
        // 1. Buscar usuario por credenciales
        // ==========================================

        Optional<ctrlUsuarios> usuarioOpt =
                ctrlUsuarioService.buscarPorUsuarioYClave(
                        request.getIdUsuario(),
                        request.getClave());
        
        
        System.out.println("Usuario o clave incorrectos---------" + usuarioOpt);

        if (usuarioOpt.isEmpty()) {

            System.out.println("Usuario o clave incorrectos");

            return ResponseEntity.status(401)
                    .body("Usuario o contraseña incorrectos");

        }

        ctrlUsuarios usuario = usuarioOpt.get();

        // ==========================================
        // 2. Verificar que el usuario esté activo
        // ==========================================

        if (usuario.getEstado() == null || usuario.getEstado() != 1) {

            System.out.println("Usuario inactivo: " + usuario.getNombreUsuario());

            return ResponseEntity.status(403)
                    .body("Usuario inactivo");

        }

        // ==========================================
        // 3. Generar token
        // ==========================================

        String token = jwtService.generarToken(usuario);

        System.out.println("Login exitoso. idLocal: " + usuario.getIdLocal());

        // ==========================================
        // 4. Armar respuesta
        // ==========================================

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("token", token);
        respuesta.put("idLocal", usuario.getIdLocal());
        //respuesta.put("nombreAcueducto", usuario.getNombreUsuario());
        
        //System.out.println("idUsuario para razonSocial: [" + usuario.getIdUsuario() + "]");
        //System.out.println("clave para razonSocial: [" + request.getClave() + "]");
        
        String razonSocial = ctrlUsuarioService.obtenerRazonSocial(
                usuario.getIdUsuario(), request.getClave());

        respuesta.put("nombreAcueducto", razonSocial);
        
        System.out.println("Local: " + razonSocial);
   
        

        return ResponseEntity.ok(respuesta);

    }

}