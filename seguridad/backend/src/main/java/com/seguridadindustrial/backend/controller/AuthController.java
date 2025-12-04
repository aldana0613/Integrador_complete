package com.seguridadindustrial.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.seguridadindustrial.backend.Usuario;
import com.seguridadindustrial.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static class LoginRequest {
        public String usuario;
        public String password;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        // Log del intento de login
        logger.info("🔐 Intento de login del usuario: {}", loginRequest.usuario);

        Usuario usuario = usuarioRepository.findByUsernameAndPassword(
                loginRequest.usuario,
                loginRequest.password
        );

        if (usuario != null) {
            logger.info("✅ Login exitoso para usuario: {}", usuario.getUsername());
            return "{\"success\": true, \"mensaje\": \"Login exitoso\", \"usuario\": \"" +
                    usuario.getUsername() + "\", \"rol\": \"" + usuario.getRol() + "\"}";
        } else {
            logger.warn("❌ Login fallido para usuario: {}", loginRequest.usuario);
            return "{\"success\": false, \"mensaje\": \"Usuario o contraseña incorrectos\"}";
        }
    }

    @GetMapping("/test")
    public String test() {
        logger.info("📡 [GET] /api/auth/test - verificación de estado del backend");
        return "✅ Backend de Seguridad Industrial funcionando correctamente";
    }
}
