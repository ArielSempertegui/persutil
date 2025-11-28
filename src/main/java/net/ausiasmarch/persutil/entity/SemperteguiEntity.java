package net.ausiasmarch.persutil.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sempertegui_pelicula")
@Data
@NoArgsConstructor
public class SemperteguiEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min=2, max=255)
    private String nombre;

    @NotNull
    @Size(min=3, max=255)
    private String genero;

    @NotNull
    @Size(min=3, max=255)
    private String director;

    @NotNull
    private int puntuacion;

    @NotNull
    @Min(1901)
    @Max(2155)
    private int anyo;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape=JsonFormat.Shape.STRING)
    private LocalDateTime fechaCreacion;

    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape=JsonFormat.Shape.STRING)
    private LocalDateTime fechaModificacion;

    // @NotNull
    // @Lob
    // private byte[] portada;

}
