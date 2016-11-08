<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head onload="/hello">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
	
	
</head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>T3 • TodoNotMVC</title>
<link href="Style.css" rel="stylesheet" type="text/css" media="all" />
<link href="Base.css" rel="stylesheet" type="text/css" media="all" />
<style>
/* .toDoInput{
      		font-size: 24px;
      	}
		.todo-list {
	    	margin: 0;
	    	padding: 0;
	    	list-style: none;
		}
		.todo-list li {
		    position: relative;
		    font-size: 24px;
		    border-bottom: 1px solid #ededed;
		}
		.todo-list li label {
		    white-space: pre-line;
		    word-break: break-all;
		    padding: 10px 10px;
		    margin-left: 45px;
		    display: block;
		    line-height: 1.2;
		    transition: color 0.4s;
		    background-color: #fff;
		    margin-left: 0px;
		}
		/* .todo-list li .destroy {
		    display: block;
		    position: absolute;
		    top: 0;
		    right: 10px;
		    bottom: 0;
		    width: 40px;
		    height: 40px;
		    margin: auto 0;
		    font-size: 30px;
		    color: #cc9a9a;
		    margin-bottom: 11px;
		    transition: color 0.2s ease-out;
		}
		button {
    margin: 0;
    padding: 0;
    border: 0;
    background: none;
    font-size: 100%;
    vertical-align: baseline;
    font-family: inherit;
    font-weight: inherit;
    color: inherit;
    -webkit-appearance: none;
    appearance: none;
    -webkit-font-smoothing: antialiased;
    -moz-font-smoothing: antialiased;
    font-smoothing: antialiased;
}
.todo-list li .destroy:after {
    content: '×';
}
button, input[type="checkbox"] {
    outline: none;
} */
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
	}
	body{
	    padding: 126px 93px 35px 74px;
	    

body {
    padding: 126px 93px 35px 74px;
  
}
	}
	ul.todo-list{
	    font-size: 20px;
    font-style: italic;
}
	body{
	 background-image: url("bg.jpg");
	 }
	

</style>
</head>
<body>

	<section id="main" data-module="list">

		<div>
			<input class="toDoInput" type="textfield"
				style="padding: 20px; width: 100%; font-size:20px"></input>
		</div>
		<ul class="todo-list">
		</ul>

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
		                    +'<div class='+object.key+' myVal='+object.key+' id="input" hidden><input value='+object.data+' class="inputRecord"  type="text"/></div>'
		                    +'<div class="elements-btn"><span  class="destroy" id='+object.key+'>Delete</span></div>'
		                    +'<div class="elements-btn"><span id='+object.key+' class="editRecord" id='+object.key+'>Edit</span></div>'
		                    +'</li>';
		                    
		                }
						
					 	$('.todo-list').html(htmlElement);
						
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
							
							  var data = {};
							
							  data.key = $(e.currentTarget).parent().attr('myVal');
							  data.data = $(".inputRecord").val();
 								
 							 console.log(data);
							   
							   $.ajax({
									url:"/update",
									type:"POST",
									contentType: "application/json",
									data:JSON.stringify(data),
									success:function(data){
										alert(data);

									}
							   
							});  
							}
					    
				});
					
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
			$( ".toDoInput" ).keypress(function(event) {
				
			
				var keycode = (event.keyCode ? event.keyCode : event.which);
				if(keycode == "13"){
				

					
					var val = $(".toDoInput").val();
					$.ajax({
						url:"/addsave",
						type:"POST",
						data:"data="+val,
						sucess:function(data){
							alert(data);
						
						}
						
					});
					
					var myList = '<li id='+val+'><label>'+val+'</label><button id='+val+' class="destroy"></button></li>';
					$('.todo-list').append(myList);
					$('.toDoInput').val(""); 
					
					
				

				}
				
				
				});
			 
			
			});
		
	
	
	</script>
</body>
</html>
