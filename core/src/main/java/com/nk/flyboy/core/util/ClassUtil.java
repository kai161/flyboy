package com.nk.flyboy.core.util;


import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by cheris on 2016/9/19.
 */
public class ClassUtil {

    public static List<Class<?>> getClassList(String packageName,boolean isRecursive){
        List<Class<?>> classList=new ArrayList<Class<?>>();

        try{
            Enumeration<URL> urls=Thread.currentThread().getContextClassLoader().getResources(packageName.replaceAll("\\.","/"));
            while (urls.hasMoreElements()){
                URL url=urls.nextElement();
                if(url!=null){
                    String protocol=url.getProtocol();
                    if(protocol.equals("file")){
                        String packagePath=url.getPath();

                    }else if(protocol.equals("jar")){
                        JarURLConnection jarURLConnection=(JarURLConnection)url.openConnection();
                        JarFile jarFile=jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntries=jarFile.entries();
                        while (jarEntries.hasMoreElements()){
                            JarEntry jarEntry=jarEntries.nextElement();
                            String jarEntryName=jarEntry.getName();
                            if(jarEntryName.endsWith(".class")){
                                String className=jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                if(isRecursive||className.substring(0,className.lastIndexOf(".")).equals(packageName)){
                                    classList.add(Class.forName(className));
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){

        }

        return classList;
    }

    public static void addClass(List<Class<?>> classList,String packagePath,String packageName,boolean isRecursive){
        try{
            File[] files=getClassFiles(packagePath);
        }catch (Exception ex){

        }
    }

    private static File[] getClassFiles(String packagePath) {
        return new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return (pathname.isFile()&&pathname.getName().endsWith(".class"))||pathname.isDirectory();
            }
        });
    }

}
