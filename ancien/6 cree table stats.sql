CREATE TABLE public.stats
(
  id integer NOT NULL DEFAULT nextval('stats_id_seq'::regclass),
  min double precision,
  max double precision,
  mod double precision,
  med double precision,
  "timestamp" timestamp without time zone,
  CONSTRAINT pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.stats
  OWNER TO postgres;