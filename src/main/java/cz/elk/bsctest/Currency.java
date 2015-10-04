package cz.elk.bsctest;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum of currencies used in the program.
 */
@Getter
@AllArgsConstructor
public enum Currency {

    CZK("CZK"),
    USD("USD"),
    EUR("EUR"),
    HUF("HUF"),
    GBP("GBP");
    
    private final String code;

    public static Optional<Currency> valueOfCode(final String code) {
        final Optional<Currency> firstMatchingCurrency = Stream.of(Currency.values())
                                                               .filter(enumItem -> enumItem.code.equals(code))
                                                               .findFirst();
        
        return firstMatchingCurrency;
    }
    
}
