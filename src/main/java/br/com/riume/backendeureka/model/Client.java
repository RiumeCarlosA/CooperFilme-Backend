package br.com.riume.backendeureka.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id", columnDefinition = "BINARY(36)")
    private UUID clientId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "created_at", updatable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new java.sql.Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new java.sql.Timestamp(System.currentTimeMillis());
    }
}
