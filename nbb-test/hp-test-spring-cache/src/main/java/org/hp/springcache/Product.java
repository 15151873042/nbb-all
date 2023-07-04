package org.hp.springcache;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 产品实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private String productId;
    private String category;
    private String name;
    private String descn;
}
