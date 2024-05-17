CREATE TABLE public.registro_votacao
(
    id           SERIAL PRIMARY KEY,
    pauta_id   SERIAL NOT NULL,
    associado_id SERIAL NOT NULL,
    voto         BOOLEAN,
    data_voto    DATE,
    FOREIGN KEY (pauta_id) REFERENCES public.pauta (id),
    FOREIGN KEY (associado_id) REFERENCES public.associado (id)
);
