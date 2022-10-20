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


$sql = "UPDATE alumnos SET matricula = '$matricula', "
        . "nombre = '$nombre' "
         . "apellidos = '$apellidos' "
        . "WHERE id = '$id'";
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
