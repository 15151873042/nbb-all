package org.hp.springcache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SpringCacheTest {

    @Autowired
    private ProductService productService;

    @Test
    public void listAll() {
        List<Product> all = productService.listAll();
        System.out.println(all);
    }

    @Test
    public void getById() {
        Product product = productService.getById("1001");
        Product product2 = productService.getById("1001");
        System.out.println(product);
    }

    @Test
    public void getByName() {
        Product product = productService.getByName("苹果");
        System.out.println(product);
    }

    @Test
    public void update() {
        Product product = new Product("1001", "水果", "火龙果", "红心火龙果");
        productService.update2(product);
    }


}
