package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.AlcanyizEntity;

public interface AlcanyizRepository extends JpaRepository<AlcanyizEntity, Long> {

    Page<AlcanyizEntity> findByPublicadoTrue(Pageable oPageable);

    Page<AlcanyizEntity> findByPublicadoFalse(Pageable oPageable);

    AlcanyizEntity findByIdAndPublicadoTrue(Long id);

}
