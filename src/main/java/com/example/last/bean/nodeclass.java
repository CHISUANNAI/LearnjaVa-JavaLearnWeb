package com.example.last.bean;

import lombok.Data;

import java.util.List;

@Data
public class nodeclass {
    private String classname;
    private Integer classnode;
    private List<node> classnodes;
}
