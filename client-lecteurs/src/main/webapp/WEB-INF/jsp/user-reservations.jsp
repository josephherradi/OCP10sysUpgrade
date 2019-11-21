<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>liste livres</title>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />

</head>
<body>
	<div class="container">



		<div class="col-md-offset-2 col-md-9">
		<br>
		<p>
        <button type="button" name="back" onclick="history.back()">back</button>

        </p>
		<br>


			<h3>Mes réservations </h3>

			<table class="table table-striped table-bordered">
				<tr>
					<th>Date de demande</th>
					<th> Livre demandé</th>
					<th>statut réservation</th>
					<th>numero liste attente</th>
					<th>date retour plus proche</th>








				</tr>

				<c:forEach var="userReservations" items="${userReservations}">
				<c:url var="annulationLink" value="reservations/getResa">
				<c:param name="reservationId" value="${userReservations.reservationId}" />
                                                					</c:url>

					<tr>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${userReservations.dateDemande}" /> </td>
						<td>${userReservations.livre}</td>
						<td>${userReservations.statut}</td>
						<td>${userReservations.numListeAttente}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${userReservations.dateRetourPlusProche}" /></td>

						<c:if test="${userReservations.statut=='en attente'}" var="variable">

                                                <td><a href="${annulationLink}">Annuler</a></td>

                                                </c:if>


					</tr>
				</c:forEach>
			</table>

		</div>
	</div>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>