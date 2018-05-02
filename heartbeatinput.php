<?php
$link=mysqli_connect("localhost","root","","test"); 
if (!$link)  
{ 
   echo "MySQL ?묒냽 ?먮윭 : ";
   echo mysqli_connect_error();
   exit();
}  
 
$ID = $_GET['ID'];
$RATE = $_GET['RATE'];
$DATE = $_GET['DATE'];

if ($ID !="" and $RATE !="" and $DATE !=""){   

$sql="insert into heartbeat(ID,HEART_RATE,DATE) values('$ID','$RATE','$DATE')";
    $result=mysqli_query($link,$sql);  
 
    if($result){  
       echo "input_success";  
    }  
    else{  
       echo "error"; 
    } 
}
else {
    echo "blank_existed";
}

mysqli_close($link);
?>

