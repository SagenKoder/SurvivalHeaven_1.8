package info.nordbyen.survivalheaven.api.subplugin.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface SurvivalHeavenSubPlugin {
    String name();
}
