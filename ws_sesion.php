<?php
session_start();
require_once 'conexion.php';

if (!isset($_POST["usuario"]) || !isset($_POST["password"])) {
    
    header('Content-type: text/xml');
    echo "<xml><resultado>No se recibieron parametros</resultado></xml>";
    exit;
}

$usuario = $_POST["usuario"];
$password = $_POST["password"];

$sql = "SELECT COUNT(id) FROM usuarios WHERE usuario = '$usuario' AND password = MD5('$password')";
$resultado = $db->query($sql);
$registro = $resultado->fetch_array();

if ($registro["COUNT(id)"] == 1) {

    $_SESSION["sesion"] = 1;
    header('Content-type: text/xml');
    echo "<xml><resultado>1</resultado></xml>";
}

else {

    header('Content-type: text/xml');
    echo "<xml><resultado>0</resultado></xml>";
}
?>