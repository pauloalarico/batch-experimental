package org.example.batchexperimental.model.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.batchexperimental.model.enums.Seating;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientData {
    @Id
    private UUID id;
    private String documentNumber;
    private String name;
    private LocalDate birthDate;
    private String artistName;
    private LocalDate concertDate;
    @Enumerated(EnumType.STRING)
    private Seating seating;
    private Double value;
}
