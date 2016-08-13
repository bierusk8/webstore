<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Zamówienia</title>
</head>
<body>

<section class="container">
<div class="row">
	<h1>Zamówione przedmioty:</h1>
	

<table class="table table-hover">

	<tr>
        <th>Imię Nazwisko:</th>
    	<th>Nick:</th>
    	<th>Ulica:</th>
    	<th>Kod pocztowy:</th>
    	<th>Kraj:</th>
    	<th>Numer telefonu:</th>
    	<th>Przedmiot:</th>
    	<th>Data dostarczenia:</th> 
    </tr>
    

	
<c:forEach items="${orders}" var="order">
			

  <tr>
    <td>${order.fullName}</td>
    <td>${order.nickName}</td>
    <td>${order.shippingAddress.streetName} ${order.shippingAddress.doorNo}</td>
    <td>${order.shippingAddress.zipCode}</td>
    <td>${order.shippingAddress.country}</td>
    <td>${order.phone}</td>
    <td>${order.productName}</td>
    <td>${order.shippingDate}</td> 
    
   </tr>

  
</c:forEach>
</table>

</div>
</section>
</body>
</html>