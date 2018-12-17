<?php

include_once "connexion.php";

class StatistiqueDAO
{

    function listerStatistiquesCompletes()
    {
        global $basededonnees;
        $SQL_LISTER_STATISTIQUES = "SELECT * FROM public.statistiques ORDER BY id DESC LIMIT 1";
        $requeteListeStatistiques = $basededonnees->prepare($SQL_LISTER_STATISTIQUES);
		$requeteListeStatistiques->execute();
		return $requeteListeStatistiques->fetchAll(PDO::FETCH_OBJ);
    }

    function lireTemperatureMinimum()
    {
        global $basededonnees;
        $SQL_LIRE_TEMPERATURE_MINIMUM = "SELECT MIN(temperature) AS minimum from public.brute;";
        $requete = $basededonnees->prepare($SQL_LIRE_TEMPERATURE_MINIMUM);
		$requete->execute();
		return $requete->fetchAll(PDO::FETCH_OBJ);
    }

    function lireTemperatureMaximum()
    {
        global $basededonnees;
        $SQL_LIRE_TEMPERATURE_MAXIMUM = "SELECT MAX(temperature) AS maximum from public.brute;";
        $requete = $basededonnees->prepare($SQL_LIRE_TEMPERATURE_MAXIMUM);
		$requete->execute();
		return $requete->fetchAll(PDO::FETCH_OBJ);
    }

    function lireTemperatureMoyenne()
    {
        global $basededonnees;
        $SQL_LIRE_TEMPERATURE_MOYENNE = "SELECT AVG(temperature) AS moyenne from public.brute;";
        $requete = $basededonnees->prepare($SQL_LIRE_TEMPERATURE_MOYENNE);
		$requete->execute();
		return $requete->fetchAll(PDO::FETCH_OBJ);
    }

    function lireTemperatureMode()
    {
        global $basededonnees;
        $SQL_LIRE_TEMPERATURE_MODE = "SELECT COUNT(*) as compte, temperature as mode FROM public.brute GROUP BY public.brute.temperature ORDER BY compte DESC LIMIT 1;";
        $requete = $basededonnees->prepare($SQL_LIRE_TEMPERATURE_MODE);
		$requete->execute();
		return $requete->fetchAll(PDO::FETCH_OBJ);
    }

    function lireTemperatureMediane()
    {
        global $basededonnees;
        $SQL_LIRE_TEMPERATURE_MEDIANE = "SELECT temperature as mediane FROM public.brute ORDER BY temperature OFFSET ((SELECT count(*) FROM public.brute) / 2) LIMIT 1;";
        $requete = $basededonnees->prepare($SQL_LIRE_TEMPERATURE_MEDIANE);
		$requete->execute();
		return $requete->fetchAll(PDO::FETCH_OBJ);
    }

}

?>