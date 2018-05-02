<?php
$link=mysqli_connect("localhost","root","","test"); 
if (!$link)  
{ 
   echo "MySQL ?묒냽 ?먮윭 : ";
   echo mysqli_connect_error();
   exit();
}  
 
$ID = $_GET['ID'];
$DATE = $_GET['DATE'];
$STEP = $_GET['STEP'];

if ($ID !="" and $DATE !="" and $STEP !=""){   

$sql="insert into emergency(ID,DATE,STEP) values('$ID','$DATE','$STEP')";
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

