package net.ausiasmarch.persutil.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.persutil.entity.AlcaldeEntity;

public interface AlcaldeRepository extends JpaRepository<AlcaldeEntity, Long> {

    Page<AlcaldeEntity> findByPublicadoTrue(Pageable pageable);

    List<AlcaldeEntity> findTop6ByPublicadoTrueAndDestacadoTrueOrderByValoracionDesc();

    Page<AlcaldeEntity> findByPublicadoTrueAndDestacadoTrue(Pageable pageable);

    @Query("SELECT DISTINCT a.genero FROM AlcaldeEntity a WHERE a.publicado = true ORDER BY a.genero")
    List<String> findDistinctGeneros();

    @Query("SELECT a FROM AlcaldeEntity a WHERE a.publicado = true AND a.destacado = false AND (:genero IS NULL OR a.genero = :genero) AND a.valoracion >= :minValoracion")
    Page<AlcaldeEntity> findByPublicadoTrueFiltered(String genero, int minValoracion, Pageable pageable);

}
