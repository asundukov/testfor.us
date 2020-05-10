CREATE TABLE publish_type(
    publish_type_id smallint not null primary key,
    name varchar(16) not null
);

INSERT INTO publish_type
    (publish_type_id, name) VALUES
    (1, 'PRIVATE'),
    (2, 'DIRECT_LINK'),
    (3, 'PUBLIC');

CREATE TABLE questionnaire (
    questionnaire_id serial not null primary key,
    title varchar(64) not null,
    description varchar(512) not null,
    open_stat boolean not null,
    open_result boolean not null,
    publish_type_id smallint not null references publish_type(publish_type_id),
    created_at timestamp not null default now(),
    committed_at timestamp null
);

CREATE TABLE questionnaire_access (
    questionnaire_id int not null primary key references questionnaire(questionnaire_id),
    secret_word char(32) not null,
    admin_email varchar(128)
);

CREATE TABLE question (
    question_id serial not null primary key,
    questionnaire_id int not null references questionnaire(questionnaire_id),
    value varchar(1024) not null,
    points int not null,
    is_multiple_choice boolean not null
);

CREATE INDEX question_questionnaire_id_idx ON question(questionnaire_id);

CREATE TABLE option (
    option_id serial not null primary key,
    question_id int not null references question(question_id),
    value varchar(255) not null,
    is_right boolean not null,
    is_custom boolean not null,
    points int not null
);

CREATE INDEX option_question_id_idx ON option(question_id);

CREATE TABLE attempt (
    attempt_id serial not null primary key,
    questionnaire_id int not null,
    token varchar(16) not null,
    is_completed boolean not null,
    created_at timestamp not null default now()
);

CREATE TABLE answer (
    answer_id serial not null primary key,
    attempt_id int not null references attempt(attempt_id),
    question_id int not null references question(question_id),
    points int not null,
    created_at timestamp not null default now()
);

CREATE INDEX answer_attempt_id_idx ON answer(attempt_id);
CREATE INDEX answer_question_id_idx ON answer(question_id);
CREATE UNIQUE INDEX answer_attempt_id_question_id_uniq ON answer(attempt_id, question_id);

CREATE TABLE answer_option (
    answer_option_id serial not null primary key,
    answer_id int not null references answer(answer_id),
    option_id int not null references option(option_id),
    value text,
    points int not null
);

CREATE TABLE result (
    result_id serial not null primary key,
    attempt_id int not null references attempt(attempt_id),
    created_at timestamp not null default now()
);

CREATE INDEX result_attempt_id_idx ON result(attempt_id);

