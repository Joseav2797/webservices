<?php
session_start();
session_destroy();

header('Content-type: text/xml');
echo "<xml><resultado>1</resultado></xml>";
?>
