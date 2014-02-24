package com.mobivery.utils;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Marshaller that could be used by any Service Generator generated DTO
 *
 * @author Service Generator
 */
public class ServiceGeneratorDTOMarshal<Q> implements DataTransferObjectJSONMarshallingInterface<Q> {

    private Class dtoClass;
    private Class daoClass;
    private Method getInstance;

    public ServiceGeneratorDTOMarshal(Class dtoClass) {
        String className = dtoClass.getSimpleName();
        String daoClassName = replaceLast(className, "DTO", "DAO");
        try {
            daoClass = Class.forName(daoClassName);
            init(dtoClass, daoClass);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Could not find " + daoClassName + " for DTO " + className);
        }
    }

    public ServiceGeneratorDTOMarshal(Class dtoClass, Class daoClass) {
        init(dtoClass, daoClass);
    }

    private void init(Class dtoClass, Class daoClass) {
        this.dtoClass = dtoClass;
        this.daoClass = daoClass;
        try {
            this.getInstance = daoClass.getMethod("getInstance");
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Could not access getInstance method on " + daoClass.getSimpleName());
        }

    }

    @Override
    public JSONObject serialize(Q object) {
        try {
            Object daoInstance = getInstance.invoke(null);
            Method serialize = daoInstance.getClass().getMethod("serialize", dtoClass);
            return (JSONObject) serialize.invoke(daoInstance, object);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Q deserialize(JSONObject jsonObject) {

        try {
            Object daoInstance = getInstance.invoke(null);
            Method create = daoInstance.getClass().getMethod("create", JSONObject.class);
            return (Q) create.invoke(daoInstance, jsonObject);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String replaceLast(String string, String from, String to) {
        int lastIndex = string.lastIndexOf(from);
        if (lastIndex < 0) {
            return string;
        }
        String tail = string.substring(lastIndex).replaceFirst(from, to);
        return string.substring(0, lastIndex) + tail;
    }
}
