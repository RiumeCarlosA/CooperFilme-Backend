ALTER TABLE status_flows
    ADD COLUMN script_id BINARY(36) NOT NULL;

ALTER TABLE status_flows
    ADD CONSTRAINT fk_script_id
        FOREIGN KEY (script_id)
            REFERENCES scripts(script_id);
