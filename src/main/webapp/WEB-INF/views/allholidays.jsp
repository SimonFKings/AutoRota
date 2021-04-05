<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 03/03/2020
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>All Holidays</title>
    <link href="/resources/css/autoRota.css" rel="stylesheet" type="text/css">
    <style>
        table {
            margin-top: 10px;
            text-align: center;

        }


        th {
            background-color: #6312c7;
            color: white;
            text-align: center;

        }

        tr:nth-child(even) {
            background-color: #cccccc
        }

        th, td {
            padding: 40px;
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
    <h1>All Holidays</h1>

    <table border="1">

        <tr>
            <th> Employee Name</th>
            <th> Start Date</th>
            <th> End Date</th>
            <th> Approved</th>
        </tr>


        <c:forEach items="${holidayList}" var="holiday" varStatus="status">
            <tr>
                <td>
                        ${holiday.getUser().getForename()}
                    <br>
                        ${holiday.getUser().getLastname()}

                </td>
                <td>${holiday.getStartDateS()}</td>
                <td>${holiday.getEndDateS()}</td>

                <td><c:choose>
                    <c:when test="${holiday.isAccepted()}">
                        Approved
                        <br/>
                    </c:when>
                    <c:otherwise>
                        Not Approved
                        <br/>
                    </c:otherwise>
                </c:choose>


                    <a id="approve${holiday.getId()}" href="/holiday/approved?holidayId=${holiday.getId()}">
                        Approve
                    </a>
                </td>
            </tr>
        </c:forEach>


    </table>

</div>
</body>
</html>
