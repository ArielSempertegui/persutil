package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.ZanonEntity;

public interface ZanonRepository extends JpaRepository<ZanonEntity, Long> {

    Page<ZanonEntity> findByPublicoTrue(Pageable oPageable);
    Page<ZanonEntity> findByPublicoFalse(Pageable oPageable);
    ZanonEntity findByIdAndPublicoTrue(Long id);
}
