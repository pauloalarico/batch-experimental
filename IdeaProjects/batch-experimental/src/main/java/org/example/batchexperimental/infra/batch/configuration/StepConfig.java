package org.example.batchexperimental.infra.batch.configuration;

import org.example.batchexperimental.domain.entitie.ClientData;
import org.example.batchexperimental.infra.batch.processor.MoveFileProcessor;
import org.example.batchexperimental.infra.batch.processor.ServiceTaxProcessor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class StepConfig {
    @Bean
    public Step initialStep(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager,
                            ItemReader<ClientData> reader,
                            ItemWriter<ClientData> writer,
                            ServiceTaxProcessor processor) {
        return new StepBuilder("initial-step", jobRepository)
                .<ClientData, ClientData>chunk(10)
                .transactionManager(transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step moveFileStep(JobRepository jobRepository, MoveFileProcessor moveFileProcessor) {
        return new StepBuilder("move-file", jobRepository)
                .tasklet(moveFileProcessor)
                .build();
    }

}
