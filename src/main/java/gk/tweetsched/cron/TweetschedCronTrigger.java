package gk.tweetsched.cron;

import gk.tweetsched.cron.service.PropertyService;
import gk.tweetsched.cron.service.QueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import static gk.tweetsched.cron.util.Constants.CRON_EXPRESSION;
import static gk.tweetsched.cron.util.Constants.DEFAULT_CRON_EXPRESSION;

/**
 * TweetschedCronTrigger class.
 * <p>
 * Date: May 27, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
@Configuration
@ComponentScan
public class TweetschedCronTrigger {
    private static final Logger LOGGER = LogManager.getLogger(TweetschedCronTrigger.class);
    @Autowired
    private QueueService queueService;
    @Autowired
    private PropertyService propService;

    public static void main(String args[]) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TweetschedCronTrigger.class);
        TweetschedCronTrigger cronTrigger = context.getBean("tweetschedCronTrigger", TweetschedCronTrigger.class);
        cronTrigger.runScheduler();
        LOGGER.info("Cron trigger was started.");
    }

    private void runScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        String cronSchedule = propService.getProp(CRON_EXPRESSION);
        scheduler.schedule(() -> {
                    queueService.processNext();
                },
                new CronTrigger(cronSchedule == null || cronSchedule.isEmpty() ? DEFAULT_CRON_EXPRESSION : cronSchedule));
    }
}
