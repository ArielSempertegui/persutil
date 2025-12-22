package net.ausiasmarch.persutil.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa un item de la lista de compra.
 * 
 * Esta clase mapea la tabla 'calinesculistacompra' de la base de datos.
 * Incluye validaciones para garantizar la integridad de los datos y
 * formato específico para las fechas en JSON.
 */
@Entity
@Table(name = "calinesculistacompra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CalinescuEntity {
    
    /** Identificador único del item */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    /** Nombre del producto (mínimo 3, máximo 255 caracteres) */
    @NotNull
    @Size(min = 3, max = 255)
    private String nombre;
    
    /** Descripción o contenido del item (mínimo 3 caracteres) */
    @NotNull
    @Size(min = 3)
    private String contenido;

    /** Fecha esperada para realizar la compra */
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "fechaCompraEsperada")
    private LocalDateTime fecha_compra_esperada;

    /** Fecha de creación del registro (establecida automáticamente) */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime fecha_creacion;
    
    /** Fecha de la última modificación del registro */
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime fecha_modificacion;

    /** Indica si el item está publicado y visible para usuarios públicos */
    @NotNull
    private boolean publicado=true;

    /** Precio estimado del producto */
    @Nullable
    private Double precio = 0.0;

    /** Cantidad de unidades del producto */
    @NotNull
    private Integer cantidad = 1;

}
