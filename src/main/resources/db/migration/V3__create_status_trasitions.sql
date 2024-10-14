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
    (1, 2, 1),
    (2, 3, 1),
    (3, 4, 2),
    (4, 5, 2),
    (4, 8, 2),
    (5, 6, 3),
    (5, 8, 3)
