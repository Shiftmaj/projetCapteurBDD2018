<?php
    include "../accesseur/ParametreDAO.php";

    // headers requis
    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: POST");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    if (isset($_POST['superieur_a']) && isset($_POST['inferieur_a'])) {

        $parametreDAO = new ParametreDAO();
        $parametres = new stdClass();
        $parametres->superieurA = $_POST['superieur_a'];
        $parametres->inferieurA = $_POST['inferieur_a'];

        if (isset($_POST['quantite_entree'])) {
            $parametres->quantiteEntree = $_POST['quantite_entree'];
            $parametres->heures = 0;

        }
        else if (isset($_POST['heures'])) {
            $parametres->heures = $_POST['heures'];
            $parametres->quantiteEntree = 0;

        }
        else {
            die();
        }

    }
    else {
        echo "CONNEXION FERMEE";
        die();
    }
    $parametreDAO->modifierParametres($parametres);
    echo "SUCCES";

?>