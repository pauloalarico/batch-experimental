package org.example.batchexperimental.processor;

import org.example.batchexperimental.model.entitie.ClientData;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class ServiceTaxProcessor implements ItemProcessor<ClientData, ClientData> {
    @Override
    public @Nullable ClientData process(ClientData item) throws Exception {
        item.setTaxValue();
        return item;
    }
}

