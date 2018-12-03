CREATE OR REPLACE FUNCTION public.sauvegarde_temp_view() RETURNS trigger LANGUAGE plpgsql AS $function$
BEGIN
IF (new.id % 40) = 0 THEN
INSERT INTO public.view (moyenne_temp, timestamp) VALUES ((SELECT AVG(temp) FROM public.raw WHERE id >= (new.id - 39)), current_timestamp);
INSERT INTO public.stats (min, max, mod, med, timestamp) VALUES (
    (SELECT MIN(temp) FROM raw WHERE id >= (new.id - 39)),
    (SELECT MAX(temp) FROM raw WHERE id >= (new.id - 39)),
    (SELECT temp FROM raw WHERE id >= (new.id - 39) GROUP BY raw.temp ORDER BY (SELECT COUNT(*) FROM raw WHERE id >= (new.id - 39)) DESC LIMIT 1),
    (SELECT temp FROM raw WHERE raw.id > ((SELECT MAX(id) FROM raw) - 39) ORDER BY temp OFFSET 20 LIMIT 1),
    current_timestamp
);
END IF;
return new;
END
$function$