<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />



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
				<div style="text-align: right">
                				<a href="${pageContext.request.contextPath}/logout"
                					class="button medium hpbottom">Se déconnecter</a>
                			</div>
                			<p>Bienvenue ${sessionScope.user}</p>

		<br>
        		<div style="text-align: left">
                				<a
                					href="http://localhost:8082/livres"
                					class="button medium hpbottom">Liste des livres</a>
                			</div>

        		<br>

        		<div style="text-align: left">
                                				<a
                                					href="http://localhost:8082/userprolongations"
                                					class="button medium hpbottom">Mes prolongations</a>
                                			</div>

                        		<br>

             		<div style="text-align: left">
                                     				<a
                                     					href="http://localhost:8082/userReservations"
                                     					class="button medium hpbottom">Mes réservations</a>
                                     			</div>

                             		<br>


			<h2>ma liste des prets</h2>

			<table class="table table-striped table-bordered">
				<tr>
					<th>nom livre</th>
					<th>date du pret</th>
					<th>date de retour</th>
					<th>pret deja prolonge?</th>
					<th>rendu?</th>







				</tr>

				<c:forEach var="tempPrets" items="${userPrets}">
				<c:url var="prolongationLink"
                						value="pret/${tempPrets.id}/prolongation/showFormProlo">
                					</c:url>

					<tr>
						<td>${tempPrets.nomLivre}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${tempPrets.datePret}" /> </td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${tempPrets.dateRetour}" /> </td>
						<td>${tempPrets.pretProlonge}</td>
						<td>${tempPrets.rendu}</td>


                    <c:if test="${tempPrets.pretProlonge==false && tempPrets.rendu==false && now<tempPrets.dateRetour}" var="variable">

						<td><a href="${prolongationLink}">Prolongation</a></td>

						</c:if>



					</tr>
				</c:forEach>
			</table>

			<fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd" />

		</div>
	</div>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>