<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Liste des Congés</title>
    <style>
        .short-leave {
            color: green;
            font-weight: bold;
        }
        .long-leave {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h1>Liste des Congés</h1>

<!-- Display success or error messages -->
<!-- Display success or error messages -->
<c:if test="${not empty param.message}">
    <p><strong>${param.message}</strong></p>
</c:if>

<c:if test="${not empty congesList}">
    <table border="1">
        <thead>
        <tr>
            <th>Numéro</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Nombre de jours</th>
            <th>Service</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="conge" items="${congesList}">
            <tr>
                <td>${conge.numero}</td>
                <td>${conge.nom}</td>
                <td>${conge.prenom}</td>
                <td class="${conge.nbj <= 10 ? 'short-leave' : 'long-leave'}">${conge.nbj}</td>
                <td>${conge.service}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty congesList}">
    <p>Aucun congé trouvé.</p>
</c:if>

<!-- Form to submit a new congé -->
<h2>Ajouter un Congé</h2>
<form action="conge" method="POST">
    <div>
        <label for="Numero">Numéro :</label>
        <input type="text" id="Numero" name="Numero" required>
    </div>
    <div>
        <label for="Nom">Nom :</label>
        <input type="text" id="Nom" name="Nom" required>
    </div>
    <div>
        <label for="Prenom">Prénom :</label>
        <input type="text" id="Prenom" name="Prenom" required>
    </div>
    <div>
        <label for="NBJ">Nombre de Jours :</label>
        <input type="number" id="NBJ" name="NBJ" required>
    </div>
    <div>
        <label for="Service">Service :</label>
        <input type="text" id="Service" name="Service" required>
    </div>
    <div>
        <button type="submit">Envoyer</button>
    </div>
</form>
</body>
</html>
