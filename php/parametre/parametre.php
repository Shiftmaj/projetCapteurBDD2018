<?php
    include "../accesseur/ParametreDAO.php";

    // headers requis
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    switch ($_SERVER["REQUEST_METHOD"]){
       case "PUT":
       parse_str(file_get_contents("php://input"),$post_vars);
            if (isset($post_vars['superieur_a']) && isset($post_vars['inferieur_a'])) {

                $parametreDAO = new ParametreDAO();
                $parametres = new stdClass();
                $parametres->superieurA = $post_vars['superieur_a'];
                $parametres->inferieurA = $post_vars['inferieur_a'];

                if (isset($post_vars['quantite_entree'])) {
                    $parametres->quantiteEntree = $post_vars['quantite_entree'];
                    $parametres->heures = 0;

                }
                else if (isset($post_vars['heures'])) {
                    $parametres->heures = $post_vars['heures'];
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
          $listeParametres = $parametreDAO->listerParametres();

          header('Content-Type: application/xml');
          echo '<?xml version="1.0" encoding="UTF-8"?>';
          ?>
          <parametres>
          <?php
            foreach($listeParametres as $parametre)
            {
            ?>
                <parametre>
                    <heures><?=$parametre->heures?></heures>
                    <quantite_entree><?=$parametre->quantite_entree?></quantite_entree>
                    <superieur_a><?=$parametre->superieur_a?></superieur_a>
                    <inferieur_a><?=$parametre->inferieur_a?></inferieur_a> 
                </parametre>
            <?php
            }
          ?>
          </parametres>
          <?php
          
          break;
    }
?>