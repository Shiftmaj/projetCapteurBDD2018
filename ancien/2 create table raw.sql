CREATE TABLE public."raw"
(
  id serial,
  temp real,
  "timestamp" timestamp without time zone,
  CONSTRAINT raw_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."raw"
  OWNER TO postgres;
