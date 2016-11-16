<%@ page contentType="text/html; charset=UTF-8"%>

<html><head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
	  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	
	

<link href="Style.css" rel="stylesheet" type="text/css" media="all" />
<link href="Base.css" rel="stylesheet" type="text/css" media="all" />
<div class="navbar navbar-inverse" >
    
    <div class="nav navbar-nav ">
    <form  name="logout" method="post"  action="/logout">
      <li> <input  type="submit" name="logout"class="glyphicon glyphicon-user"></input></li>
    </form></div>
    
</div>
  
</head>
 
<style>

.navtodo{
padding: 50px 30px 50px 80px;

}

.todo-list li {
	display: table;
	position: relative;
    font-size: 20px;
	border-bottom: 1px solid #ededed;
	width: 100%;
	height: 60px;
	    color: white;
	
}

ul.todo-list {
	margin: 10px auto;
	padding: 0px;
}

.elements {
	width: 50%;
	position: relative;
	float: left;
	padding: 10px;
    
}

.elements-btn {
	margin: auto;
	width: 40px;
	position: relative;
	float: right;
	padding: 10px;
}

h5 {
	margin: 0px;
	padding: 0px;
	    color: white;
	        font-size: 21px;
	    
	
}

.inputDiv {
	float: left;
	position: absolute;
	height: 50px;
	width: 50px;
}

input.inputRecord {
	height: 50px;
	width: 350px;
	font-size: 20px;
	color:black
	}

	ul.todo-list{
	    font-size: 20px;
    font-style: italic;
}
	body{
	 background-image: url("hnck7801.jpg");
	 }
	.dropbtn {
    background-color: #4CAF50;
    color: white;
    padding: 16px;
    font-size: 16px;
    border: none;
    cursor: pointer;
}

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

.dropdown-content a:hover {background-color: #f1f1f1}

.dropdown:hover .dropdown-content {
    display: block;
}

.dropdown:hover .dropbtn {
    background-color: #3e8e41;
}
.container{
  width: 500px;
    margin: auto;
}
label {
    padding: 23px 30px 0px 9px;
        font-weight: 500;
    
}
button.destroy {
width:23px 30px 0px 9px;
}
span.delete {
    margin-top: 21px;
    padding-left: 299px;
}
</style>





<body>


	<section id="main" data-module="list">

		<div class="container">
			<input class="toDoInput" type="textfield"
				style="padding: 20px; width: 100%; font-size:20px"></input>
		
		<ul class="todo-list">
		</ul>
	</div>
	</section>

	<script>
		$( document ).ready(function() {
			
			$.ajax({
				url:"/retrieve",
				success:function(data){
					
					
					   var htmlElement = "";

					 var jsonData = JSON.parse(data) 
					 for (var i = 0; i < jsonData.length; i++) //The json object has lenght
		                {
		                    var object = jsonData[i]; //You are in the current object
		                    htmlElement += '<li>'
		                    +'<div id='+object.key+' class="elements"><h5>'+object.data+'</div>'
		                    +'<div class='+object.key+' myVal='+object.key+' id="input" hidden><input value='+object.data+' class="inputRecord"  id="record"type="text"/></div>'
		                    +'<div class="elements-btn"><span  class="destroy" id='+object.key+'>Delete</span></div>'
		                    +'<div class="elements-btn"><span id='+object.key+' class="editRecord" id='+object.key+'>Edit</span></div>'
		                    +'</li>';
		                    
		                }
						
					 	$('.todo-list').html(htmlElement);
					 	commonFunction();
						
					
				}
				
			});

		/* 	$( "#input" ).keypress(function(event) {
				
			
				var keycode = (event.keyCode ? event.keyCode : event.which);
				if(keycode == "13"){
					alert($("#input"); */
					/* var val = $(".toDoInput").val();
					$.ajax({
						url:"/addsave",
						data:"data="+val,
						sucess:function(data){
							alert(data);
						}
						 */
				//	});
			
				/* }}); */
		
				
			
			});
			
		
		
		
		
		$( ".toDoInput" ).keypress(function(event) {
				
				
				var keycode = (event.keyCode ? event.keyCode : event.which);
				if(keycode == "13"){
				

					
					var val = $(".toDoInput").val();
					$.ajax({
						url:"/addsave",
						type:"POST",
						data:"data="+val,
						success:function(data){
							console.log("i am here");
							var myData = JSON.parse(data);
							alert(data);
						   var htmlElement = '<li>'
				                    +'<div id='+myData.key+' class="elements"><h5>'+myData.data+'</div>'
				                    +'<div class='+myData.key+' myVal='+myData.key+' id="input" hidden><input value='+myData.data+' class="inputRecord"  id="record"type="text"/></div>'
				                    +'<div class="elements-btn"><span  class="destroy" id='+myData.key+'>Delete</span></div>'
				                    +'<div class="elements-btn"><span id='+myData.key+' class="editRecord" id='+myData.key+'>Edit</span></div>'
				                    +'</li>';
							$('.todo-list').append(htmlElement);
							$('.toDoInput').val(""); 
							
							commonFunction();
						}
						
					});
					
					
					
					
				

				}
				
				
			
				
				});
		
		function commonFunction(){
			
			
			$('.destroy').click(function(e){
				var id=$(e.currentTarget).attr('id');
			 	$.ajax({
					url:"/destroy",
					type:"POST",
					data:"key="+id,
					success:function(data){
						$(e.currentTarget).parent().parent().remove();
					}
				})
	 			});
			
			$('.editRecord').click(function(e){
				var id2=$(e.currentTarget).attr('id');
				$("#"+id2).hide();
				$("."+id2).show();
			});
			
			$('.inputRecord').keypress(function(e) {
				var keycode = (event.keyCode ? event.keyCode : event.which);
				if(keycode == "13"){
				console.log($(e.currentTarget));
				  var data = {};
				
				  data.key = $(e.currentTarget).parent().attr('myVal');
				  data.data = $(e.currentTarget).val();
				 
				  $(this).hide();
				  $('#'+data.key).show();
				  $('#'+data.key+'> h5').text(data.data);
				   
				   $.ajax({
						url:"/update",
						type:"POST",
						contentType: "application/json",
						data:JSON.stringify(data),
						success:function(data){
							 console.log(JSON.parse(data));
							 var myVal = JSON.parse(data);
							 var myKey = myVal.key;
							 
							
							 
							/*  var val = $("#record").val();
							 
							 var myList = '<li id='+val+'><label>'+val+'</label><button id='+val+' class="destroy"></button></li>';
							$('.todo-list').append(myList); */
							/* $('.'+myKey+' >input').hide();
							$('#'+myKey+' h5').html(myVal.data);
							$('#'+myKey+'').show(); */
							 
							
							 
								
							
								
							
						}
				   
				});  
				}
		    
	});
			
			
			
		}
		
		
	
	</script>
</body>
</html>
