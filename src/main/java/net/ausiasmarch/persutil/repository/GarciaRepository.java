package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.GarciaEntity;

public interface GarciaRepository extends JpaRepository<GarciaEntity, Long> {
    Page<GarciaEntity> findByPublico(Boolean publicado, Pageable pageable);
}
