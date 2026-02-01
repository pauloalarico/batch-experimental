package org.example.batchexperimental.configuration;

import lombok.RequiredArgsConstructor;
import org.example.batchexperimental.model.entitie.ClientData;
import org.example.batchexperimental.processor.ClientDataProcessor;
import org.example.batchexperimental.utils.CustomSqlParameters;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {
    @Value("${app.paths.value}")
    private String pathResource;
    private final DataSource dataSource;

    @Bean
    public Job job(Step initalStep, JobRepository jobRepository) {
        return new JobBuilder("tickets-generator", jobRepository)
                .start(initalStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step initialStep(JobRepository jobRepository) {
        return new StepBuilder("initial-step", jobRepository)
                .<ClientData, ClientData>chunk(10)
                .reader(reader())
                .writer(writer(dataSource))
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
    public ItemWriter<ClientData> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ClientData>()
                .dataSource(dataSource)
                .sql(
                        "INSERT INTO client_data (id, document_number, name, birth_date, artist_name, concert_date, seating, value)" +
                                " VALUES(:id, :documentNumber, :name, :birthDate, :artistName, :concertDate, :seating, :value)"
                ).itemSqlParameterSourceProvider(new CustomSqlParameters() {
                })
                .build();
    }
}
