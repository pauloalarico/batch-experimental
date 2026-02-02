package org.example.batchexperimental.infra.batch.configuration;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.example.batchexperimental.infra.batch.processor.ClientDataProcessor;
import org.example.batchexperimental.infra.batch.processor.MoveFileProcessor;
import org.example.batchexperimental.infra.batch.processor.ServiceTaxProcessor;
import org.example.batchexperimental.domain.entitie.ClientData;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {
    @Value("${app.paths.value}")
    private String pathResource;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job job(@Qualifier("initialStep") Step initalStep, JobRepository jobRepository) {
        return new JobBuilder("tickets-generator", jobRepository)
                .start(initalStep)
                .next(moveFileStep(jobRepository))
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step initialStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("initial-step", jobRepository)
                .<ClientData, ClientData>chunk(10)
                .transactionManager(transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<ClientData> reader() {
        return new FlatFileItemReaderBuilder<ClientData>()
                .name("file-reader")
                .resource(new FileSystemResource(pathResource + "/file.txt"))
                .delimited()
                .delimiter(";")
                .names("documentNumber", "name", "birthDate", "artistName", "concertDate", "seating", "value")
                .fieldSetMapper(new ClientDataProcessor())
                .build();
    }

    @Bean
    public ItemWriter<ClientData> writer() {
        return new JpaItemWriter<>(entityManagerFactory);
    }

    @Bean
    public ServiceTaxProcessor processor() {
        return new ServiceTaxProcessor();
    }

    @Bean
    public Step moveFileStep(JobRepository jobRepository) {
        return new StepBuilder("move-file", jobRepository)
                .tasklet(moveFileProcessor())
                .build();
    }

    @Bean
    public MoveFileProcessor moveFileProcessor() {
        return new MoveFileProcessor();
    }
}
