<?php  

$conn = mysqli_connect("localhost", "root", "", "test");

$ID = $_GET['ID'];

$select_query = "SELECT MAX(DATE) FROM heartbeat WHERE ID = '$ID'";
$result_set = mysqli_query($conn, $select_query);
$row = mysqli_fetch_array($result_set);

$SEARCH = $row[0];

$select_query1 = "SELECT HEART_RATE FROM heartbeat WHERE DATE = '$SEARCH'";
$result_set1 = mysqli_query($conn, $select_query1);
$row1 = mysqli_fetch_array($result_set1);

echo $row1[0];



   mysqli_close($conn);
   
?>
