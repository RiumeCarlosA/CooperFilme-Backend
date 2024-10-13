package br.com.riume.backendeureka.repository;

import br.com.riume.backendeureka.model.StatusFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatusFlowRepository extends JpaRepository<StatusFlow, UUID> {
}
