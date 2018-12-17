<?php
    include "../accesseur/ParametreDAO.php";

    // headers requis
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    $request = explode('/', trim($_SERVER['PATH_INFO'],'/'));
    $input = json_decode(file_get_contents('php://input'));
    
    switch ($_SERVER['REQUEST_METHOD']){
       case "PUT":
            if (isset($input->superieur_a) && isset($input->inferieur_a) && is_numeric($input->inferieur_a) && is_numeric($input->superieur_a)) {

                $parametreDAO = new ParametreDAO();
                $parametres = new stdClass();
                $parametres->superieurA = $input->superieur_a;
                $parametres->inferieurA = $input->inferieur_a;

                if (isset($input->quantite_entree) && is_integer($input->quantite_entree)) {
                    $parametres->quantiteEntree = $input->quantite_entree;
                    $parametres->heures = 0;

                }
                else if (isset($input->heures) && is_integer($input->heures)) {
                    $parametres->heures = $input->heures;
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
            break;

        case "GET":
            $parametreDAO = new ParametreDAO();
            $parametre = $parametreDAO->listerParametres();

            header('Content-Type: application/xml');
            echo '<?xml version="1.0" encoding="UTF-8"?>';
            ?>
            <parametres>
                <parametre>
                    <heures><?=$parametre->heures?></heures>
                    <quantite_entree><?=$parametre->quantite_entree?></quantite_entree>
                    <superieur_a><?=$parametre->superieur_a?></superieur_a>
                    <inferieur_a><?=$parametre->inferieur_a?></inferieur_a> 
                </parametre>
            </parametres>
            <?php
            break;

        default:
            echo "CONNEXION FERMEE";
            die();   
    }
?>