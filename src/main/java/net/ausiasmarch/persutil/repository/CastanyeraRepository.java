package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.CastanyeraEntity;

public interface CastanyeraRepository extends JpaRepository<CastanyeraEntity, Long> {


	Page<CastanyeraEntity> findByPublicoTrue(Pageable oPageable);

    Page<CastanyeraEntity> findByPublicoFalse(Pageable oPageable);

    CastanyeraEntity findByIdAndPublicoTrue(Long id);

}
