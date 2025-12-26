package net.ausiasmarch.persutil.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nullable;
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

@Entity
@Table(name = "jspreguntas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlcanyizEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 3, max = 1024)
    private String question;

    @NotNull
    @Size(max = 1024)
    private String answer1;

    @NotNull
    @Size(max = 1024)
    private String answer2;

    @NotNull
    @Size(max = 1024)
    private String answer3;

    @NotNull
    @Size(max = 1024)
    private String answer4;

    @NotNull
    private long correct;

    @NotNull
    private Boolean publicado;

    @NotNull
    @Size(max = 256)
    private String tema;

    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime create_date;
    
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime modify_date;
}