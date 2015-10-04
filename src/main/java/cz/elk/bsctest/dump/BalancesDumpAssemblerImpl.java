package cz.elk.bsctest.dump;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import org.springframework.stereotype.Component;

import cz.elk.bsctest.Currency;

@Component
public class BalancesDumpAssemblerImpl implements BalancesDumpAssembler {

    private static final String HEADER = "========= Balances dump =========";
    private static final String FOOTER = "========= Dump end =========";

    @Override
    public String assembleDump(final Map<Currency, BigDecimal> balances) {
        final StringBuilder sb = new StringBuilder();

        appendHeader(sb);
        appendBody(sb, balances);
        appendFooter(sb);

        return sb.toString();
    }

    
    private static void appendBody(final StringBuilder sb, 
                            final Map<Currency, BigDecimal> balances) {
        balances.entrySet()
                .stream()
                .filter(entry -> entry.getValue().signum() != 0)
                .forEach(entry -> appendLine(sb, entry.getKey(), entry.getValue()));
    }

    private static void appendFooter(final StringBuilder sb) {
        sb.append(FOOTER).append("\n");
    }

    private static void appendHeader(final StringBuilder sb) {
        sb.append(HEADER).append("\n");
    }

    private static void appendLine(final StringBuilder sb,
                                   final Currency currency,
                                   final BigDecimal value) {

        sb.append(MessageFormat.format("  {0}: {1}\n", currency, value.toString()));
        
    }
}
