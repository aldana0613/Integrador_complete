package com.seguridadindustrial.backend;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "capacitaciones")
public class Capacitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tema;
    private LocalDate fecha;
    private LocalTime hora;
    private Integer duracion;
    private String responsable;
    private String area;

    // Acepta "usuario_reporta" y "usuarioReporta" desde JSON y mapea a la columna DB
    @JsonAlias({ "usuario_reporta", "usuarioReporta" })
    @Column(name = "usuario_reporta")
    private String usuarioReporta;

    public Capacitacion() {}

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getUsuarioReporta() { return usuarioReporta; }
    public void setUsuarioReporta(String usuarioReporta) { this.usuarioReporta = usuarioReporta; }
}
