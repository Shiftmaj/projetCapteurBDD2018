CREATE OR REPLACE FUNCTION public.archive_brute()
RETURNS trigger
AS $BODY$
BEGIN
    IF(new.id % 500 == 0)
    THEN
        INSERT INTO public.brute_archivage(id, temperature, timestamp)
        SELECT id, temperature, timestamp FROM public.brute;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;

CREATE TRIGGER archive_brute BEFORE INSERT ON public.brute FOR EACH ROW EXECUTE PROCEDURE public.archive_brute();