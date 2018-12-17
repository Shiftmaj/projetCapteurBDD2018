<?php

include_once "connexion.php";

class ParametreDAO
{

    function listerParametres() {
        global $manager;

        $commande = new MongoDB\Driver\Command(array('find' => 'parametres'));
        $curseur = $manager->executeCommand('capture', $commande);

        $parametres = new stdClass();

        foreach ($curseur as $ligne) {
            $parametres->heures = $ligne->heures;
            $parametres->quantite_entree = $ligne->quantite_entree;
            $parametres->superieur_a = $ligne->superieur_a;
            $parametres->inferieur_a = $ligne->inferieur_a;

            return $parametres;
        }
    }

    function modifierParametresMongo() {
        global $manager;

        $bulk = new MongoDB\Driver\BulkWrite(['ordered' => true]);
        $bulk->delete(['_id' => 0], ['limit' => 0]);

        $writeConcern = new \MongoDB\Driver\WriteConcern(\MongoDB\Driver\WriteConcern::MAJORITY, 1000);

        $result = $manager->executeBulkWrite('capture.parametre', $bulk, $writeConcern);

        return $result;
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