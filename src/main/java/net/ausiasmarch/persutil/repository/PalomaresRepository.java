package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.PalomaresEntity;

public interface PalomaresRepository extends JpaRepository<PalomaresEntity, Long> {

    Page<PalomaresEntity> findByPublicadoTrue(Pageable oPageable);

    Page<PalomaresEntity> findByPublicadoFalse(Pageable oPageable);

    PalomaresEntity findByIdAndPublicadoTrue(Long id);

}
