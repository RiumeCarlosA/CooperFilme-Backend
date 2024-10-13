package br.com.riume.backendeureka.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vote_id", columnDefinition = "BINARY(36)")
    private UUID voteId;

    @ManyToOne
    @JoinColumn(name = "script_id", nullable = false)
    private Script script;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote", nullable = false)
    private VoteOption vote;

    @Column(name = "vote_date", updatable = false)
    private Timestamp voteDate;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @PrePersist
    protected void onCreate() {
        voteDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public enum VoteOption {
        APPROVED, NOT_APPROVED
    }
}
