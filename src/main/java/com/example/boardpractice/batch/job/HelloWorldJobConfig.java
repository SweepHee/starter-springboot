package com.example.boardpractice.batch.job;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Configuration
public class HelloWorldJobConfig extends DefaultBatchConfiguration  {

//    @Qualifier("helloWorldJob")
    @Bean
    public Job helloWorldJob(JobRepository jobRepository, Step helloWorldStep) {
        return new JobBuilder("helloWorldJob", jobRepository)
                .start(helloWorldStep)
                .build();
    }

    @Bean
    public Tasklet myTasklet() {
        return ((StepContribution contribution, ChunkContext chunkContext) -> {
            System.out.println("hello world spring batch!!");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Step helloWorldStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("helloWorldStep", jobRepository)
                .tasklet(myTasklet(), transactionManager)
//                .chunk(1, transactionManager)
                .build();
    }

//    @Override
//    protected Charset getCharset() {
//        return StandardCharsets.ISO_8859_1;
//    }

}