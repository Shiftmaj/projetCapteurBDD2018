<?php
    $hostname = "54.37.152.134";
    $databaseName = "capture";
    $port = "5432";
    
    $username = "postgres";
    $password = "PG@546582";

    $dsn = 'pgsql:dbname='.$databaseName.';host='.$hostname.';port='.$port;
    //$basededonnees = new PDO($dsn, $username, $password);

    $manager = new MongoDB\Driver\Manager(
        'mongodb://capture:capture9182@capture0-shard-00-00-sx85f.mongodb.net:27017,capture0-shard-00-01-sx85f.mongodb.net:27017,capture0-shard-00-02-sx85f.mongodb.net:27017/test?ssl=true&replicaSet=Capture0-shard-0&authSource=admin&retryWrites=true'
    );
  
?>