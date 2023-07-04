package org.hp.springcache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    //模拟数据库中的数据
    private List<Product> data;
    public ProductServiceImpl(){
        data=new ArrayList<>();
        data.add(new Product("1001","水果","苹果","红富士苹果"));
        data.add(new Product("1002","水果","香蕉","香蕉香蕉"));
        data.add(new Product("1003","洗护","洗发水","海飞丝"));
        data.add(new Product("1004","休闲食品","辣条","辣条辣条"));
    }

    @Override
    public List<Product> listAll() {
        log.info("数据库访问：findAll方法");
        return data;
    }

    @Override
    public Product getById(String id) {
        log.info("getById，数据库访问查询id：{}",id);
        for(Product p:data){
            if(p.getProductId().equals(id)){
                return p;
            }
        }
        return null;
    }

    @Override
    public Product getByName(String name) {
        log.info("getByName，数据库访问查询name：{}",name);
        return data.stream().filter(bean -> bean.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void update(Product product) {
        for (Product p : data) {
            if (p.getProductId().equals(product.getProductId())) {
                p.setCategory(product.getCategory());
                p.setName(product.getName());
                p.setDescn(product.getDescn());
            }
        }
    }

    @Override
    public Product update2(Product product) {
        for (Product p : data) {
            if (p.getProductId().equals(product.getProductId())) {
                p.setCategory(product.getCategory());
                p.setName(product.getName());
                p.setDescn(product.getDescn());
            }
        }

        return product;
    }
}
