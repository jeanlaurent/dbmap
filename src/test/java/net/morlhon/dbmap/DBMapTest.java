package net.morlhon.dbmap;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DBMapTest {

    private DBMap<String, Double> map;
    private File location;
    private DiskSerializer serializer;

    @Before
    public void init() {
        serializer = mock(DiskSerializer.class);
        map = new DBMap<>(serializer);
        location = new File("wedont.care");
    }

    @Test
    public void should_allow_put() {
        map.put("foo", 12d);

        assertThat(map).hasSize(1);

        map.put("bar", 13d);

        assertThat(map).hasSize(2);
    }


    @Test
    public void should_allow_get() {
        map.put("foo", 12d);

        assertThat(map.get("foo")).isEqualTo(12d);
    }

    @Test
    public void should_create_a_simple_map() {
        map.put("foo", 12d);
        map.put("bar", 13d);
        map.put("qix", 14d);

        assertThat(map.get("foo")).isEqualTo(12d);
    }

    @Test
    public void should_save_and_load() throws IOException {
        map.put("foo", 12d);
        map.put("bar", 13d);
        map.put("qix", 14d);

        map.save(location);
        map.load(location);

        verify(serializer).save(anyObject(), eq(location));
        verify(serializer).load(location);
    }

    @Test
    public void element_are_sorted_by_key() {
        map.put("foo", 12d);
        map.put("bar", 13d);
        map.put("qix", 14d);

        assertThat(map.keySet()).containsExactly("bar", "foo", "qix");
    }

    @Test
    public void element_are_sorted_by_comparator() {
        map = new DBMap<>(serializer, (s1, s2) -> s1.compareTo(s2) * -1);
        map.put("foo", 12d);
        map.put("bar", 13d);
        map.put("qix", 14d);

        assertThat(map.keySet()).containsExactly("qix", "foo", "bar");
    }


}