<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>liste livres</title>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />

</head>
<body>
	<div class="container">
		<div class="col-md-offset-1 col-md-11">

			<div class="panel-body">
				<h3>Annuler réservation?</h3>

				<form:form action="annulResa" cssClass="form-horizontal"
					method="post" modelAttribute="laReservationBean">
                   	<form:hidden path="reservationId" />
                   	<form:hidden path="notified" />
                   	<form:hidden path="utilisateur" />
                   	<form:hidden path="numListeAttente" />
                   	<form:hidden path="livre" />





                        					<div class="form-group">
                        						<div class="col-md-10">
                        							<br> <input type="submit" name="Valider">
                        						</div>
                        					</div>
				</form:form>




			</div>
		</div>
	</div>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>