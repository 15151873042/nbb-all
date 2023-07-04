package org.hp.springaop.basic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String firstName;

    private String lastName;

    private Integer age;
}
