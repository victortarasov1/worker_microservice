CREATE TABLE logging_event (
                               event_id SERIAL PRIMARY KEY,
                               timestmp BIGINT NOT NULL,
                               formatted_message TEXT NOT NULL,
                               logger_name VARCHAR(256) NOT NULL,
                               level_string VARCHAR(256) NOT NULL,
                               thread_name VARCHAR(256),
                               caller_filename VARCHAR(256),
                               caller_class VARCHAR(256),
                               caller_method VARCHAR(256),
                               caller_line CHAR(4)
);

CREATE TABLE logging_event_exception (
                                         event_id BIGINT NOT NULL,
                                         i SMALLINT NOT NULL,
                                         trace_line TEXT NOT NULL,
                                         PRIMARY KEY (event_id, i),
                                         FOREIGN KEY (event_id) REFERENCES logging_event (event_id)
);
