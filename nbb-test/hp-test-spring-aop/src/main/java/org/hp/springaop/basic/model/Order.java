package org.hp.springaop.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {

    private String userName;

    private String product;
}
