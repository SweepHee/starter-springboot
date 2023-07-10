package com.example.boardpractice.batch.job.JobListener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobListenerConfig extends DefaultBatchConfiguration  {

//    @Primary
    @Bean
    public Job jobListenerJob(JobRepository jobRepository, Step jobListenerStep) {
        return new JobBuilder("jobListenerJob", jobRepository)
                .listener(new JobLoggerListener())
                .start(jobListenerStep)
                .build();
    }

    @Bean
    public Step jobListenerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("jobListenerStep", jobRepository)
                .tasklet(jobListenerTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet jobListenerTasklet() {
        return ((StepContribution contribution, ChunkContext chunkContext) -> {
            System.out.println("JobListener Job batch!!");
            return RepeatStatus.FINISHED;
        });
    }

//    @Override
//    protected Charset getCharset() {
//        return StandardCharsets.ISO_8859_1;
//    }

}