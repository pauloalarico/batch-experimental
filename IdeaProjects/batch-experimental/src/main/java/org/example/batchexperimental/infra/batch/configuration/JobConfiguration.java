package org.example.batchexperimental.infra.batch.configuration;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job job(@Qualifier("initialStep") Step initalStep, JobRepository jobRepository,
                   @Qualifier("moveFileStep") Step moveFileStep) {
        return new JobBuilder("tickets-generator", jobRepository)
                .start(initalStep)
                .next(moveFileStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
