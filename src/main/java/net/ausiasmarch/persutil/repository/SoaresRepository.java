package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.SoaresEntity;

public interface SoaresRepository extends JpaRepository<SoaresEntity, Long> {
    Page<SoaresEntity> findByPublicacionFalse(Pageable pageable);

    Page<SoaresEntity> findByPublicacionTrue(Pageable oPageable);

    Page<SoaresEntity> findByPreguntasContainingIgnoreCase(String preguntas, Pageable oPageable);

    Page<SoaresEntity> findByPublicacionTrueAndPreguntasContainingIgnoreCase(String preguntas, Pageable oPageable);

    boolean existsByPreguntasIgnoreCase(String preguntas);

    boolean existsByPreguntasIgnoreCaseAndIdNot(String preguntas, Long id);
}
