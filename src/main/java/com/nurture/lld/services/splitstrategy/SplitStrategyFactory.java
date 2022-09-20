package com.nurture.lld.services.splitstrategy;

import com.nurture.lld.dtos.Expense;
import com.nurture.lld.dtos.SplitType;

public class SplitStrategyFactory {

    public SplitStrategy getStrategy(Expense expense) {
        if (expense.getSplitType() == SplitType.equal) {
            return new EqualSplit();
        } else if (expense.getSplitType() == SplitType.percent) {
            return new PercentageSplit();
        } else if (expense.getSplitType() == SplitType.exact) {
            return new ExactSplit();
        } else {
            throw new RuntimeException("unknown split type");
        }
    }
}
