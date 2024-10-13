package br.com.riume.backendeureka.repository;

import br.com.riume.backendeureka.model.Role;
import br.com.riume.backendeureka.model.Status;
import br.com.riume.backendeureka.model.StatusTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StatusTransitionRepository extends JpaRepository<StatusTransition, Long>  {
    boolean existsByCurrentStatusAndNextStatusAndRoleIn(Status currentStatus, Status nextStatus, List<Role> role);
}
