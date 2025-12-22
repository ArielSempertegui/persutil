package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.ReynaEntity;

public interface ReynaRepository extends JpaRepository<ReynaEntity, Long> {

    Page<ReynaEntity> findByEsPublicaTrue(Pageable oPageable);

    Page<ReynaEntity> findByEsPublicaFalse(Pageable oPageable);

    ReynaEntity findByIdAndEsPublicaTrue(Long id);
}
