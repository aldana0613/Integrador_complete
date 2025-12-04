package com.seguridadindustrial.backend.controller;

import com.seguridadindustrial.backend.Capacitacion;
import com.seguridadindustrial.backend.repository.CapacitacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capacitaciones")
@CrossOrigin(origins = "*")
public class CapacitacionController {

    @Autowired
    private CapacitacionRepository capacitacionRepository;

    @GetMapping
    public List<Capacitacion> getAllCapacitaciones() {
        return capacitacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Capacitacion getCapacitacionById(@PathVariable Long id) {
        return capacitacionRepository.findById(id).orElse(null);
    }

    @GetMapping("/area/{area}")
    public List<Capacitacion> getCapacitacionesByArea(@PathVariable String area) {
        return capacitacionRepository.findByArea(area);
    }

    // POST: recibe raw body, imprime y mapea con ObjectMapper configurado para java.time
    @PostMapping
    public ResponseEntity<Capacitacion> createCapacitacion(@RequestBody String body) {
        try {
            System.out.println("📌 RAW REQUEST BODY: " + body);

            ObjectMapper mapper = new ObjectMapper();
            // Registrar módulo para java.time (LocalDate, LocalTime...)
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            Capacitacion capacitacion = mapper.readValue(body, Capacitacion.class);

            System.out.println("📌 MAPPED Capacitacion:");
            System.out.println("    Tema: " + capacitacion.getTema());
            System.out.println("    Fecha: " + capacitacion.getFecha());
            System.out.println("    Hora: " + capacitacion.getHora());
            System.out.println("    Duración: " + capacitacion.getDuracion());
            System.out.println("    Responsable: " + capacitacion.getResponsable());
            System.out.println("    Área: " + capacitacion.getArea());
            System.out.println("    Usuario reporta (mapeado): " + capacitacion.getUsuarioReporta());
            System.out.println("===========================================");

            Capacitacion saved = capacitacionRepository.save(capacitacion);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public Capacitacion updateCapacitacion(@PathVariable Long id, @RequestBody Capacitacion capacitacionDetails) {

        Capacitacion capacitacion = capacitacionRepository.findById(id).orElse(null);

        if (capacitacion != null) {

            if (capacitacionDetails.getTema() != null)
                capacitacion.setTema(capacitacionDetails.getTema());

            if (capacitacionDetails.getFecha() != null)
                capacitacion.setFecha(capacitacionDetails.getFecha());

            if (capacitacionDetails.getHora() != null)
                capacitacion.setHora(capacitacionDetails.getHora());

            if (capacitacionDetails.getDuracion() != null)
                capacitacion.setDuracion(capacitacionDetails.getDuracion());

            if (capacitacionDetails.getResponsable() != null)
                capacitacion.setResponsable(capacitacionDetails.getResponsable());

            if (capacitacionDetails.getArea() != null)
                capacitacion.setArea(capacitacionDetails.getArea());

            if (capacitacionDetails.getUsuarioReporta() != null)
                capacitacion.setUsuarioReporta(capacitacionDetails.getUsuarioReporta());

            return capacitacionRepository.save(capacitacion);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCapacitacion(@PathVariable Long id) {
        capacitacionRepository.deleteById(id);
    }
}
