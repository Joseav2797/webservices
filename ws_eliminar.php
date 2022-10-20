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

$sql = "DELETE FROM alumnos WHERE matricula = '$matricula'";
$resultado = $db->query($sql);

if ($resultado) {

    header('Content-type: text/xml');
    echo "<xml><resultado>1</resultado></xml>";
}

else {

    header('Content-type: text/xml');
    echo "<xml><resultado>'$db->error'</resultado></xml>";
}
?>
