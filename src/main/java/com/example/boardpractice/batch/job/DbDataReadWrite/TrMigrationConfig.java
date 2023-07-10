package com.example.boardpractice.batch.job.DbDataReadWrite;

import com.example.boardpractice.accounts.Accounts;
import com.example.boardpractice.accounts.AccountsRepository;
import com.example.boardpractice.orders.Orders;
import com.example.boardpractice.orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class TrMigrationConfig extends DefaultBatchConfiguration  {

    private final OrdersRepository ordersRepository;
    private final AccountsRepository accountsRepository;


    @Primary
    @Bean
    public Job trMigrationJob(JobRepository jobRepository, Step trMigrationStep) {
        return new JobBuilder("trMigrationJob", jobRepository)
                .start(trMigrationStep)
                .build();
    }

    @JobScope
    @Bean
    public Step trMigrationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                ItemReader trOrdersReader, ItemProcessor trOrderProcessor,
                                ItemWriter trOrdersWriter
    ) {
        return new StepBuilder("trMigrationStep", jobRepository)
                .<Orders, Accounts>chunk(5, transactionManager)
                .reader(trOrdersReader) // 데이터를 읽는다
                .processor(trOrderProcessor) // 읽은 데이터를 새로 쓸 데이터의 entity로 SET한다
                .writer(trOrdersWriter) // 저장한다
                .build();
    }


    // builder 이용한 방법 (커스텀하기 까다롭다)
//    @StepScope
//    @Bean
//    public RepositoryItemWriter<Accounts> trOrdersWriter() {
//        return new RepositoryItemWriterBuilder<Accounts>()
//                .repository(accountsRepository)
//                .methodName("save")
//                .build();
//    }
    
    
    
    /* item writer 개발자 커스텀하기 편함 */
    @StepScope
    @Bean
    public ItemWriter<Accounts> trOrdersWriter() {
        return new ItemWriter<Accounts>() {
            @Override
            public void write(Chunk<? extends Accounts> chunk) throws Exception {
                chunk.forEach(accountsRepository::save);
            }
        };
    }
    

    @StepScope
    @Bean
    public ItemProcessor<Orders, Accounts> trOrderProcessor() {
        return new ItemProcessor<Orders, Accounts>() {
            @Override
            public Accounts process(Orders item) throws Exception {
                return new Accounts(item);
            }
        };
    }


    @StepScope
    @Bean
    public RepositoryItemReader<Orders> trOrdersReader() {
        return new RepositoryItemReaderBuilder<Orders>()
                .name("trOrdersReader")
                .repository(ordersRepository)
                .methodName("findAll")
                .pageSize(5)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

//    @Override
//    protected Charset getCharset() {
//        return StandardCharsets.ISO_8859_1;
//    }

}