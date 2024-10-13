package br.com.riume.backendeureka.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "status_transitions")
@Data
@NoArgsConstructor
public class StatusTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transition_id")
    private Long transitionId;

    @ManyToOne
    @JoinColumn(name = "current_status_id", nullable = false)
    private Status currentStatus;

    @ManyToOne
    @JoinColumn(name = "next_status_id", nullable = false)
    private Status nextStatus;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}

