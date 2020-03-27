package com.chizu.tsuru.api.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTagDTO {

    @NotNull
    private String name;
}
