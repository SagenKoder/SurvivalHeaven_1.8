package info.nordbyen.survivalheaven.subplugins.subplugin;

import java.net.*;
import java.lang.reflect.*;
import java.util.zip.*;
import org.bukkit.*;
import info.nordbyen.survivalheaven.*;
import java.util.*;
import java.io.*;

public class AnnoSubPluginLoader
{
    private static final ArrayList<File> jars;
    private static final File folder;
    private static final Class<?>[] parameters;
    
    static {
        jars = new ArrayList<File>();
        folder = new File("./plugins/SurvivalHeaven-CORE/SubPlugins/");
        parameters = new Class[] { URL.class };
    }
    
    public static void addFile(final File f) throws IOException {
        addURL(f.toURI().toURL());
    }
    
    public static void addFile(final String s) throws IOException {
        final File f = new File(s);
        addFile(f);
    }
    
    public static void addURL(final URL u) throws IOException {
        final URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        final Class<URLClassLoader> sysclass = URLClassLoader.class;
        try {
            final Method method = sysclass.getDeclaredMethod("addURL", AnnoSubPluginLoader.parameters);
            method.setAccessible(true);
            method.invoke(sysloader, u);
        }
        catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }
    }
    
    public static List<String> getClassesInJar(final String jarPath) throws IOException {
        final List<String> classNames = new ArrayList<String>();
        final ZipInputStream zip = new ZipInputStream(new FileInputStream(jarPath));
        for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
            if (entry.getName().endsWith(".class") && !entry.isDirectory()) {
                final StringBuilder className = new StringBuilder();
                String[] split;
                for (int length = (split = entry.getName().split("/")).length, i = 0; i < length; ++i) {
                    final String part = split[i];
                    if (className.length() != 0) {
                        className.append(".");
                    }
                    className.append(part);
                    if (part.endsWith(".class")) {
                        className.setLength(className.length() - ".class".length());
                    }
                }
                classNames.add(className.toString());
            }
        }
        zip.close();
        return classNames;
    }
    
    public static void loadJars() throws IOException, ClassNotFoundException {
        AnnoSubPluginLoader.folder.mkdirs();
        File[] listFiles;
        for (int length = (listFiles = AnnoSubPluginLoader.folder.listFiles()).length, i = 0; i < length; ++i) {
            final File file = listFiles[i];
            if (file.getName().endsWith(".jar")) {
                Bukkit.broadcastMessage("Fant en jar fil " + file.getAbsolutePath());
                AnnoSubPluginLoader.jars.add(file);
                addFile(file);
                final List<String> classes = getClassesInJar(file.getAbsolutePath());
                for (final String klassName : classes) {
                    SH.getManager().getAnnoSubPluginManager().addClass(Class.forName(klassName));
                }
            }
        }
    }
    
    public static void testLoadJar(final File f) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        final URLClassLoader urlCl = new URLClassLoader(new URL[] { f.toURI().toURL() }, System.class.getClassLoader());
        for (final String cls : getClassesInJar(f.getAbsolutePath())) {
            final Class<?> main = urlCl.loadClass(cls);
            SH.getManager().getAnnoSubPluginManager().addClass(main);
        }
        urlCl.close();
    }
    
    public static void testLoadJars() {
        File[] listFiles;
        for (int length = (listFiles = AnnoSubPluginLoader.folder.listFiles()).length, i = 0; i < length; ++i) {
            final File file = listFiles[i];
            if (file.getName().endsWith(".jar")) {
                try {
                    testLoadJar(file.getAbsoluteFile());
                }
                catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException ex2) {
                    final Exception ex;
                    final Exception e = ex;
                    e.printStackTrace();
                }
            }
        }
    }
}
