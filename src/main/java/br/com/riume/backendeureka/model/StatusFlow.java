package br.com.riume.backendeureka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "status_flows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "flow_id", columnDefinition = "BINARY(36)")
    private UUID flowId;

    @ManyToOne
    @JoinColumn(name = "status_from_id", nullable = false)
    private Status statusFrom;

    @ManyToOne
    @JoinColumn(name = "status_to_id", nullable = false)
    private Status statusTo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "script_id", nullable = false)
    private Script script;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}
