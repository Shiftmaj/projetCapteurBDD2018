<?php
    $hostname = "54.37.152.134";
    $databaseName = "capture";
    $port = "5432";
    
    $username = "postgres";
    $password = "PG@546582";

    $dsn = 'pgsql:dbname='.$databaseName.';host='.$hostname.';port='.$port;
    $basededonnees = new PDO($dsn, $username, $password);
?>