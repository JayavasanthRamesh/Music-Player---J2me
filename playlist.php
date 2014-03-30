<?php 
	mysql_connect("localhost","root",'');
	mysql_select_db("musicplayer");
	$query=mysql_query("select name,songs from playlists");
	while($row=mysql_fetch_array($query))
		echo "".$row['name']." ".$row['songs']."\n" ;
?>
