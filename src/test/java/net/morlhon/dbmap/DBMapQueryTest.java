package net.morlhon.dbmap;

import lombok.EqualsAndHashCode;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DBMapQueryTest {
    private DBMap<String, Product> dbMap;
    private Product foo, bar, qix;

    @Before
    public void init() {
        foo = new Product("foo", "this is foo", 42);
        bar = new Product("bar", "this is bar", 1);
        qix = new Product("qix", "this is qix", 10);
        dbMap = new DBMap<>();
        dbMap.put(foo.name, foo);
        dbMap.put(bar.name, bar);
        dbMap.put(qix.name, qix);
    }

    @Test
    public void should_find_one() {
        Product found = dbMap.findOne( (p) -> "foo".equals(p.name)).get();
        assertThat(found).isEqualTo(foo);
    }

    @Test
    public void should_find_many() {
        List<Product> products = dbMap.find( (p) -> p.price > 5 );
        assertThat(products).containsOnly(foo, qix);
    }

    @Test
    public void should_update() {
        dbMap.findAndUpdate( (p) -> p.price > 5 , (p) -> p.price += 1);
        assertThat(dbMap.values()).extracting("price").containsExactly(1,43,11);
    }

    @Test
    public void should_delete() {
        dbMap.delete((p) -> p.price == 1);
        assertThat(dbMap.values()).containsOnly(foo, qix);
    }


    @EqualsAndHashCode
    public class Product {
        public String name;
        public int price;
        public String description;

        public Product(String name, String description, int price) {
            this.description = description;
            this.name = name;
            this.price = price;
        }

    }

}
