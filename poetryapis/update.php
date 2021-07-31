<?php
	$servername = "localhost";
	$username = "root";
	$password = "";
	$db = "poetrydb";

	$conn = new mysqli($servername, $username, $password, $db);
	if($conn -> connect_error)
	{
	 die("Connection failed: ".$conn -> connect_error);
	}

	$POETRY = $_POST['poetry_data'];
	$ID = $_POST['id'];

	$query = "UPDATE poetry SET poetry_data = '$POETRY' WHERE id='$ID' ";

	$result = $conn -> query($query);

	if($result == 1)
	{
	 $response = array("status" => "1", "message" => "Poetry Succesfully Updted");
	}
	else
	{
	 $response = array("status" => "0", "message" => "Poetry not Updated");
	}

	echo json_encode($response);

?>