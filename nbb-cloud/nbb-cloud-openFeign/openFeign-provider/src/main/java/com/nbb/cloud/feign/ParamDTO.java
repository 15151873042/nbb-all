package com.nbb.cloud.feign;

import java.util.List;

public class ParamDTO {

    private List<String> idList;
    private String name;

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParamDTO() {
    }
}
