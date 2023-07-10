package com.example.boardpractice.batch.job.ValidationParam;

import com.example.boardpractice.batch.job.ValidationParam.Validator.FileParamValidator;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class ValidatedParamJobConfig extends DefaultBatchConfiguration  {



    @Bean
    public Job validatedParamJob(JobRepository jobRepository, Step validatedParamStep) {
        return new JobBuilder("validatedParamJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(multipleValidator())
                .start(validatedParamStep)
                .build();
    }

    private CompositeJobParametersValidator multipleValidator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        validator.setValidators(Arrays.asList(new FileParamValidator())); // 배열에 넣어서 밸리데이터 여러개 가능
        return validator;
    }



    @Bean
    public Step validatedParamStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                   Tasklet validatedParamTasklet) {
        return new StepBuilder("validatedParamStep", jobRepository)
                .tasklet(validatedParamTasklet, transactionManager)
//                .chunk(1, transactionManager)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
        return ((StepContribution contribution, ChunkContext chunkContext) -> {
            System.out.println(fileName);
            System.out.println("validated param job config!!2222");
            return RepeatStatus.FINISHED;
        });
    }



//    @Override
//    protected Charset getCharset() {
//        return StandardCharsets.ISO_8859_1;
//    }

}