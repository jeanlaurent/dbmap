package net.morlhon.dbmap.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import net.morlhon.dbmap.DiskSerializer;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class JSONDiskSerializer implements DiskSerializer {
    private final ObjectMapper objectMapper;

    public JSONDiskSerializer(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }

    public JSONDiskSerializer() {
        objectMapper = new ObjectMapper().registerModule(new AfterburnerModule());
    }

    @Override
    public void save(TreeMap map, File location) throws IOException {
        objectMapper.writeValue(location, map);
    }

    @Override
    public TreeMap load(File location) throws IOException {
        return objectMapper.readValue(location, TreeMap.class);
    }
}
