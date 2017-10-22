package com.jvm;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by double on 17-8-17.
 */
public class ClassLoaderTest {

    public static void main(String[] args)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader loder = new ClassLoader() {
            @Override public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream resourceAsStream = getClass().getResourceAsStream(filename);

                    if (resourceAsStream == null) {
                        System.out.println("resourceAsStream == null " + super.loadClass(name));
                        return super.loadClass(name);
                    }

                    byte[] b = new byte[resourceAsStream.available()];
                    resourceAsStream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };



        Object o = loder.loadClass("com.jvm.ClassLoaderTest").newInstance();
        System.out.println("1.  " + o.getClass());
        Class<?> myclass = o.getClass();

        ClassLoaderTest classLoaderTest = new ClassLoaderTest();
        Class<? extends ClassLoaderTest> classLoaderTestClass = classLoaderTest.getClass();
        System.out.println("2    " + myclass.equals(classLoaderTestClass));

        ClassLoader parent = loder.getParent();
        System.out.println("3.   "+parent);

        ClassLoader classLoader = classLoaderTestClass.getClassLoader();
        System.out.println("4.  " +classLoader);

        System.out.println("5." + myclass.getClassLoader());

    }
}
