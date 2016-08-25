package com.zte.mos.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Scan
{
    public static synchronized  <T> Set<Class<T>> getClasses(String pack, Class<T>... superClass)
    {

        Set<Class<T>> classes = new LinkedHashSet<Class<T>>();
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try
        {
            dirs = Thread.currentThread().getContextClassLoader().getResources(
                    packageDirName);
            while (dirs.hasMoreElements())
            {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol))
                {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath,
                            classes, superClass);
                }
                else if ("jar".equals(protocol))
                {
                    scanJar(classes, packageName, packageDirName, url,
                            superClass);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return classes;
    }

    private static <T> void scanJar(Set<Class<T>> classes, String packageName,
            String packageDirName, URL url, Class<T>[] superClass)
    {
        JarFile jar;
        try
        {
            jar = ((JarURLConnection) url.openConnection())
                    .getJarFile();
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements())
            {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.charAt(0) == '/')
                {
                    name = name.substring(1);
                }
                if (name.startsWith(packageDirName))
                {
                    int idx = name.lastIndexOf('/');
                    if (idx != -1)
                    {
                        packageName = name.substring(0, idx)
                                .replace('/', '.');
                        if (name.endsWith(".class")
                                && !entry.isDirectory())
                        {
                            addClass(packageName, classes,
                                    name.substring(packageName.length() + 1),
                                    superClass);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static <T> void findAndAddClassesInPackageByFile(String packageName,
            String packagePath, Set<Class<T>> classes, Class<T>[] superClass)
    {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory())
        {
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File file)
            {
                return file.isDirectory()
                        || (file.getName().endsWith(".class"));
            }
        });
        for (File file : dirfiles)
        {
            if (file.isDirectory())
            {
                findAndAddClassesInPackageByFile(
                        packageName + "." + file.getName(),
                        file.getAbsolutePath(), classes, superClass);
            }
            else
            {
                addClass(packageName, classes, file.getName(), superClass);
            }
        }
    }

    private static <T> void addClass(String packageName, Set<Class<T>> classes,
            String name,
            Class<T>[] superClass)
    {
        String className =
                name.substring(0, name.length() - ".class".length());
        try
        {
            Class<?> claz =
                    Thread.currentThread().getContextClassLoader().loadClass(
                            packageName + '.' + className);
            if (assignableFrom(superClass, claz))
            {
                classes.add((Class<T>) claz);
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    private static boolean assignableFrom(Class<?>[] superClass, Class<?> claz)
    {
        if (superClass != null && superClass.length > 0)
        {
            for (Class<?> class1 : superClass)
            {
                if (class1 != claz && class1.isAssignableFrom(claz))
                {
                    return true;
                }
            }
        }
        else
        {
            return true;
        }
        return false;
    }
}
