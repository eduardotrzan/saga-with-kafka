/**
 * Schema creation
 *
 */

CREATE SEQUENCE ordering_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;
END;

CREATE TABLE "ordering" (
    "id"                         BIGINT DEFAULT nextval('ordering_id_seq' :: REGCLASS) NOT NULL,
    "uuid"                       UUID                                                        NOT NULL DEFAULT uuid_generate_v4(),
    "create_date"                TIMESTAMP WITH TIME ZONE                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "update_date"                TIMESTAMP WITH TIME ZONE                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "version"                    BIGINT                                                      NOT NULL,
    "status"                     VARCHAR(70)                                                 NOT NULL DEFAULT 'PENDING', -- 'IN_PROGRESS', 'DONE'
    "description"                VARCHAR(200)                                                NOT NULL,
    "cost"                       DECIMAL(24, 12)                                             NOT NULL,

    PRIMARY KEY ("id")
);
END;

CREATE UNIQUE INDEX "ordering_idx01"
    ON "ordering" ("uuid");
