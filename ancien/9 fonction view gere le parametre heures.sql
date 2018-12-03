CREATE OR REPLACE FUNCTION public.sauvegarde_temperature_vue()
 RETURNS trigger
 
AS $function$
BEGIN
    IF (new.id % (SELECT quantite_entree FROM parametres LIMIT 1)) = 0 THEN
    INSERT INTO public.vue (moyenne_temperature, timestamp) VALUES ((SELECT AVG(temperature) FROM public.brute WHERE id >= (new.id - ((SELECT quantite_entree FROM parametres LIMIT 1)-1))), current_timestamp);
    IF(SELECT quantite_entree FROM parametres LIMIT 1) > 0 THEN
    INSERT INTO public.statistiques (minimum, maximum, quantite_entree, mode, mediane, moyenne, timestamp) VALUES (
        (SELECT superieur_a FROM parametres LIMIT 1),
        (SELECT inferieur_a FROM parametres LIMIT 1),
    	(SELECT quantite_entree FROM parametres LIMIT 1),
        (SELECT temperature FROM brute WHERE id >= (new.id - ((SELECT quantite_entree FROM parametres LIMIT 1)-1)) GROUP BY brute.temperature ORDER BY (SELECT COUNT(*) FROM brute WHERE id >= (new.id - ((SELECT quantite_entree FROM parametres LIMIT 1)-1))) DESC LIMIT 1),
        (SELECT temperature FROM brute WHERE brute.id > ((SELECT MAX(id) FROM brute) - ((SELECT quantite_entree FROM parametres LIMIT 1)-1)) ORDER BY temperature OFFSET (SELECT quantite_entree FROM parametres LIMIT 1)/2 LIMIT 1),
    	((SELECT AVG(temperature) FROM ( SELECT temperature FROM public.brute where temperature > (SELECT superieur_a FROM parametres LIMIT 1) and temperature < (SELECT inferieur_a FROM parametres LIMIT 1) GROUP BY id order by id desc limit (SELECT quantite_entree FROM parametres LIMIT 1)) as brute)),
        current_timestamp
    );
    END IF;
    IF(SELECT heures FROM parametres LIMIT 1) > 0 THEN 
    INSERT INTO public.statistiques (minimum, maximum, quantite_entree, mode, mediane, moyenne, timestamp) VALUES (
        (SELECT superieur_a FROM parametres LIMIT 1),
        (SELECT inferieur_a FROM parametres LIMIT 1),
    	(SELECT quantite_entree FROM parametres LIMIT 1),
        (SELECT temperature FROM brute WHERE (DATE_PART('day', current_timestamp - timestamp ) * 24 + DATE_PART('hour', current_timestamp - timestamp)) <= (SELECT heures FROM parametres LIMIT 1) GROUP BY brute.temperature ORDER BY (SELECT COUNT(*) FROM brute WHERE (DATE_PART('day', current_timestamp - timestamp ) * 24 + DATE_PART('hour', current_timestamp - timestamp))<=(SELECT heures FROM parametres LIMIT 1))),
        (SELECT temperature FROM brute WHERE (DATE_PART('day', current_timestamp - timestamp ) * 24 + DATE_PART('hour', current_timestamp - timestamp)) <= (SELECT heures FROM parametres LIMIT 1) ORDER BY temperature OFFSET (SELECT count(*) FROM brute WHERE (DATE_PART('day', current_timestamp - timestamp ) * 24 + DATE_PART('hour', current_timestamp - timestamp)) <= (SELECT heures FROM parametres LIMIT 1))/2),
    	((SELECT AVG(temperature) FROM ( SELECT temperature FROM public.brute where temperature > (SELECT superieur_a FROM parametres LIMIT 1) and temperature < (SELECT inferieur_a FROM parametres LIMIT 1) GROUP BY id order by id desc limit (SELECT count(*) FROM brute WHERE (DATE_PART('day', current_timestamp - timestamp ) * 24 + DATE_PART('hour', current_timestamp - timestamp)) <= (SELECT heures FROM parametres LIMIT 1))) as brute)),
        current_timestamp
    );
    END IF;
    END IF;
    RETURN new;
END;
$function$ LANGUAGE plpgsql;
