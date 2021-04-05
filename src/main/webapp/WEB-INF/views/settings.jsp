<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 15/03/2020
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Settings</title>
    <link href="/resources/css/autoRota.css" rel="stylesheet" type="text/css">

</head>
<body>
<ul>
    <li><a href="<c:url value="/home" />">Home Page</a></li>
    <c:if test="${admin == true}">
        <li><a href="<c:url value="/register/" />">Register</a></li>
    </c:if>
    <li><a href="<c:url value="/my-profile/" />">Profile</a></li>
    <li><a href="<c:url value="/rota/today" />">Rota</a></li>
    <li><a href="<c:url value="/availability/" />">Availability</a></li>
    <li><a href="<c:url value="/holiday/" />">Holidays</a></li>

    <c:if test="${admin == true}">
        <li><a href="<c:url value="/booking/" />">Bookings</a></li>
        <li><a href="<c:url value="/settings/" />">Settings</a></li>
    </c:if>

    <li style="float:right"><a href="<c:url value="/user-logout"/>">Log Out</a></li>
</ul>
<div class="format">
    <h1> Settings</h1>

    <c:if test="${success == true}">
        <p> Changes Saved</p>
    </c:if>

    <form:form name="availability" modelAttribute="business" action="/settings/save" method="POST">

        <form:label path="name">Company Name </form:label>
        <form:input path="name"></form:input>
        <form:errors path="name" cssClass="error"></form:errors>


        <form:label path="country">Country code </form:label>
        <form:input path="country"></form:input>
        <form:errors path="country" cssClass="error"></form:errors>


        <form:label path="city">City </form:label>
        <form:input path="city"></form:input>
        <form:errors path="city" cssClass="error"></form:errors>


        <input type="submit" value="Submit"/>


    </form:form>
</div>
</body>
</html>
