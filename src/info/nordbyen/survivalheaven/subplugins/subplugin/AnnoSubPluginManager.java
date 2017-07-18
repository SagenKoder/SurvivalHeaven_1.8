package info.nordbyen.survivalheaven.subplugins.subplugin;

import info.nordbyen.survivalheaven.api.subplugin.*;
import org.bukkit.*;
import java.lang.annotation.*;
import info.nordbyen.survivalheaven.*;
import java.lang.reflect.*;
import info.nordbyen.survivalheaven.api.subplugin.annotations.*;
import java.util.*;

public class AnnoSubPluginManager implements IAnnoSubPluginManager
{
    private static final HashMap<Class<?>, Boolean> classes;
    private static boolean enabled;
    
    static {
        classes = new HashMap<Class<?>, Boolean>();
        AnnoSubPluginManager.enabled = false;
    }
    
    @Override
    public void addClass(final Class<?> klass) {
        final SurvivalHeavenSubPlugin subPluginAnno = klass.getAnnotation(SurvivalHeavenSubPlugin.class);
        if (subPluginAnno == null) {
            return;
        }
        if (AnnoSubPluginManager.classes.containsKey(klass)) {
            return;
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "Registrerer " + ChatColor.GOLD + subPluginAnno.name());
        AnnoSubPluginManager.classes.put(klass, false);
        if (AnnoSubPluginManager.enabled) {
            this.enableClass(klass);
            AnnoSubPluginManager.classes.put(klass, true);
        }
    }
    
    @Override
    public void disableAll() {
        for (final Class<?> klass : AnnoSubPluginManager.classes.keySet()) {
            if (AnnoSubPluginManager.classes.get(klass)) {
                this.disableClass(klass);
            }
        }
        AnnoSubPluginManager.enabled = false;
    }
    
    private void disableClass(final Class<?> klass) {
        try {
            final SurvivalHeavenSubPlugin subPluginAnno = klass.getAnnotation(SurvivalHeavenSubPlugin.class);
            if (subPluginAnno == null) {
                return;
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "Starter disabling av " + ChatColor.GOLD + subPluginAnno.name());
            final HashMap<Method, Annotation> methodAnnotTypes = this.getMethodsAnnotatedWith(klass, SurvivalHeavenDisable.class);
            for (final Map.Entry<Method, Annotation> entry : methodAnnotTypes.entrySet()) {
                final Method method = entry.getKey();
                method.setAccessible(true);
                try {
                    method.invoke(null, SH.getPlugin());
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex2) {
                    final Exception ex;
                    final Exception e = ex;
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    @Override
    public void enableAll() {
        for (final Class<?> klass : AnnoSubPluginManager.classes.keySet()) {
            if (!AnnoSubPluginManager.classes.get(klass)) {
                this.enableClass(klass);
            }
        }
        AnnoSubPluginManager.enabled = true;
    }
    
    private void enableClass(final Class<?> klass) {
        try {
            final SurvivalHeavenSubPlugin subPluginAnno = klass.getAnnotation(SurvivalHeavenSubPlugin.class);
            if (subPluginAnno == null) {
                return;
            }
            Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "Starter enabling av " + ChatColor.GOLD + subPluginAnno.name());
            final HashMap<Method, Annotation> methodAnnotTypes = this.getMethodsAnnotatedWith(klass, SurvivalHeavenEnable.class);
            for (final Map.Entry<Method, Annotation> entry : methodAnnotTypes.entrySet()) {
                final Method method = entry.getKey();
                method.setAccessible(true);
                try {
                    method.invoke(null, SH.getPlugin());
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex2) {
                    final Exception ex;
                    final Exception e = ex;
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private HashMap<Method, Annotation> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final HashMap<Method, Annotation> methods = new HashMap<Method, Annotation>();
        for (Class<?> klass = type; klass != Object.class; klass = klass.getSuperclass()) {
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (annotation == null || method.isAnnotationPresent(annotation)) {
                    final Annotation annotInstance = method.getAnnotation(annotation);
                    methods.put(method, annotInstance);
                }
            }
        }
        return methods;
    }
    
    @Override
    public void removeClass(final Class<?> klass) {
        if (!AnnoSubPluginManager.classes.containsKey(klass)) {
            return;
        }
        if (AnnoSubPluginManager.classes.get(klass)) {
            this.disableClass(klass);
        }
        AnnoSubPluginManager.classes.remove(klass);
    }
}
