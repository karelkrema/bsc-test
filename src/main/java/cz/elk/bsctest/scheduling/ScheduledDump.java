package cz.elk.bsctest.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

import cz.elk.bsctest.commands.Command;

public class ScheduledDump {

    @Autowired
    @Qualifier("dumpCommand")
    Command dumpCommand;

    @Scheduled(fixedRate = 60000)
    public void doRecurrentDump() {
        dumpCommand.execute(null);
    }
}
