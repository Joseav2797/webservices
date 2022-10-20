<?php
session_start();
require_once 'conexion.php';

/*if (!isset($_SESSION["sesion"])) {
    
    header('Content-type: text/xml');
    echo "<xml><resultado>Sesión no iniciada</resultado></xml>";
    exit;
}
*/

if (isset($_GET["texto_buscar"])) {

    $texto_buscar = $_GET["texto_buscar"];
    
} else {

    $texto_buscar = "";
}

$texto_buscar = $db->real_escape_string($texto_buscar);

  $sql = "SELECT * FROM alumnos 
                    WHERE matricula like '%$texto_buscar%' 
                        OR nombre like '%$texto_buscar%' 
                        OR apellidos like'%$texto_buscar%'";
$resultado = $db->query($sql);

header('Content-type: text/xml');

echo '<xml>';

while ($registro = $resultado->fetch_assoc()) {

    echo "<alumno>";

    foreach ($registro as $etiqueta => $valor) {

        echo "<$etiqueta>".htmlentities($valor)."</$etiqueta>";
    }

    echo "</alumno>";
}

echo '</xml>';
?>
