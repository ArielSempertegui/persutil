package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import net.ausiasmarch.persutil.entity.SalinasEntity;
import net.ausiasmarch.persutil.exception.ResourceNotFoundException;
import net.ausiasmarch.persutil.exception.UnauthorizedException;
import net.ausiasmarch.persutil.repository.SalinasRepository;

@Service
public class SalinasService {
  
    // INYECCIÓN DE DEPENDENCIA DEBE IR AL INICIO
    @Autowired
    SalinasRepository oSalinasRepository;

    @Autowired
    SessionService oSessionService;
    
    public Long bulkCreate(Long amount) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        String[] nombres = {"Paella de marisco", "Tortilla de patatas", "Gazpacho andaluz", "Fideuá", "Tarta de queso casera", "Lentejas estofadas"};
        
        String[] ingredientes = {
            "Arroz bomba, gambas rojas, calamar, mejillones, pollo, pimiento rojo, azafrán, caldo de pescado, aceite de oliva virgen extra, sal.",
            "Patatas, huevos camperos, cebolla blanca, aceite de oliva virgen extra, sal, pimienta negra molida.",
            "Tomates maduros, pepino, pimiento verde, cebolla, pan duro, ajo, vinagre de Jerez, aceite de oliva virgen extra, agua, sal.",
            "Fideos gruesos tipo cabellín, rape, langostinos, almejas, tomate triturado, azafrán, caldo de pescado, ñora, aceite de oliva virgen extra.",
            "Queso crema tipo Philadelphia, nata para montar (35% M.G.), huevos grandes, azúcar, harina de trigo, base de galleta (Digestive) y mantequilla.",
            "Lentejas pardinas, chorizo, tocino, zanahoria, patata, pimiento verde, puerro, cebolla, ajo, pimentón dulce, hoja de laurel, caldo de pollo, aceite de oliva."
        };
        
        String[] preparaciones = {
            "En una paellera, sofreír el pollo y el marisco con aceite. Añadir el pimiento troceado. Incorporar el arroz y el azafrán, rehogando por dos minutos. Verter el caldo de pescado caliente (el doble de volumen que el arroz) y dejar cocer a fuego medio-alto. Tras 10 minutos, reducir el fuego y seguir cociendo hasta que el arroz esté en su punto (unos 20 minutos en total). Dejar reposar 5 minutos tapada.",
            "Cortar las patatas y la cebolla en láminas finas y freírlas lentamente en abundante aceite de oliva hasta que estén blandas. Escurrir bien el aceite. Batir los huevos con sal y pimienta. Mezclar las patatas y la cebolla con el huevo. Cuajar la mezcla en una sartén con un poco de aceite a fuego medio, dándole la vuelta para que se dore por ambos lados hasta alcanzar la textura deseada (más o menos jugosa).",
            "Trocear todos los vegetales y el pan. Colocarlos en una batidora o Thermomix junto al ajo, el aceite de oliva, el vinagre y la sal. Triturar hasta obtener una mezcla muy fina. Si es necesario, añadir un poco de agua fría para aligerar la textura. Pasar por un colador fino (chino) para asegurar que no queden pieles. Probar, rectificar de sal y vinagre, y enfriar en el frigorífico al menos 2 horas antes de servir.",
            "Sofreír el rape y los langostinos en aceite de oliva. Retirar y reservar. En el mismo aceite, rehogar la ñora picada y el tomate triturado. Añadir los fideos y rehogar. Incorporar el caldo de pescado caliente con el azafrán disuelto. Cocer a fuego medio hasta que los fideos estén hechos y hayan absorbido el caldo. Añadir el marisco reservado durante los últimos 5 minutos de cocción. Servir inmediatamente, opcionalmente con alioli.",
            "Precalentar el horno a 180ºC. Triturar las galletas y mezclarlas con la mantequilla derretida para crear la base, y prensarla en un molde desmontable. Para el relleno, batir el queso crema con el azúcar hasta que no haya grumos. Incorporar los huevos uno a uno, y finalmente la nata y la harina, mezclando lo justo. Verter sobre la base de galleta y hornear durante 40-50 minutos. Dejar enfriar lentamente dentro del horno apagado y luego refrigerar al menos 4 horas.",
            "Poner las lentejas a remojo 2 horas antes (opcional). En una olla, sofreír las verduras picadas (cebolla, puerro, pimiento, zanahoria y ajo) con aceite. Añadir el chorizo y el tocino. Incorporar las lentejas, el pimentón y la hoja de laurel. Cubrir con caldo de pollo. Cocer a fuego lento durante 45-60 minutos, o hasta que las lentejas estén tiernas. Añadir las patatas peladas y troceadas a mitad de cocción. Rectificar de sal y dejar reposar 10 minutos."
        }; 
        
