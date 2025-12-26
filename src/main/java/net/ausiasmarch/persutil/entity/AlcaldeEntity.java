package net.ausiasmarch.persutil.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alcalde")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlcaldeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 255)
    private String titulo;

    @NotNull
    @Size(min = 2, max = 255)
    private String autor;

    @NotNull
    @Size(min = 2, max = 100)
    private String genero;

    @NotNull
    @Size(min = 10)
    private String reseña;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer valoracion;

    @NotNull
    private Boolean publicado;

    @NotNull
    private Boolean destacado;

    @Column(name = "fecha_creacion", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
