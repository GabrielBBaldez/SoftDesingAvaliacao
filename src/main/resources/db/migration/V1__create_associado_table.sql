CREATE TABLE public.associado
(
    id SERIAL PRIMARY KEY,
    nome    VARCHAR(255),
    cpf     VARCHAR(11) UNIQUE,
    votante BOOLEAN,
    ativo   BOOLEAN DEFAULT TRUE
);
