package net.morlhon.dbmap.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import net.morlhon.dbmap.DiskSerializer;

import java.io.File;
import java.io.IOException;
import java.util.SortedMap;

public class JSONDiskSerializer implements DiskSerializer {
    private final ObjectMapper objectMapper;

    public JSONDiskSerializer(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }

    public JSONDiskSerializer() {
        objectMapper = new ObjectMapper().registerModule(new AfterburnerModule());
    }

    @Override
    public void save(SortedMap map, File location) throws IOException {
        objectMapper.writeValue(location, map);
    }

    @Override
    public SortedMap load(File location) throws IOException {
        return objectMapper.readValue(location, SortedMap.class);
    }
}
