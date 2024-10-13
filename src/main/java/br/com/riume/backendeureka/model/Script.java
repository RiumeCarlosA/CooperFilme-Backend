package br.com.riume.backendeureka.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "scripts")
@Data
@NoArgsConstructor
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "script_id", columnDefinition = "BINARY(36)")
    private UUID scriptId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "submission_date", updatable = false)
    private java.sql.Timestamp submissionDate;

    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        submissionDate = new java.sql.Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new java.sql.Timestamp(System.currentTimeMillis());
    }
}
