package com.nurture.lld.services.splitstrategy;

import java.util.ArrayList;
import java.util.List;

import com.nurture.lld.dtos.Expense;
import com.nurture.lld.entities.Transaction;

public class EqualSplit implements SplitStrategy {

    public List<Transaction> getTransactionsFromExpense(Expense expense) {

        String payer = expense.getPaidByUser();
        int splitNumber = expense.getSplitBetweenUsers().size();
        double amount = expense.getAmount()/((double)splitNumber);

        List<Transaction> transactions = new ArrayList<>();
        for (String userId : expense.getSplitBetweenUsers()) {
            if (userId.equals(payer)) {
                continue;
            }
            Transaction transaction =  new Transaction(userId, payer, amount);
            transactions.add(transaction);
        }

        return transactions;
    }
}
