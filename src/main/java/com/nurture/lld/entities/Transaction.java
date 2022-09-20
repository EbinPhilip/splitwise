package com.nurture.lld.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {

    private String user1Id;
    private String user2Id;
    private double amount;
}
