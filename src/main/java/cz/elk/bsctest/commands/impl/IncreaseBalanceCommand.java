package cz.elk.bsctest.commands.impl;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.elk.bsctest.balances.BalancesPerCurrency;
import cz.elk.bsctest.commands.Command;
import cz.elk.bsctest.commands.CommandResult;
import cz.elk.bsctest.model.CurrencyTransaction;
import cz.elk.bsctest.parsing.CurrencyTransactionParser;

@Component("increaseBalanceCommand")
public class IncreaseBalanceCommand implements Command {
    
    @Autowired
    BalancesPerCurrency balancesPerCurrency;

    @Autowired
    CurrencyTransactionParser currencyTransactionParser;
    
    @Override
    public boolean matches(final String input) {
        final Optional<CurrencyTransaction> tx = currencyTransactionParser.parse(input);
        return tx.isPresent();
    }

    @Override
    public CommandResult execute(final String input) {
        final CurrencyTransaction tx = currencyTransactionParser.parse(input).get();
        
        balancesPerCurrency.increaseBalance(tx);
        
        return new CommandResult(MessageFormat.format("Balance increased by {0} for currency {1}", tx.getAmount(), tx.getCurrency()), false);
    }
    
}
