<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<title> Aukcija LOL </title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.w3-sidebar a {font-family: "Roboto", sans-serif}
body,h1,h2,h3,h4,h5,h6,.w3-wide {font-family: "Montserrat", sans-serif;}
</style>
<body class="w3-content" style="max-width:1200px">

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-bar-block w3-white w3-collapse w3-top" style="z-index:3;width:250px" id="mySidebar">
  <div class="w3-container w3-display-container w3-padding-16">
    <i onclick="w3_close()" class="fa fa-remove w3-hide-large w3-button w3-display-topright"></i>
    <h3 class="w3-wide"><b> <a href="/Aukcinjo/pages/home.jsp" style="text-decoration: none"> AUKCINJO </a> </b></h3>
  </div>
</nav>

<!-- Top menu on small screens -->
<header class="w3-bar w3-top w3-hide-large w3-black w3-xlarge">
  <div class="w3-bar-item w3-padding-24 w3-wide"> <a href="/Aukcinjo/pages/home.jsp" style="text-decoration: none"> AUKCINJO </a> </div>
  <a href="javascript:void(0)" class="w3-bar-item w3-button w3-padding-24 w3-right" onclick="w3_open()"><i class="fa fa-bars"></i></a>
</header>

<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:250px">

  <!-- Push down content on small screens -->
  <div class="w3-hide-large" style="margin-top:83px"></div>
  
  <!-- Top header -->
  <header class="w3-container w3-xlarge">
    <c:if test="${empty loggedInUser}">
    	<p class="w3-left"> Aukcije </p>
    </c:if>
    <c:if test="${!empty loggedInUser}">
    	<p class="w3-left"> Hello, ${loggedInUser.firstName} </p>
    </c:if>
    
    <p class="w3-right">
      <c:if test="${empty loggedInUser}">
      	<a href="/Aukcinjo/pages/login.jsp" style="text-decoration: none; margin-right: 10px"> LOGIN </a>
      	<a href="/Aukcinjo/pages/register.jsp" style="text-decoration: none"> REGISTER </a>
      </c:if>
      <c:if test="${!empty loggedInUser}">
 		<a href="/Aukcinjo/serviceUserController/logoutUser" style="text-decoration: none; margin-left: 10px"> LOGOUT </a>
      </c:if>
      
    </p>
  </header>

  
	<form action="/Aukcinjo/auctionController/addNewAuction" method="post">
		<h3> 1. Add a new item </h3> <br> <br>
		Category <select name="itemCategory">
					<option value="all" selected> All </option>
					<c:forEach items="${categories}" var="category">
						<option value="${category.idCategory}"> ${category.name} </option>
					</c:forEach>
				</select> <br> <br>
		
		Name <br> <br>
		<input type="text" name="itemName" required="required"> <br> <br>
		Description <br> <br>
		<textarea rows="10" cols="30" name="itemDescription"></textarea> <br> <br>
		
		Item images <br> <br>
		Image 1 link <input type="text" name="itemImage1" required="required"> <br>
		Image 2 link <input type="text" name="itemImage2"> <br>
		Image 3 link <input type="text" name="itemImage3"> <br>
		Image 4 link <input type="text" name="itemImage4"> <br> <br>
		
		<h3> 2. Add it to a new auction </h3> <br> <br>
		Starting price <br> <br>
		<input type="text" name="bidStartingPrice" required="required"> <br> <br>
		
		<input type="submit" value="ADD A NEW AUCTION"> <br> <br>
	</form>

  <!-- Footer -->
  <footer class="w3-padding-64 w3-light-grey w3-small w3-center" id="footer">
    
  	<h2> Ovo je neki odlican footer </h2>
    
  </footer>

  <div class="w3-black w3-center w3-padding-24">Powered by <a href="https://www.w3schools.com/w3css/default.asp" title="W3.CSS" target="_blank" class="w3-hover-opacity">w3.css</a></div>

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