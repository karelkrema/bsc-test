package cz.elk.bsctest.parser;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.NonNull;

import org.springframework.stereotype.Component;

import cz.elk.bsctest.Currency;
import cz.elk.bsctest.CurrencyTransaction;

@Component
public class CurrencyTransactionParserImpl implements CurrencyTransactionParser {

    @Override
    public Optional<CurrencyTransaction> parse(@NonNull final String line) {
        
        // TODO Consider using regex
        final String[] split = line.trim().split("\\s+");
        if(split.length != 2) {
            return Optional.empty();
        }
        
        final Optional<CurrencyTransaction> retVal = parseValues(split);
        return retVal;
    }

    private Optional<CurrencyTransaction> parseValues(final String[] split) {
        final Optional<Currency> parsedCurrency = Currency.valueOfCode(split[0].trim());
        final Optional<BigDecimal> parsedAmount = parseAmount(split[1].trim());

        if(!parsedCurrency.isPresent() || !parsedAmount.isPresent()) {
            return Optional.empty();
        }
        
        return Optional.of(new CurrencyTransaction(parsedCurrency.get(), parsedAmount.get()));
    }

    private Optional<BigDecimal> parseAmount(final String input) {
        BigDecimal parsedAmount;
        try {
            parsedAmount = new BigDecimal(input);
        } catch (final NumberFormatException e) {
            return Optional.empty();
        }

        return Optional.of(parsedAmount);
    }
    
}

