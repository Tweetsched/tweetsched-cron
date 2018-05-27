package gk.tweetsched;

import gk.tweetsched.service.QueueService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

/**
 * TweetschedCronTrigger class.
 * <p>
 * Date: May 27, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
public class TweetschedCronTrigger {
    private static final String CRON_EXPRESSION = "0 */15 14-16 * * *";
    private QueueService twitterService = new QueueService();

    public static void main(String args[]) {
        TweetschedCronTrigger cronTrigger = new TweetschedCronTrigger();
        cronTrigger.runScheduler();
    }

    private void runScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        twitterService.processNext();
        scheduler.initialize();
        scheduler.schedule(() -> {twitterService.processNext();}, new CronTrigger(CRON_EXPRESSION));
    }
}
