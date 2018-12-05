const postgresql = require('pg');
const identifiants = require('./identifiants.js');

var chaineDeConnexion = 'postgres://' + identifiants.identifiant + ':' + identifiants.motDePasse
    +'@' + identifiants.ip + ':' + identifiants.port + '/' + identifiants.baseDeDonnees;

const SQL_LISTE_TEMPERATURE = 'SELECT * FROM brute_archivage LIMIT 200;';
const SQL_MOYENNE_TEMPERATURE = 'SELECT moyenne_temperature, timestamp FROM vue ORDER BY ID DESC LIMIT 1;';
const SQL_MEDIANE_TEMPERATURE = 'SELECT * FROM brute_archivage ORDER BY temperature OFFSET ((SELECT count(*) FROM brute_archivage) / 2) LIMIT 1;';
const SQL_MINIMUM_TEMPERATURE = 'SELECT * FROM brute_archivage WHERE temperature = (SELECT MIN(temperature) FROM brute_archivage);';
const SQL_MAXIMUM_TEMPERATURE = 'SELECT * FROM brute_archivage WHERE temperature = (SELECT MAX(temperature) FROM brute_archivage);';
const SQL_MODE_TEMPERATURE = "SELECT COUNT(*) as compte, temperature FROM brute_archivage GROUP BY brute_archivage.temperature ORDER BY compte DESC LIMIT 1;";

exports.listerTemperatures = async function listerTemperatures() {
    const basededonnees = new postgresql.Client(chaineDeConnexion);

    await basededonnees.connect();
    var listeTemperature = await basededonnees.query(SQL_LISTE_TEMPERATURE);
    await basededonnees.end();

    return listeTemperature.rows;
};

exports.moyenneTemperature = async function moyenneTemperature(){
    const basededonnees = new postgresql.Client(chaineDeConnexion);

    await basededonnees.connect();
    var moyTemp = await basededonnees.query(SQL_MOYENNE_TEMPERATURE);
    await basededonnees.end();

    return moyTemp.rows;
};

exports.medianeTemperature = async function medianeTemperature(){
    const basededonnees = new postgresql.Client(chaineDeConnexion);

    await basededonnees.connect();
    var medTemp = await basededonnees.query(SQL_MEDIANE_TEMPERATURE);
    await basededonnees.end();

    return medTemp.rows;
};

exports.minimumTemperature = async function minimumTemperature(){
    const basededonnees = new postgresql.Client(chaineDeConnexion);

    await basededonnees.connect();
    var minTemp = await basededonnees.query(SQL_MINIMUM_TEMPERATURE);
    await basededonnees.end();

    return minTemp.rows;
};

exports.maximumTemperature = async function maximumTemperature(){
    const basededonnees = new postgresql.Client(chaineDeConnexion);

    await basededonnees.connect();
    var maxTemp = await basededonnees.query(SQL_MAXIMUM_TEMPERATURE);
    await basededonnees.end();

    return maxTemp.rows;
};

exports.modeTemperature = async function modeTemperature(){
    const basededonnees = new postgresql.Client(chaineDeConnexion);

    await basededonnees.connect();
    var modTemp = await basededonnees.query(SQL_MODE_TEMPERATURE);
    await basededonnees.end();

    return modTemp.rows;

};
