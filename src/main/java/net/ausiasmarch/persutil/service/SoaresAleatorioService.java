package net.ausiasmarch.persutil.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoaresAleatorioService {

    @Autowired
    AleatorioService oAleatorioService;

    // No hice con java separe archivo para facilitarme el mantenimiento
    private final String[] PREGUNTAS_NEURO = {
        "Si la mente es un universo, ¿cuál es la estrella que guía tu conciencia?",
        "¿Cómo se reescribe el guion de un recuerdo que ya no te sirve?",
        "¿Qué patrón neuronal se activa cuando experimentas la 'belleza' de una idea?",
        "Si el cerebro es una red, ¿cuál es el nodo más resistente a la entropía?",
        "¿Qué sesgo cognitivo te impide ver la solución más obvia a tu problema actual?",
        "¿De qué manera tu neuroplasticidad está moldeando tu futuro en este instante?",
        "¿Cuál es el 'efecto placebo' que podrías aplicar conscientemente a tu vida?",
        "Si tus emociones son datos, ¿qué algoritmo utiliza tu mente para procesarlos?",
        "¿Cómo influye la arquitectura de tu sueño en la consolidación de tu identidad?",
        "¿Qué 'neurona espejo' te conecta más profundamente con el sufrimiento ajeno?",
        "¿Qué significa realmente 'ser' en un universo de constante cambio?",
        "Si pudieras hablar con tu yo de hace diez años, ¿qué le dirías sobre la percepción?",
        "¿Es la conciencia un subproducto o el fundamento de la realidad?",
        "¿Cómo afecta la gravedad de un agujero negro a la relatividad de tu tiempo personal?",
        "Si el universo es finito, ¿qué hay más allá de sus límites?",
        "¿Es el libre albedrío una ilusión generada por la complejidad neuronal?",
        "¿Qué melodía cerebral se reproduce cuando sientes una profunda conexión?",
        "¿Cómo se almacena el asombro en la memoria a largo plazo?",
        "Si el tiempo es una dimensión, ¿por qué solo podemos percibirlo en una dirección?",
        "¿Cuál es el límite entre la intuición y el procesamiento subconsciente de datos?",
        "¿Podría la empatía ser la clave para la próxima etapa de la evolución humana?",
        "Si cada pensamiento es una descarga eléctrica, ¿cuál es el voltaje de la iluminación?",
        "¿Cómo se relaciona la expansión del universo con tu propia sensación de crecimiento?",
        "¿Qué lenguaje utiliza el silencio en tu diálogo interno?",
        "Si la realidad es subjetiva, ¿qué porcentaje compartimos con los demás?",
        "¿Cuál es el propósito de la procrastinación desde una perspectiva neurobiológica?",
        "¿Cómo se manifiesta la incertidumbre cuántica en las decisiones cotidianas?",
        "Si la felicidad es química, ¿cómo se sintetiza la trascendencia?",
        "¿Qué tipo de energía se requiere para sostener una creencia inquebrantable?",
        "¿Es el olvido un error del sistema o un mecanismo de supervivencia esencial?",
        "¿Cómo se negocia la mente entre el deseo de estabilidad y la necesidad de aventura?",
        "Si la información es la moneda del universo, ¿cuál es tu tasa de cambio?",
        "¿Qué tan lejos puedes viajar en tu mente antes de que la realidad te reclame?",
        "¿Cuál es el sonido de una pregunta sin respuesta?",
        "Si el cerebro es un simulador, ¿quién es el operador?",
        "¿Cómo se mide la calidad de una vida en términos de conexiones sinápticas?",
        "¿Es la nostalgia una forma de viaje en el tiempo emocional?",
        "Si el destino existe, ¿dónde reside el punto de inflexión?",
        "¿Qué sucede con la energía de un pensamiento que nunca se expresa?",
        "¿Cómo se construye la esperanza a partir de la experiencia pasada?",
        "Si el universo nos observa, ¿qué historia está contando sobre nosotros?",
        "¿Cuál es el color de la verdad absoluta?",
        "¿Cómo se reconcilia la finitud de la vida con la infinitud del cosmos?",
        "Si la inteligencia artificial es el espejo de la mente, ¿qué nos revela?",
        "¿Qué parte de ti sigue siendo un misterio para ti mismo?",
        "¿Cómo se siente la rendición total en el cuerpo?",
        "Si la perfección es inalcanzable, ¿por qué la buscamos?",
        "¿Cuál es el patrón fractal que define tu camino de vida?",
        "¿Cómo se transforma el miedo en acción constructiva?",
        "Si la vida es un sueño, ¿cuándo es el momento de despertar?",
        "¿Qué pregunta te da más miedo responder?"
    };
//
    public String getPreguntaNeuro() {
        return PREGUNTAS_NEURO[oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(0, PREGUNTAS_NEURO.length - 1)];
    }
    // Fecha de creación entre 1 y 365 días atrás
    public LocalDateTime getFechaCreacion() {
        return LocalDateTime.now().minusDays(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(1, 365));
    }

    public LocalDateTime getFechaModificacion(LocalDateTime fechaCreacion) {
        return fechaCreacion.plusHours(oAleatorioService.GenerarNumeroAleatorioEnteroEnRango(1, 24 * 30));
    }

}
