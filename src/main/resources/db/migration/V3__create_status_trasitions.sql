CREATE TABLE status_transitions (
    transition_id INT AUTO_INCREMENT PRIMARY KEY,
    current_status_id INT NOT NULL,
    next_status_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (current_status_id) REFERENCES status(status_id),
    FOREIGN KEY (next_status_id) REFERENCES status(status_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Inserindo transições válidas com as roles permitidas
INSERT INTO status_transitions (current_status_id, next_status_id, role_id) VALUES
    (1, 2, 1),  -- pending_review to in_review, Analyst
    (2, 3, 2),  -- in_review to awaiting_revision, Reviewer
    (2, 4, 2),  -- in_review to in_revision, Reviewer
    (3, 5, 2),  -- awaiting_revision to awaiting_approval, Reviewer
    (4, 6, 3),  -- in_revision to in_approval, Approver
    (5, 7, 3),  -- awaiting_approval to approved, Approver
    (5, 8, 3),  -- awaiting_approval to rejected, Approver
    (6, 7, 3),  -- in_approval to approved, Approver
    (6, 8, 3);  -- in_approval to rejected, Approver
