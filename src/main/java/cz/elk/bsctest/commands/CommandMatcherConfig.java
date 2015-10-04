package cz.elk.bsctest.commands;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandMatcherConfig {

    @Autowired
    @Qualifier("dumpCommand")
    Command dumpCommand;

    @Autowired
    @Qualifier("exitCommand")
    Command exitCommand;
    
    @Autowired
    @Qualifier("increaseBalanceCommand")
    Command increaseBalanceCommand;

    @Autowired
    @Qualifier("fallbackCommand")
    Command fallbackCommand;
    
    @Bean
    public CommandMatcher commandParser() {
        final List<Command> commands = Arrays.asList(new Command[] { dumpCommand, exitCommand, increaseBalanceCommand });

        final CommandMatcher retVal = new CommandMatcherImpl(commands, fallbackCommand);
        return retVal;
    }
}
