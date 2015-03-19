package net.morlhon.dbmap.serializer;

import net.morlhon.dbmap.DiskSerializer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractDiskSerializerTest {
    private static File location = new File("test.map");
    private DiskSerializer serializer;

    protected abstract DiskSerializer buildSerializer();

    @Before
    public void init() {
        serializer = buildSerializer();
        deleteDataFile();
    }

    @AfterClass
    public static void cleanup() {
        deleteDataFile();
    }

    @Test
    public void should_save_and_load() throws IOException {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("foo","bar");
        map.put("qiz","baz");

        serializer.save(map, location);

        map = (TreeMap<String,String>) serializer.load(location);
        assertThat(map).hasSize(2);
        assertThat(map.get("foo")).isEqualTo("bar");
        assertThat(map.get("qiz")).isEqualTo("baz");
    }


    private static void deleteDataFile() {
        if (location.exists()) {
            location.delete();
        }
    }
}
