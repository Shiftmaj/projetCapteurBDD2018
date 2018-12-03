CREATE TABLE public.view
(
  id serial,
  moyenne_temp numeric,
  "timestamp" timestamp without time zone,
  CONSTRAINT view_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.view
  OWNER TO postgres;
