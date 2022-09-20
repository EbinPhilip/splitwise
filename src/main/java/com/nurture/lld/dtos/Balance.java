package com.nurture.lld.dtos;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Balance {
    private String userId;
    private Map<String, Double> owesToUsers;
    private Map<String, Double> owedByUsers;
}
