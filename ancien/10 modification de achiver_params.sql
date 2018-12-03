CREATE OR REPLACE FUNCTION public.archive_parametres()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$Begin
INSERT INTO parametres_journal values (
(SELECT heures FROM parametres),
(SELECT quantite_entree FROM parametres),
(SELECT superieur_a FROM parametres),
 (SELECT inferieur_a FROM parametres),
 (SELECT id FROM parametres));

  INSERT INTO public.statistiques (minimum, maximum, quantite_entree, mode, mediane, moyenne, timestamp) VALUES (
    new.superieur_a,
    new.inferieur_a,
	new.quantite_entree,
    (SELECT temperature FROM brute WHERE id >= (new.id - (new.quantite_entree -1)) GROUP BY brute.temperature ORDER BY (SELECT COUNT(*) FROM brute WHERE id >= (new.id - (new.quantite_entree -1))) DESC LIMIT 1),
    (SELECT temperature FROM brute WHERE brute.id > ((SELECT MAX(id) FROM brute) - ((SELECT quantite_entree FROM parametres LIMIT 1)-1)) ORDER BY temperature OFFSET new.quantite_entree /2 LIMIT 1),
	((SELECT sum(temperature) FROM ( SELECT temperature FROM public.brute where temperature > new.superieur_a and temperature < new.inferieur_a GROUP BY id order by id desc limit 40) as brute)/40),
    current_timestamp
);
DELETE from parametres;
  return new;
 End
$function$
