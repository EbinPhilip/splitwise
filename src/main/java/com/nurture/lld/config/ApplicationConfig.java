package com.nurture.lld.config;

import com.nurture.lld.commands.CommandExecutor;
import com.nurture.lld.commands.ExpenseCommand;
import com.nurture.lld.commands.ShowCommand;
import com.nurture.lld.repositories.TransactionRepository;
import com.nurture.lld.repositories.UserRepository;
import com.nurture.lld.services.SplitService;
import com.nurture.lld.services.splitstrategy.SplitStrategyFactory;

import lombok.Getter;

public class ApplicationConfig {
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final UserRepository userRepository = new UserRepository();

    private final SplitStrategyFactory splitStrategyFactory = new SplitStrategyFactory();
    private final SplitService splitService = new SplitService(transactionRepository, userRepository, splitStrategyFactory);

    private final ExpenseCommand expenseCommand = new ExpenseCommand(splitService);
    private final ShowCommand showCommand = new ShowCommand(splitService);

    @Getter
    private final CommandExecutor executor = new CommandExecutor();

    public ApplicationConfig() {

        executor.addCommand("EXPENSE", expenseCommand);
        executor.addCommand("SHOW", showCommand);
    }
}
