package com.seguridadindustrial.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.seguridadindustrial.backend.Usuario;
import com.seguridadindustrial.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        logger.info("📥 [GET] /api/usuarios - listando todos los usuarios");
        return usuarioRepository.findAll();
    }

    @GetMapping("/{username}")
    public Usuario getUsuario(@PathVariable String username) {
        logger.info("📥 [GET] /api/usuarios/{} - obteniendo usuario", username);
        return usuarioRepository.findById(username).orElse(null);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        logger.info("👤 [POST] /api/usuarios - creando nuevo usuario. username: {}, email: {}",
                usuario.getUsername(), usuario.getEmail());

        Usuario nuevo = usuarioRepository.save(usuario);

        logger.info("✅ Usuario creado correctamente con username: {}", nuevo.getUsername());
        return nuevo;
    }

    @PutMapping("/{username}")
    public Usuario updateUsuario(@PathVariable String username, @RequestBody Usuario usuarioDetails) {
        logger.info("✏️ [PUT] /api/usuarios/{} - actualizando usuario", username);

        Usuario usuario = usuarioRepository.findById(username).orElse(null);
        if (usuario != null) {
            usuario.setPassword(usuarioDetails.getPassword());
            usuario.setRol(usuarioDetails.getRol());
            usuario.setNombreCompleto(usuarioDetails.getNombreCompleto());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setAreaTrabajo(usuarioDetails.getAreaTrabajo());

            Usuario actualizado = usuarioRepository.save(usuario);
            logger.info("✅ Usuario {} actualizado correctamente", username);
            return actualizado;
        } else {
            logger.warn("⚠️ Intento de actualizar usuario que no existe. username={}", username);
        }
        return null;
    }

    @DeleteMapping("/{username}")
    public void deleteUsuario(@PathVariable String username) {
        logger.info("🗑️ [DELETE] /api/usuarios/{} - eliminando usuario", username);
        usuarioRepository.deleteById(username);
        logger.info("✅ Usuario {} eliminado", username);
    }
}
