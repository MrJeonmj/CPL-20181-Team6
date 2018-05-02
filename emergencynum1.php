<?php  

$link=mysqli_connect("localhost","root","", "test" );  
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

mysqli_set_charset($link,"utf8"); 

$ID = $_GET['ID'];
$STEP = $_GET['STEP'];


$sql="select count(*) from emergency where ID = '$ID' and STEP = '$STEP'";
$result=mysqli_query($link,$sql);
$data = mysql_fetch_array($result);  
if($result){  
    echo $data[0];
    
    }


}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 


 
mysqli_close($link);  
   
?>
