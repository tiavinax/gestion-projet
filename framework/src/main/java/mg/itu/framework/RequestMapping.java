package mg.itu.framework;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  // Disponible à l'exécution
@Target(ElementType.METHOD)           // Utilisable sur les méthodes
public @interface RequestMapping {
    String value();                   // L'URL à mapper (ex: "/produit/liste")
}