package cz.elk.bsctest.commands;

import java.util.Collection;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CommandMatcherImpl implements CommandMatcher {
    
    private final @NonNull Collection<Command> commands;
    private final @NonNull Command fallbackCommand;
    
    @Override
    public Command parse(final String line) {

        final Optional<Command> firstMatchingCommand = commands.stream()
                                                               .filter(command -> command.matches(line))
                                                               .findFirst();

        return firstMatchingCommand.orElse(fallbackCommand);
    }

}



