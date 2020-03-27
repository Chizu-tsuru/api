package com.chizu.tsuru.api.DTO;

import lombok.Data;

import javax.swing.*;
import java.util.List;

@Data
public class GetWorkspaceDTO {

    public Spring name;
    public List<String> clusters;

}