        for (long i = 0; i < amount; i++) {
            SalinasEntity receta = new SalinasEntity();
            
            // Se mantiene el nombre sin el contador 'i' para evitar la numeración
            int indice = (int)(Math.random() * nombres.length);
            
            receta.setNombre(nombres[indice]);
            receta.setIngredientes(ingredientes[indice]);
            receta.setPreparacion(preparaciones[indice]); 
            receta.setPublicado(true);
            receta.setFechaCreacion(LocalDateTime.now());
            receta.setFechaModificacion(null);      
            oSalinasRepository.save(receta);
        }
        return oSalinasRepository.count();
    }
  
    // ----------------------------CRUD---------------------------------
    public SalinasEntity get(Long id) {
        if (!oSessionService.isSessionActive()) {
            SalinasEntity receta = oSalinasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Receta not found"));
            if (Boolean.TRUE.equals(receta.isPublicado())) {
                return receta;
            }
            throw new ResourceNotFoundException("Receta not found");
        }
        return oSalinasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta not found"));
    }

    public Long create(SalinasEntity salinasEntity) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        salinasEntity.setFechaCreacion(LocalDateTime.now());
        salinasEntity.setFechaModificacion(null);
        oSalinasRepository.save(salinasEntity);
        return salinasEntity.getId();
    }

    public Long update(SalinasEntity salinasEntity) {
        if (!oSessionService.isSessionActive()) {
                        throw new UnauthorizedException("No active session");
                }
        SalinasEntity existingSalinas = oSalinasRepository.findById(salinasEntity.getId())
                .orElseThrow(() -> new RuntimeException("Receta not found"));
        existingSalinas.setNombre(salinasEntity.getNombre());
        existingSalinas.setIngredientes(salinasEntity.getIngredientes());
        existingSalinas.setPreparacion(salinasEntity.getPreparacion());
        existingSalinas.setFechaModificacion(LocalDateTime.now());
        existingSalinas.setPublicado(salinasEntity.isPublicado());
        oSalinasRepository.save(existingSalinas);
        return existingSalinas.getId();
    }

    public Long delete(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        oSalinasRepository.deleteById(id);
        return id;
    }

    public Page<SalinasEntity> getPage(Pageable oPageable) {
         if (!oSessionService.isSessionActive()) {
            return oSalinasRepository.findByPublicado(true, oPageable);
        }
        return oSalinasRepository.findAll(oPageable);
    }

   

    

    public Long publicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        SalinasEntity existing = oSalinasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salinas not found"));
        existing.setPublicado(true);
        // fechaModificacion se asigna automáticamente con @PreUpdate
        oSalinasRepository.save(existing);
        return existing.getId();
    }

    public Long despublicar(Long id) {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        SalinasEntity existing = oSalinasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salinas not found"));
        existing.setPublicado(false);
        // fechaModificacion se asigna automáticamente con @PreUpdate
        oSalinasRepository.save(existing);
        return existing.getId();
    }



    public Long count() {
        return oSalinasRepository.count();
    }

    // vaciar tabla blog (solo administrador)
    public Long empty() {
        if (!oSessionService.isSessionActive()) {
            throw new UnauthorizedException("No active session");
        }
        Long total = oSalinasRepository.count();
        oSalinasRepository.deleteAll();
        return total;
    }
}
