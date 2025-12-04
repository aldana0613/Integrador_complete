package com.seguridadindustrial.backend.controller;

import com.seguridadindustrial.backend.Incidente;
import com.seguridadindustrial.backend.repository.IncidenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/incidentes")
@CrossOrigin(origins = "*")
public class IncidenteController {

    private static final Logger logger = LoggerFactory.getLogger(IncidenteController.class);

    @Autowired
    private IncidenteRepository incidenteRepository;

    @GetMapping
    public List<Incidente> getAllIncidentes() {
        logger.info("📥 [GET] /api/incidentes - listando todos los incidentes");
        return incidenteRepository.findAll();
    }

    @GetMapping("/estado/{estado}")
    public List<Incidente> getIncidentesByEstado(@PathVariable String estado) {
        logger.info("📥 [GET] /api/incidentes/estado/{} - listando incidentes por estado", estado);
        return incidenteRepository.findByEstado(estado);
    }

    @GetMapping("/usuario/{usuarioReporta}")
    public List<Incidente> getIncidentesByUsuario(@PathVariable String usuarioReporta) {
        logger.info("📥 [GET] /api/incidentes/usuario/{} - listando incidentes reportados por usuario", usuarioReporta);
        return incidenteRepository.findByUsuarioReporta(usuarioReporta);
    }

    @GetMapping("/{id}")
    public Incidente getIncidenteById(@PathVariable Long id) {
        logger.info("📥 [GET] /api/incidentes/{} - obteniendo incidente por ID", id);
        return incidenteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Incidente createIncidente(@RequestBody Incidente incidente) {
        logger.info("📝 [POST] /api/incidentes - creando incidente. Tipo: {}, Usuario: {}",
                incidente.getTipoIncidencia(), incidente.getUsuarioReporta());
        Incidente saved = incidenteRepository.save(incidente);
        logger.debug("✅ Incidente creado con ID {}", saved.getId());
        return saved;
    }

    @PutMapping("/{id}")
    public Incidente updateIncidente(@PathVariable Long id, @RequestBody Incidente incidenteDetails) {
        logger.info("✏️ [PUT] /api/incidentes/{} - actualizando incidente", id);
        Incidente incidente = incidenteRepository.findById(id).orElse(null);
        if (incidente != null) {
            if (incidenteDetails.getTipoIncidencia() != null) {
                incidente.setTipoIncidencia(incidenteDetails.getTipoIncidencia());
            }
            if (incidenteDetails.getFechaHora() != null) {
                incidente.setFechaHora(incidenteDetails.getFechaHora());
            }
            if (incidenteDetails.getUbicacion() != null) {
                incidente.setUbicacion(incidenteDetails.getUbicacion());
            }
            if (incidenteDetails.getDescripcion() != null) {
                incidente.setDescripcion(incidenteDetails.getDescripcion());
            }
            if (incidenteDetails.getAccion() != null) {
                incidente.setAccion(incidenteDetails.getAccion());
            }
            if (incidenteDetails.getEstado() != null) {
                incidente.setEstado(incidenteDetails.getEstado());
            }
            if (incidenteDetails.getUsuarioReporta() != null) {
                incidente.setUsuarioReporta(incidenteDetails.getUsuarioReporta());
            }
            Incidente updated = incidenteRepository.save(incidente);
            logger.debug("✅ Incidente {} actualizado correctamente", id);
            return updated;
        } else {
            logger.warn("⚠️ Intento de actualizar incidente que no existe. ID={}", id);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteIncidente(@PathVariable Long id) {
        logger.info("🗑️ [DELETE] /api/incidentes/{} - eliminando incidente", id);
        incidenteRepository.deleteById(id);
    }
}
