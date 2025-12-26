package net.ausiasmarch.persutil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.ContrerasEntity;

public interface ContrerasRepository extends JpaRepository<ContrerasEntity, Long>{
	// Filtrar x publico
	org.springframework.data.domain.Page<ContrerasEntity> findByPublico(boolean publico, org.springframework.data.domain.Pageable pageable);
}
