package com.nurture.lld.services.splitstrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nurture.lld.dtos.Expense;
import com.nurture.lld.entities.Transaction;

public class PercentageSplit implements SplitStrategy {

    public List<Transaction> getTransactionsFromExpense(Expense expense) {

        if (expense.getSplitBetweenUsers().size()
            != expense.getSplitDetails().size()) {
            throw new RuntimeException("Not enough split details");
        }

        String payer = expense.getPaidByUser();
        double amount = expense.getAmount();

        if (expense.getSplitDetails().stream()
                .mapToDouble(Double::doubleValue).sum() != 100.0) {
            throw new RuntimeException("Percentages must add up to 100");
        }

        List<Transaction> transactions = new ArrayList<>();
        Iterator<String> usersIterator = expense.getSplitBetweenUsers().iterator();
        Iterator<Double> detailsIterator = expense.getSplitDetails().iterator();
        while(usersIterator.hasNext() && detailsIterator.hasNext()) {
            String userId = usersIterator.next();
            double percentage = detailsIterator.next();
            if (userId.equals(payer)) {
                continue;
            }
            Transaction transaction =  new Transaction(userId, payer,
                    (percentage/100.0) * amount);
            transactions.add(transaction); 
        }

        return transactions;
    }
}
