CREATE TABLE roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE status (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    user_id BINARY(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE user_roles (
    user_id BINARY(36) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE clients (
     client_id BINARY(36) PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     email VARCHAR(255) UNIQUE NOT NULL,
     phone VARCHAR(20),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE scripts (
     script_id BINARY(36) PRIMARY KEY,
     client_id BINARY(36) NOT NULL,
     status_id INT NOT NULL,
     document VARCHAR(300) NOT NULL,
     submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     deleted BOOLEAN DEFAULT FALSE,
     active BOOLEAN DEFAULT TRUE,
     FOREIGN KEY (client_id) REFERENCES clients(client_id),
     FOREIGN KEY (status_id) REFERENCES status(status_id)
);

CREATE TABLE status_flows (
    flow_id BINARY(36) PRIMARY KEY,
    status_from_id INT NOT NULL,
    status_to_id INT NOT NULL,
    user_id BINARY(36) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (status_from_id) REFERENCES status(status_id),
    FOREIGN KEY (status_to_id) REFERENCES status(status_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE votes (
    vote_id BINARY(36) PRIMARY KEY,
    script_id BINARY(36) NOT NULL,
    user_id BINARY(36) NOT NULL,
    vote ENUM('approved', 'not_approved') NOT NULL,
    vote_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (script_id) REFERENCES scripts(script_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
