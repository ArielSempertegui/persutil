package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.SalinasEntity;

public interface SalinasRepository extends JpaRepository<SalinasEntity, Long>  {

    Page<SalinasEntity> findByPublicado(Boolean publicado, Pageable pageable);
}
