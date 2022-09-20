package com.nurture.lld.services.splitstrategy;

import java.util.List;

import com.nurture.lld.dtos.Expense;
import com.nurture.lld.entities.Transaction;

public interface SplitStrategy {

    public List<Transaction> getTransactionsFromExpense(Expense expense);
}
