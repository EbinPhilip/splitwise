package com.nurture.lld.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nurture.lld.entities.Transaction;

public class TransactionRepository {

    private Map<String, List<Transaction>> userToTransactionMap = new HashMap<>();

    public void addTransaction(Transaction transaction) {

        // add transaction to user1 map
        if (userToTransactionMap.containsKey(transaction.getUser1Id())) {
            userToTransactionMap.get(transaction.getUser1Id()).add(transaction);
        } else {
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            userToTransactionMap.put(transaction.getUser1Id(), transactions);
        }

        // add tranaction to user2 map
        if (userToTransactionMap.containsKey(transaction.getUser2Id())) {
            userToTransactionMap.get(transaction.getUser2Id()).add(transaction);
        } else {
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            userToTransactionMap.put(transaction.getUser2Id(), transactions);
        }
    }

    public List<Transaction> getTransactions(String userId) {
        List<Transaction> transactions = userToTransactionMap.get(userId);
        if (transactions != null) {
            return new ArrayList<>(transactions);
        } else {
            return new ArrayList<>();
        }
    }
}
