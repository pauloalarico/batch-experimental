package org.example.batchexperimental.infra.batch.configuration;

import org.example.batchexperimental.infra.batch.processor.MoveFileProcessor;
import org.example.batchexperimental.infra.batch.processor.ServiceTaxProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfig {

    @Bean
    public ServiceTaxProcessor processor() {
        return new ServiceTaxProcessor();
    }

    @Bean
    public MoveFileProcessor moveFileProcessor() {
        return new MoveFileProcessor();
    }
}
