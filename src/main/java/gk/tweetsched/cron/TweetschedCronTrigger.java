package gk.tweetsched.cron;

import gk.tweetsched.cron.service.QueueService;
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
    private QueueService twitterService = new QueueService();

    public static void main(String args[]) {
        TweetschedCronTrigger cronTrigger = new TweetschedCronTrigger();
        cronTrigger.runScheduler();
    }

    private void runScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        twitterService.processNext();
        scheduler.initialize();
        String cronSchedule = System.getenv(CRON_EXPRESSION);
        scheduler.schedule(() -> {twitterService.processNext();},
                new CronTrigger(cronSchedule == null || cronSchedule.isEmpty() ? DEFAULT_CRON_EXPRESSION : cronSchedule));
    }
}
