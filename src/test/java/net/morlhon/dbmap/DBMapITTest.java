package net.morlhon.dbmap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DBMapITTest {
    private static File location = new File("test.data");;
    private DBMap<String, String> DBMap;

    @Before
    public void init() {
        DBMap = new DBMap<>();
        deleteDataFile();
    }

    @AfterClass
    public static void cleanup() {
        deleteDataFile();
    }

    @Test
    public void should_save_and_load() throws IOException {
        DBMap.put("foo", "bar");
        DBMap.put("baz", "qiz");

        DBMap.save(location);
        DBMap.load(location);

        assertThat(DBMap.size()).isEqualTo(2);
        assertThat(DBMap.get("foo")).isEqualTo("bar");
        assertThat(DBMap.get("baz")).isEqualTo("qiz");
    }

    private static void deleteDataFile() {
        if (location.exists()) {
            location.delete();
        }
    }

}
