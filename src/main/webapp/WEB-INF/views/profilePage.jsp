<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 14/11/2019
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Profile Page</title>
    <link href="/resources/css/autoRota.css" rel="stylesheet" type="text/css">
<style>
    p {
        text-align: center;
        color:black;

    }

    h3 {
        text-align: center;
        color:#6312c7;

    }
    h1{
        text-align: center;

    }

    input[type=text], [type=password] {
        width: 30%;
        text-align: center;
    }

</style>
</head>
<body>


<ul>
    <li><a href="<c:url value="/home" />">Home Page</a></li>
    <c:if test="${admin == true}">
        <li><a href="<c:url value="/register/" />">Register</a></li>
    </c:if>
    <li> <a href="<c:url value="/my-profile/" />">Profile</a></li>
    <li> <a href="<c:url value="/rota/today" />">Rota</a></li>
    <li> <a href="<c:url value="/availability/" />">Availability</a></li>
    <li> <a href="<c:url value="/holiday/" />">Holidays</a></li>
    <c:if test="${admin == true}">
        <li> <a href="<c:url value="/booking/" />">Bookings</a></li>
        <li> <a href="<c:url value="/settings/" />">Settings</a></li>
    </c:if>
    <li style="float:right"> <a href="<c:url value="/user-logout"/>">Log Out</a></li>

</ul>

<div class="format">

<h1>Profile Page</h1>

    <c:if test="${success == true}">
        <p> Changes Saved</p>
    </c:if>



<%--@elvariable id="user" type="autorota"--%>
<form:form name="user" modelAttribute="user" action="/my-profile/edit" method="POST" >


<h3>First Name </h3>
    <p>${firstname} </p>

<h3>Last Name </h3>
    <p>${lastname} </p>

<h3>Email Address </h3>
    <form:input path="email" ></form:input>
    <br>
    <form:errors path ="email" cssClass="error"></form:errors>
    <h3>Password </h3>
    <form:password path="password"></form:password>
    <br>
    <form:errors path ="password" cssClass="error"></form:errors>
    <h3>Confirm Password </h3>
    <form:password path="confirmPassword"></form:password>
    <br>
    <form:errors path ="confirmPassword" cssClass="error"></form:errors>

    <h3>Phone Number </h3>
    <form:input path="phoneNum" ></form:input>
    <br>
    <form:errors path ="phoneNum" cssClass="error"></form:errors>


    <h3>Job Title </h3>
    <p>${jobTitle} </p>

    <h3>Date of Birth </h3>
    <p>${dob}  </p>


    <h3>Salary </h3>
    <p>£${salary} per hour</p>

    <input type="submit" value="Save Changes" />


</form:form>

</div>

</body>
</html>
