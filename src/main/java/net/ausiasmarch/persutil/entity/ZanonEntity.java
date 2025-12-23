package net.ausiasmarch.persutil.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "zanon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZanonEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 3, max = 1024)
    private String titulo;
    @NotNull
    @Size(min = 3)
    private String contenido;
    @NotNull
    @Size(min = 3, max = 1024)
    private String etiquetas;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaCreacion;
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaModificacion;
    
    @NotNull
    private Integer duracion;

    public enum Dificultad {
        baja,
        media,
        alta
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private Dificultad dificultad;

    @NotNull
    @BooleanFlag
    private Boolean publico;

    @NotNull
    private String imagen = "https://avena.io/blog/wp-content/uploads/2023/09/Guia-completa-y-efectiva-para-tu-rutina-de-ejercicios-todo-lo-que-necesitas-saber-1.jpg";
}
