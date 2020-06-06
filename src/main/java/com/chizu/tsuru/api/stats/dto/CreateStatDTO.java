package com.chizu.tsuru.api.stats.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class CreateStatDTO {
    @NotNull
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")
    private String origin;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME  )
    private Date date;

    @NotNull
    private Double executionTime;

    @NotNull
    private int totalResult;

    @NotNull
    private String request;
}
