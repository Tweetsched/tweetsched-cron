package gk.tweetsched.cron;

import gk.tweetsched.cron.service.QueueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class TweetschedCronTrigger {
    private static final Logger LOGGER = LogManager.getLogger(TweetschedCronTrigger.class);
    private QueueService queueService = new QueueService();

    public static void main(String args[]) {
        TweetschedCronTrigger cronTrigger = new TweetschedCronTrigger();
        cronTrigger.runScheduler();
        LOGGER.info("Cron trigger was started.");
    }

    private void runScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        String cronSchedule = System.getenv(CRON_EXPRESSION);
        scheduler.schedule(() -> {queueService.processNext();},
                new CronTrigger(cronSchedule == null || cronSchedule.isEmpty() ? DEFAULT_CRON_EXPRESSION : cronSchedule));
    }
}
