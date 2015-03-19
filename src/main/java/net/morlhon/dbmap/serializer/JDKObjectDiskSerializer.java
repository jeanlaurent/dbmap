package net.morlhon.dbmap.serializer;

import net.morlhon.dbmap.DiskSerializer;

import java.io.*;
import java.util.SortedMap;

public class JDKObjectDiskSerializer implements DiskSerializer {

    @Override
    public void save(SortedMap map, File location) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(location));
        synchronized (map) {
            stream.writeObject(map);
        }
        stream.close();
    }

    @Override
    public SortedMap load(File location) throws IOException {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(location))) {
            return (SortedMap)stream.readObject();
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe);
        }
    }


}
