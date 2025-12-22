package net.ausiasmarch.persutil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.CalinescuEntity;

/**
 * Repositorio JPA para la gestión de entidades CalinescuEntity.
 * 
 * Proporciona métodos estándar de JPA además de consultas personalizadas
 * para filtrar por estado de publicación.
 */
public interface CalinescuRepository extends JpaRepository<CalinescuEntity, Long> {

	/**
	 * Busca un item por su ID solo si está publicado.
	 * 
	 * @param id Identificador del item
	 * @return El item si existe y está publicado, null en caso contrario
	 */
	CalinescuEntity findByIdAndPublicadoTrue(Long id);

	/**
	 * Obtiene una página de items publicados.
	 * 
	 * @param pageable Información de paginación y ordenamiento
	 * @return Página de items publicados
	 */
	Page<CalinescuEntity> findByPublicadoTrue(Pageable pageable);


}

