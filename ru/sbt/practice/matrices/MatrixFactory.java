package ru.sbt.practice.matrices;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by artem on 10.11.14.
 */
public class MatrixFactory {
    private static Object factory(Class factoryClass,  Class[] argTypes,  Object[] arguments) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = factoryClass.getDeclaredConstructor(argTypes);
        Object instance = constructor.newInstance(arguments);
        return instance;
    }

    public static Matrix create(Class resultClass, int nLines, int fooColumns) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] argTypes = {int.class, int.class};
        Object[] arguments = {nLines, fooColumns};
        return (Matrix)factory(resultClass, argTypes, arguments);
    }

    public static Vector createVector(Class vectorClass, int nColumns) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] argTypes = {int.class};
        Object[] arguments = {nColumns};
        return (Vector)factory(vectorClass, argTypes, arguments);
    }
}