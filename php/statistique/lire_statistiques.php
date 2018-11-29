<?php

if($_SERVER["REQUEST_METHOD"]==='GET'){

    include "../accesseur/StatistiqueDAO.php";

    $statistiqueDAO = new StatistiqueDAO();

    $listeStats = $statistiqueDAO->listerStatistiquesCompletes();

    header('Content-Type: application/xml');
    echo '<?xml version="1.0" encoding="UTF-8"?>';
	?>

	<statistiques>
	<?php
	    foreach($listeStats as $stats)
	    {
		?>
	        <statistique>
	            <minimum><?=$stats->minimum?></minimum>
	            <maximum><?=$stats->maximum?></maximum>
	            <moyenne><?=$stats->moyenne?></moyenne>
	            <mode><?=$stats->mode?></mode>
	            <mediane><?=$stats->mediane?></mediane>
	        </statistique>
		<?php
	    }
	?>
	</statistiques>

<?php
}
?>
