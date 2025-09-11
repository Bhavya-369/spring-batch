package com.suri.job.scheduler;

import java.util.Set;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job importCustomerJob;
    private final JobExplorer jobExplorer;

    /**
     * Scheduled method to run the batch job automatically every 2 minutes
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void runScheduledJob() {
        try {
            // Check for currently running executions
            Set<JobExecution> runningExecutions = jobExplorer.findRunningJobExecutions(importCustomerJob.getName());
            if (!runningExecutions.isEmpty()) {
                System.out.println("Previous job still running. Skipping this execution.");
                return;
            }

            // Launch new job
            JobExecution execution = jobLauncher.run(
                    importCustomerJob,
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis()) // unique job instance
                            .toJobParameters()
            );

            System.out.println("Scheduled Batch Job Status: " + execution.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
