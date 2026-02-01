package org.example.batchexperimental.utils;

import org.example.batchexperimental.model.entitie.ClientData;
import org.springframework.batch.infrastructure.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class CustomSqlParameters extends BeanPropertyItemSqlParameterSourceProvider<ClientData> {

    @Override
    public SqlParameterSource createSqlParameterSource(ClientData item) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", item.getId());
        params.addValue("documentNumber", item.getDocumentNumber());
        params.addValue("name", item.getName());
        params.addValue("birthDate", item.getBirthDate());
        params.addValue("artistName", item.getArtistName());
        params.addValue("concertDate", item.getConcertDate());
        params.addValue("seating", item.getSeating().name());
        params.addValue("value", item.getValue());

        return params;
    }
}
