package info.nordbyen.survivalheaven.api.util;

import java.lang.reflect.*;

public class ReflectionUtil
{
    public static Object getNewInstanceOf(final String className) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        Object instance = null;
        final Class<?> cls = Class.forName(className);
        final Constructor[] constructors = cls.getDeclaredConstructors();
        constructors[0].setAccessible(true);
        instance = constructors[0].newInstance(new Object[0]);
        return instance;
    }
}
