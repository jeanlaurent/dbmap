package net.morlhon.dbmap.serializer;

import net.morlhon.dbmap.DiskSerializer;

import java.io.*;
import java.util.TreeMap;

public class JDKObjectDiskSerializer implements DiskSerializer {

    @Override
    public void save(TreeMap map, File location) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(location));
        synchronized (map) {
            stream.writeObject(map);
        }
        stream.close();
    }

    @Override
    public TreeMap load(File location) throws IOException {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(location))) {
            return (TreeMap)stream.readObject();
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe);
        }
    }


}
