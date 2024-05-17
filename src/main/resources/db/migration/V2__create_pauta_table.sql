CREATE TABLE public.pauta
(
    id           SERIAL PRIMARY KEY,
    titulo       VARCHAR(255),
    descricao    VARCHAR(255),
    data_criacao DATE,
    votacao_inicio DATE,
    votacao_fim DATE
);