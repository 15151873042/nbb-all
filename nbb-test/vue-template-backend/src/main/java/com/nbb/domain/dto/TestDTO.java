package com.nbb.domain.dto;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TestDTO implements Serializable {

    private String userName;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

    private Date date;
}
