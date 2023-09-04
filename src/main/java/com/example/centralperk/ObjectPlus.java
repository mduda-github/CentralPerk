package com.example.centralperk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class ObjectPlus implements Serializable {
    private static Map<Class, List<ObjectPlus>> allExtents = new Hashtable();

    public ObjectPlus() {
        List<ObjectPlus> extent = null;
        Class theClass = this.getClass();

        if(allExtents.containsKey(theClass)){
            extent = allExtents.get(theClass);
        } else {
            extent = new ArrayList<>();
            allExtents.put(theClass, extent);
        }
        extent.add(this);
    }

    public static void writeExtents (ObjectOutputStream stream) throws IOException {
        stream.writeObject(allExtents);
    }

    public static void readExtents (ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allExtents = (Hashtable) stream.readObject();
    }

    public static <T> Collection<T> getExtent(Class<T> type) throws ClassNotFoundException {
        if (allExtents.containsKey(type)) {
            return (Collection<T>) allExtents.get(type);
        }
        throw new ClassNotFoundException(
                String.format("%s. Stored extents: %s", type.toString(), allExtents.keySet())
        );
    }

    public static void showExtent(Class theClass) throws Exception {
        List<ObjectPlus> extent = null;

        if(allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        } else {
            throw new Exception("Unknown class " + theClass);
        }

        System.out.println("Extent of the class: " + theClass.getSimpleName());

        for (Object obj : extent) {
            System.out.println(obj);
        }

    }

}
