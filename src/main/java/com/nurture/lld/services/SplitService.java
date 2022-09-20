package com.nurture.lld.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.nurture.lld.dtos.Balance;
import com.nurture.lld.dtos.Expense;
import com.nurture.lld.entities.Transaction;
import com.nurture.lld.repositories.TransactionRepository;
import com.nurture.lld.repositories.UserRepository;
import com.nurture.lld.services.splitstrategy.SplitStrategy;
import com.nurture.lld.services.splitstrategy.SplitStrategyFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SplitService {

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private SplitStrategyFactory splitStrategyFactory;

    public void addExpense(Expense expense) {
        SplitStrategy strategy = splitStrategyFactory.getStrategy(expense);

        // get all transactions using strategy and save in transaction repository
        List<Transaction> transactions = strategy.getTransactionsFromExpense(expense);
        for(Transaction transaction : transactions) {
            transactionRepository.addTransaction(transaction);
        }

        // add all users specified in expense to user repository
        userRepository.addUserIfNotPresent(expense.getPaidByUser());
        for(String user : expense.getSplitBetweenUsers()) {
            userRepository.addUserIfNotPresent(user);
        }
    }

    public Balance getBalanceForUser(String userId) {
        List<Transaction> transactions = transactionRepository.getTransactions(userId);
        if (transactions.isEmpty()) {
            return null;
        }

        // calculate amount owedBy/owesTo each user
        Map<String, Double> balanceMap = new HashMap<>();
        for(Transaction transaction : transactions) {
            Double amount = 0.0;
            String user;
            if (transaction.getUser2Id().equals(userId)) {
                amount = transaction.getAmount();
                user = transaction.getUser1Id();
            } else {
                amount = -transaction.getAmount();
                user = transaction.getUser2Id();
            }
            if (balanceMap.containsKey(user)) {
                balanceMap.put(user, balanceMap.get(user) + amount);
            } else {
                balanceMap.put(user, amount);
            }
        }

        // separate owedBy and owesTo into two sorted groups
        TreeMap<String, Double> owesToUsers = new TreeMap<>();
        TreeMap<String, Double> owedByUsers = new TreeMap<>();
        for (Map.Entry<String, Double> entry : balanceMap.entrySet()) {
            if (entry.getValue() == 0) {
                continue;
            } else if (entry.getValue() < 0) {
                owesToUsers.put(entry.getKey(), -entry.getValue());
            } else {
                owedByUsers.put(entry.getKey(), entry.getValue());
            }
        }

        Balance balance = new Balance(userId, owesToUsers, owedByUsers);
        return balance;
    }

    public List<Balance> getAllBalances() {
        Set<String> users = userRepository.getAllUsers();

        List<Balance> balances = new ArrayList<>();
        for(String user : users) {
            Balance balance = getBalanceForUser(user);
            if (balance == null) {
                continue;
            }
            balances.add(balance);
        }

        // since we are getting balances of all users
        // otherwise, we would see repetitions
        for(Balance balance : balances) {
            balance.getOwedByUsers().clear();
        }
        
        return balances;
    }
}
