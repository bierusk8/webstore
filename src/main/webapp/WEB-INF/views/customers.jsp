<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Klienci</title>
</head>
<body>

<section class="container">
	<div class="row">
		<c:forEach items="${customers}" var="customer">
			<div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
			<div class="thumbnail">
			<div class="caption">
				<h2>${customer.name}</h2>
				<p>ID: ${customer.customerId}</p>
				<p>Przedmiot: ${customer.itemName}</p>
				<p><i>IP: ${customer.address}</i></p>
				<p>Liczba dokonanych zamowien: ${customer.noOfOrdersMade}</p>
			</div>
			</div>
			</div>
		</c:forEach>
	</div>
</section>
</body>
</html>