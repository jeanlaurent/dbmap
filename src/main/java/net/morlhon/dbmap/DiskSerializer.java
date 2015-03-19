package net.morlhon.dbmap;

import java.io.File;
import java.io.IOException;
import java.util.SortedMap;

public interface DiskSerializer {
    void save(SortedMap map, File location) throws IOException;

    SortedMap load(File location) throws IOException;
}
