<?php

include_once "connexion.php";

class ParametreDAO
{

    function listerParametresMongo() {
        global $manager;

        $commande = new MongoDB\Driver\Command(array('find' => 'parametres'));
        $curseur = $manager->executeCommand('capture', $commande);

        return($curseur->toArray());
    }

    function listerParametres(){
        global $basededonnees;
        $SQL_LISTER_PARAMETRES = "SELECT * FROM public.parametres LIMIT 1;";
        $requeteListeParametres = $basededonnees->prepare($SQL_LISTER_PARAMETRES);
		$requeteListeParametres->execute();
		return $requeteListeParametres->fetchAll(PDO::FETCH_OBJ);
    }

    function modifierParametres($parametres){
        global $basededonnees;
        $SQL_AJOUTER_PARAMETRE = "INSERT INTO public.parametres VALUES (:heures, :quantite_entree, :superieur_a, :inferieur_a)";
        $requete = $basededonnees->prepare($SQL_AJOUTER_PARAMETRE);
        $requete->bindParam(":heures", $parametres->heures);
        $requete->bindParam(":quantite_entree", $parametres->quantiteEntree);
        $requete->bindParam(":superieur_a", $parametres->superieurA);
        $requete->bindParam(":inferieur_a", $parametres->inferieurA);
        $requete->execute();
    }
}

?>