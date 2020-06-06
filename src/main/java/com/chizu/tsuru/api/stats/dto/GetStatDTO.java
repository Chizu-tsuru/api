package com.chizu.tsuru.api.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStatDTO {
    @NotNull
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")
    private String origin;

    @NotNull
    private Date date;

    @NotNull
    private Double executionTime;

    @NotNull
    private int totalResult;

    @NotNull
    private String request;
}
