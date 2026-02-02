package org.example.batchexperimental.infra.batch.configuration;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.example.batchexperimental.domain.entitie.ClientData;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WriterConfiguration {
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public ItemWriter<ClientData> writer() {
        return new JpaItemWriter<>(entityManagerFactory);
    }
}
