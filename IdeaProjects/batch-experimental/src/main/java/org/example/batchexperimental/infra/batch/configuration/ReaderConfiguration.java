package org.example.batchexperimental.infra.batch.configuration;

import org.example.batchexperimental.domain.entitie.ClientData;
import org.example.batchexperimental.infra.batch.processor.ClientDataProcessor;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ReaderConfiguration {
    @Value("${app.paths.value}")
    private String pathResource;

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
}
