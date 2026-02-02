package org.example.batchexperimental.infra.persistence;

import org.example.batchexperimental.domain.entitie.ClientData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientDataRepository extends JpaRepository<ClientData, UUID> {
}
