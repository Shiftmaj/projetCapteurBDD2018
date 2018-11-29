<?php

if($_SERVER["REQUEST_METHOD"]==='GET'){

    include "../accesseur/StatistiqueDAO.php";

    $statistiqueDAO = new StatistiqueDAO();

    $listeStats = $statistiqueDAO->lireTemperatureMediane();

    header('Content-Type: application/xml');
    echo '<?xml version="1.0" encoding="UTF-8"?>';

    foreach($listeStats as $stats)
    {
    ?>
        <temperature>
            <mediane><?=$stats->mediane?></mediane>
        </temperature>
    <?php
    }
}
else{
    die();
}
?>