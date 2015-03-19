package net.morlhon.dbmap.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.morlhon.dbmap.DiskSerializer;
import org.junit.Test;

import java.io.IOException;
import java.util.TreeMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JSONDiskSerializerTest extends AbstractDiskSerializerTest {

    @Override
    protected DiskSerializer buildSerializer() {
        return new JSONDiskSerializer();
    }

    @Test
    public void should_override_objectmapper() throws IOException {
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        JSONDiskSerializer serializer = new JSONDiskSerializer(objectMapper);
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("foo", "bar");
        serializer.save(treeMap, location);
        verify(objectMapper).writeValue(location, treeMap);
    }

}