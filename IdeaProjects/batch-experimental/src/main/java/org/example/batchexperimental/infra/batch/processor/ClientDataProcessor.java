package org.example.batchexperimental.infra.batch.processor;

import lombok.extern.slf4j.Slf4j;
import org.example.batchexperimental.domain.entitie.ClientData;
import org.example.batchexperimental.domain.enums.Seating;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
public class ClientDataProcessor implements FieldSetMapper<ClientData> {

    @Override
    public ClientData mapFieldSet(FieldSet fieldSet) throws BindException {
        return ClientData.builder()
                .documentNumber(fieldSet.readString("documentNumber"))
                .name(fieldSet.readString("name"))
                .birthDate(readDate(fieldSet.readString("birthDate")))
                .artistName(fieldSet.readString("artistName"))
                .concertDate(readDate(fieldSet.readString("concertDate")))
                .seating(Seating.valueOf(verify(Objects.requireNonNull(fieldSet.readString("seating")))))
                .value(fieldSet.readDouble("value"))
                .build();
    }

    private LocalDate readDate(String date) {
        if (date == null) {
            throw new NullPointerException("Date cannot be null");
        }
        return LocalDate.parse(date);
    }

    private String verify (String seat) {
        if (seat.contains(" ")) {
            return seat.replace(" ", "_").toUpperCase();
        }
        return seat.toUpperCase();
    }
}
