package com.example.apiexception.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDto {
    private Integer number;
    private String name;
}
