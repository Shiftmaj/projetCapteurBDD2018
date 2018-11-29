<?php

if($_SERVER["REQUEST_METHOD"]==='GET'){

    include "../accesseur/StatistiqueDAO.php";

    $statistiqueDAO = new StatistiqueDAO();

    $listeStats = $statistiqueDAO->lireTemperatureMoyenne();

    header('Content-Type: application/xml');
    echo '<?xml version="1.0" encoding="UTF-8"?>';

    foreach($listeStats as $stats)
    {
    ?>
        <temperature>
            <moyenne><?=$stats->moyenne?></moyenne>
        </temperature>
    <?php
    }
}
?>