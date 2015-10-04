package cz.elk.bsctest.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    Environment env;

    @Bean
    public ScheduledDump recurrentDump() {
        return new ScheduledDump();
    }

}
