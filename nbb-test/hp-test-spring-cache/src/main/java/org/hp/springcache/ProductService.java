package org.hp.springcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ProductService {

    @Cacheable(cacheNames = "product", key="#root.methodName")
    List<Product> listAll();

    @Cacheable(cacheNames = {"product"}, key = "#root.methodName+'['+#id+']'")
    Product getById(String id);

    @Cacheable(cacheNames = {"product"}, keyGenerator = "myKeyGenerator")
    Product getByName(String name);

    @CacheEvict(cacheNames = {"product"}, key = "'getById['+#product.productId+']'")
    void update(Product product);

    @CachePut(cacheNames = {"product"}, key = "'getById['+#product.productId+']'")
    Product update2(Product product);


}
