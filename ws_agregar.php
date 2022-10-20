<?php
session_start();
require_once 'conexion.php';

/*if (!isset($_SESSION["sesion"])) {
    
    header('Content-type: text/xml');
    echo "<xml><resultado>Sesión no iniciada</resultado></xml>";
    exit;
}
*/

$matricula = $db->real_escape_string($_GET["matricula"]);
$nombre = $db->real_escape_string($_GET["nombre"]);
$apellidos = $db->real_escape_string($_GET["apellidos"]);


$sql = "INSERT INTO alumnos VALUES (DEFAULT, '$matricula', '$nombre', "
        . "'$apellidos', NOW())";
$resultado = $db->query($sql);

if ($resultado) {

    header('Content-type: text/xml');
    echo "<xml><resultado>$db->insert_id</resultado></xml>";
}

else {

    header('Content-type: text/xml');
    echo "<xml><resultado>$db->error</resultado></xml>";
}
