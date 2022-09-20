package com.nurture.lld.commands;

import java.util.List;
import java.util.Map;

import com.nurture.lld.dtos.Balance;
import com.nurture.lld.services.SplitService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowCommand implements Command {

    private SplitService splitService;

    private void printBalance(Balance balance) {
        String user = balance.getUserId();
        for(Map.Entry<String, Double> entry : balance.getOwesToUsers().entrySet()) {
            System.out.println(String.format("%s owes %s:%s", user,
                    entry.getKey(), String.valueOf(entry.getValue())));
        }
        for(Map.Entry<String, Double> entry : balance.getOwedByUsers().entrySet()) {
            System.out.println(String.format("%s owes %s:%s", 
                    entry.getKey(), user, String.valueOf(entry.getValue())));
        }
    }

    public void execute(List<String> inputs) {
        try {
            String userId = inputs.get(1).trim();
            if (userId.equals("all")) {
                List<Balance> balances = splitService.getAllBalances();
                for(Balance balance : balances) {
                    printBalance(balance);
                }
                if (balances.isEmpty()) {
                    System.out.println("No balances");
                }
                System.out.println("");
            } else {
                Balance balance = splitService.getBalanceForUser(userId);
                if (balance == null) {
                    System.out.println("No balances");
                    System.out.println("");
                    return;
                }
                printBalance(balance);
                System.out.println("");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("not enough parameters specified");
        }
    }
}
