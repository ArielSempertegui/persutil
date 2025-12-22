package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.PavonEntity;

public interface PavonRepository extends JpaRepository<PavonEntity, Long>{

    Page<PavonEntity> findByPublicoTrue(Pageable oPageable);

    Page<PavonEntity> findByPublicoFalse(Pageable oPageable);

    PavonEntity findByIdAndPublicoTrue(Long id);
    
}
