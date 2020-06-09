<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 07.06.2020
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <title>Title</title>
    <style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
    <h2> LIST OF MEALS </h2>
    <table class="t1">
        <tr class  = "descript_tr">
            <th>Date/time</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <c:forEach var = "item" items="${mealList}" >
            <fmt:parseDate value="${item.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
            <c:if test="${item.excess eq false}">
                <tr class="cell_false">
            </c:if>
            <c:if test="${item.excess eq true}">
                <tr class="cell">
            </c:if>
            <td class = "cells"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/></td>
            <td><c:out value ="${item.description}"/></td>
            <td><c:out value ="${item.calories}"/></td>
        </c:forEach>
    </table>



</body>
</html>
