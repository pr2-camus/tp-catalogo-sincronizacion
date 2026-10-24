CREATE TABLE category (
                        id          BIGINT PRIMARY KEY,
                        name        VARCHAR(255) NOT NULL,
                        description TEXT,
                        enabled     BOOLEAN NOT NULL DEFAULT TRUE,
                        created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
                        updated_at  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE professional (
                            id          BIGINT PRIMARY KEY,
                            category_id BIGINT NOT NULL REFERENCES category(id),
                            first_name  VARCHAR(255) NOT NULL,
                            last_name   VARCHAR(255) NOT NULL,
                            enabled     BOOLEAN NOT NULL DEFAULT TRUE,
                            created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
                            updated_at  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE weekly_schedule (
                               id                    BIGINT PRIMARY KEY,
                               professional_id       BIGINT NOT NULL REFERENCES professional(id),
                               day_of_week           VARCHAR(10) NOT NULL,
                               start_time            VARCHAR(10) NOT NULL,
                               end_time              VARCHAR(10) NOT NULL,
                               slot_duration_minutes INTEGER NOT NULL,
                               enabled               BOOLEAN NOT NULL DEFAULT TRUE,
                               created_at            TIMESTAMP WITH TIME ZONE NOT NULL,
                               updated_at            TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE sync_state (
                          id              BIGINT PRIMARY KEY DEFAULT 1,
                          current_version BIGINT NOT NULL,
                          updated_at      TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE processed_event (
                               event_id   VARCHAR(255) PRIMARY KEY,
                               processed_at TIMESTAMP WITH TIME ZONE NOT NULL
);
