package mg.itu.framework;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  // Disponible à l'exécution
@Target(ElementType.TYPE)             // Utilisable sur les classes
public @interface Controller {
    String value() default "";        // Nom du contrôleur (optionnel)
}
