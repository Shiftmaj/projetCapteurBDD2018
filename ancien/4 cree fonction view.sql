CREATE OR REPLACE FUNCTION public.sauvegarde_temp_view()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
BEGIN
IF (new.id % 40) = 0 THEN 
INSERT INTO public.view (moyenne_temp, timestamp) VALUES ((SELECT AVG(temp) FROM public.raw WHERE id >= (new.id - 39)), current_timestamp);
END IF;																						
return new;																					
END
$function$
