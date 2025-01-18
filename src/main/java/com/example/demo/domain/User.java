package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
public class User {
    @Id
    private Long id;
    private String name;
    private String email;

}
