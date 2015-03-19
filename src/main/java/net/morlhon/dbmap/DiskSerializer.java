package net.morlhon.dbmap;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public interface DiskSerializer {
    void save(TreeMap map, File location) throws IOException;

    TreeMap load(File location) throws IOException;
}
