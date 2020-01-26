<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<title>Aukcija</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.w3-sidebar a {
	font-family: "Roboto", sans-serif
}

body, h1, h2, h3, h4, h5, h6, .w3-wide {
	font-family: "Montserrat", sans-serif;
}
</style>
<body class="w3-content" style="max-width: 1200px">

	<!-- Sidebar/menu -->
	<nav class="w3-sidebar w3-bar-block w3-white w3-collapse w3-top"
		style="z-index: 3; width: 250px" id="mySidebar">
		<div class="w3-container w3-display-container w3-padding-16">
			<i onclick="w3_close()"
				class="fa fa-remove w3-hide-large w3-button w3-display-topright"></i>
			<h3 class="w3-wide">
				<b> <a href="/Aukcinjo/pages/home.jsp"
					style="text-decoration: none"> AUKCINJO </a>
				</b>
			</h3>
		</div>
	</nav>

	<!-- Top menu on small screens -->
	<header class="w3-bar w3-top w3-hide-large w3-black w3-xlarge">
		<div class="w3-bar-item w3-padding-24 w3-wide">
			<a href="/Aukcinjo/pages/home.jsp" style="text-decoration: none">
				AUKCINJO </a>
		</div>
		<a href="javascript:void(0)"
			class="w3-bar-item w3-button w3-padding-24 w3-right"
			onclick="w3_open()"><i class="fa fa-bars"></i></a>
	</header>

	<!-- Overlay effect when opening sidebar on small screens -->
	<div class="w3-overlay w3-hide-large" onclick="w3_close()"
		style="cursor: pointer" title="close side menu" id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
	<div class="w3-main" style="margin-left: 250px">

		<!-- Push down content on small screens -->
		<div class="w3-hide-large" style="margin-top: 83px"></div>

		<!-- Top header -->
		<header class="w3-container w3-xlarge">
			<p class="w3-left">Messages with: ${receiver.firstName} ${receiver.lastName} </p>
		</header>


		<c:forEach items="${messages}" var="message">
			<c:set var="first" value="${message.serviceuser1.idServiceUser}"/>
			<c:set var="second" value="${idSender}"/>
			<c:set var="third" value="${idReceiver}"/>
			
			<c:if test="${first == second}">
				<p> <span style="border: 1px solid black; border-radius: 4px; padding:5px; background-color: #3f64eb; color:white;"> ${message.content} </span> <span style="float: right;"> ${message.serviceuser1.firstName} - <fmt:formatDate value="${message.date}" pattern = "HH:mm - dd.MM.yyyy"/>  </span> </p>
			</c:if>
			<c:if test="${first == third}">
				<p> <span style="border: 1px solid black; border-radius: 4px; padding:5px; background-color: gray; color:white;"> ${message.content} </span> <span style="float: right;"> ${message.serviceuser1.firstName} - <fmt:formatDate value="${message.date}" pattern = "HH:mm - dd.MM.yyyy"/> </span> </p>
			</c:if>
		</c:forEach>
		
		<br>

		<form action="/Aukcinjo/serviceUserController/sendMessage?idReceiver=${idReceiver}" method="post">
			<textarea name="newMessage" rows="4" cols="50" required="required"> </textarea> <br> <br>
			
			<input type="submit" value="SEND">
		</form>



		<!-- Footer -->
		<footer class="w3-padding-64 w3-light-grey w3-small w3-center"
			id="footer">

			<h2>Ovo je neki odlican footer</h2>

		</footer>

		<div class="w3-black w3-center w3-padding-24">
			Powered by <a href="https://www.w3schools.com/w3css/default.asp"
				title="W3.CSS" target="_blank" class="w3-hover-opacity">w3.css</a>
		</div>

		<!-- End page content -->
	</div>



	<script>
		// Accordion 
		function myAccFunc() {
			var x = document.getElementById("demoAcc");
			if (x.className.indexOf("w3-show") == -1) {
				x.className += " w3-show";
			} else {
				x.className = x.className.replace(" w3-show", "");
			}
		}

		// Click on the "Jeans" link on page load to open the accordion for demo purposes
		document.getElementById("myBtn").click();

		// Open and close sidebar
		function w3_open() {
			document.getElementById("mySidebar").style.display = "block";
			document.getElementById("myOverlay").style.display = "block";
		}

		function w3_close() {
			document.getElementById("mySidebar").style.display = "none";
			document.getElementById("myOverlay").style.display = "none";
		}
	</script>

</body>
</html>