package br.com.riume.backendeureka.repository;

import br.com.riume.backendeureka.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScriptRepository extends JpaRepository<Script, UUID> {
}
