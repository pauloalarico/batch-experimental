package org.example.batchexperimental.processor;

import lombok.extern.slf4j.Slf4j;
import org.example.batchexperimental.model.entitie.ClientData;
import org.example.batchexperimental.model.enums.Seating;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class ClientDataProcessor implements FieldSetMapper<ClientData> {

    @Override
    public ClientData mapFieldSet(FieldSet fieldSet) throws BindException {
        return ClientData.builder()
                .id(UUID.randomUUID())
                .documentNumber(fieldSet.readString("documentNumber"))
                .name(fieldSet.readString("name"))
                .birthDate(readDate(fieldSet.readString("birthDate")))
                .artistName(fieldSet.readString("artistName"))
                .concertDate(readDate(fieldSet.readString("concertDate")))
                .seating(Seating.valueOf(fieldSet.readString("seating").toUpperCase()))
                .value(toDouble(fieldSet.readString("value")))
                .build();
    }

    private LocalDate readDate(String date) {
        if (date == null) {
            throw new NullPointerException("Date cannot be null");
        }
        return LocalDate.parse(date);
    }

    private Double toDouble(String value) {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }
        return Double.valueOf(value);

    }
}
