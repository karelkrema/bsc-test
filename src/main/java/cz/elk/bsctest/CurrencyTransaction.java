package cz.elk.bsctest;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * An object representing currency transaction.<br/>
 * 
 * It is ensured that neither currency nor amount is null.<br/>
 * 
 */
@Getter
@AllArgsConstructor
@ToString
public class CurrencyTransaction {

    private final @NonNull Currency currency;
    private final @NonNull BigDecimal amount;

}
