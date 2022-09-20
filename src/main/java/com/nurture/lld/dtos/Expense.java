package com.nurture.lld.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@Builder
public class Expense {
    @NonNull
    private String paidByUser;

    @NonNull
    private SplitType splitType;

    @NonNull
    private Double amount;

    @NonNull
    private List<String> splitBetweenUsers;

    private List<Double> splitDetails;
}
