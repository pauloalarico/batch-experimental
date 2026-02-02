package org.example.batchexperimental.domain.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.batchexperimental.domain.enums.Seating;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String documentNumber;
    private String name;
    private LocalDate birthDate;
    private String artistName;
    private LocalDate concertDate;
    @Enumerated(EnumType.STRING)
    private Seating seating;
    private Double value;
    private Double taxServiceValue;

    public void setTaxValue() {
        if(seating.equals(Seating.CAMAROTE)) {
            this.taxServiceValue = 251.00;
        }

        if(seating.equals(Seating.VIP)) {
            this.taxServiceValue = 20.00;
        }

        if(seating.equals(Seating.PISTA_PREMIUM)) {
            this.taxServiceValue = 34.40;
        }

        if(seating.equals(Seating.PISTA)) {
            this.taxServiceValue = 25.00;
        }

        if(seating.equals(Seating.ARQUIBANCADA)) {
            this.taxServiceValue = 12.00;
        }
    }
}
