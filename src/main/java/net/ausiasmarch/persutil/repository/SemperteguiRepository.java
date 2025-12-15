package net.ausiasmarch.persutil.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.SemperteguiEntity;

public interface SemperteguiRepository extends JpaRepository<SemperteguiEntity, Long> {

    Page<SemperteguiEntity> findByPublicadoTrue(Pageable oPageable);

    Page<SemperteguiEntity> findByPublicadoFalse(Pageable oPageable);

    Optional<SemperteguiEntity> findByIdAndPublicadoTrue(Long id);
}