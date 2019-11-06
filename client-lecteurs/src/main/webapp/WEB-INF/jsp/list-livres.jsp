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



		<div class="col-md-offset-2 col-md-9">
		<br>
		<p>
        <button type="button" name="back" onclick="history.back()">back</button>

        </p>
		<br>
					<h4>Recherche par categorie</h4>
        			<br>


				        			<form:form
                        				action="searchByCategorie"
                        				cssClass="form-horizontal" method="get">
                        				<br>
                        				<div class="form-group">
                        					<div class="col-md-offset-2 col-md-6">
                                                    <select class="form-control" name="categorie">
                                                        <option value="Litterature">Litterature</option>
                                                        <option value="Gestion">Gestion</option>
                                                        <option value="Sciences">Sciences</option>
                                                        <option value="BD">BD</option>
                                                        <option value="Magazine">Magazine</option>
                                                        <option value="Roman">Roman</option>

                                                      </select>
                                                      <br>
                        						<button type="submit" class="btn btn-default btn-sm">Chercher</button>
                        					</div>
                        				</div>


                        			</form:form>



		<br>

			<h2>Liste des livres</h2>

			<table class="table table-striped table-bordered">
				<tr>
					<th>nom</th>
					<th>description</th>
					<th>categorie</th>
					<th>disponibilite</th>





				</tr>

				<c:forEach var="tempLivres" items="${livresList}">
				<c:url var="reservationLink"
                                						value="livre/${tempLivres.id}/reservation/showFormResa">
                                					</c:url>

					<tr>
						<td>${tempLivres.nom}</td>
						<td>${tempLivres.description}</td>
						<td>${tempLivres.categorie}</td>
						<td>${tempLivres.disponibilite}</td>

					    <c:if test="${tempLivres.disponibilite==0}" var="variable">

                        <td><a href="${reservationLink}">Réservation</a></td>

                        </c:if>
					</tr>
				</c:forEach>
			</table>

		</div>
	</div>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>