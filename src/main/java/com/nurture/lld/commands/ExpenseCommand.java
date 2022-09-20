package com.nurture.lld.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.nurture.lld.dtos.Expense;
import com.nurture.lld.dtos.SplitType;
import com.nurture.lld.services.SplitService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExpenseCommand implements Command {

    private SplitService splitService;

    public void execute(List<String> inputs) {
        try {

            String paidByUser = inputs.get(1).trim();
            double amount = Double.parseDouble(inputs.get(2).trim());

            int splitCount = Integer.parseInt(inputs.get(3).trim());
            List<String> splitBetweenUsers = new ArrayList<>();
            for(int i = 0; i<splitCount; ++i) {
                splitBetweenUsers.add(inputs.get(4+i).trim());
            }
            SplitType splitType = SplitType.valueOf(inputs.get(4+splitCount).trim().toLowerCase());

            ListIterator<String> detailsIterator = inputs.listIterator(splitCount + 5);
            List<Double> splitDetails = new ArrayList<>();
            while (detailsIterator.hasNext()) {
                splitDetails.add(Double.parseDouble(detailsIterator.next().trim()));
            }

            Expense expense =  new Expense(paidByUser,
                    splitType, amount, splitBetweenUsers, splitDetails);
            splitService.addExpense(expense);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("not enough parameters specified");
        }
    }
}
