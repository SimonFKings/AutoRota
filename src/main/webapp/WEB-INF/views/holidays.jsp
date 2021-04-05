<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 02/03/2020
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Holidays</title>
    <link href="/resources/css/autoRota.css" rel="stylesheet" type="text/css">
    <style>
        table {
            margin-top: 10px;
            text-align: center;

        }

        ul {
            width: 100%;
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

        .button {
            display: block;
            background: #6312c7;
            padding: 10px;
            text-align: center;
            border-radius: 5px;
            color: white;
            font-weight: bold;


        }

    </style>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script
            src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

    <script>
        $(function () {

            $("#pickdate").datepicker({
                firstDay: 1,
                showWeek: true,
                dateFormat: 'dd/mm/yy'


            })
            $("#pickdate2").datepicker({
                firstDay: 1,
                showWeek: true,
                dateFormat: 'dd/mm/yy'


            })


        });

    </script>
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
    <h1>Holidays</h1>
    <c:if test="${admin == true}">
        <a class="button" href="<c:url value="/holiday/all" />">All Holidays</a>
    </c:if>
    <%--@elvariable id="holiday" type="autorota"--%>
    <form:form name="holiday" modelAttribute="holiday" action="/holiday/new" method="POST">

        <form:label path="startDateS"> Start Date </form:label>
        <form:input path="startDateS" id="pickdate" readonly="true" cssClass="form-control"></form:input>
        <form:errors path="startDateS" cssClass="error"></form:errors>


        <br/>


        <form:label path="endDateS"> End Date </form:label>
        <form:input path="endDateS" id="pickdate2" readonly="true" cssClass="form-control"></form:input>
        <form:errors path="endDateS" cssClass="error"></form:errors>

        <input type="submit" value="Submit"/>

    </form:form>
    <table border="1">

        <tr>
            <th> Start Date</th>
            <th> End Date</th>
            <th> Approved</th>
        </tr>
        <c:forEach items="${holidayList}" var="holiday" varStatus="status">

            <tr>

                <td>${holiday.getStartDateS()}</td>

                <td>${holiday.getEndDateS()}</td>

                <td>

                    <c:choose>
                        <c:when test="${holiday.isAccepted()}">
                            Approved
                            <br/>
                        </c:when>
                        <c:otherwise>
                            Not Approved
                            <br/>
                        </c:otherwise>
                    </c:choose>


                </td>


            </tr>

        </c:forEach>


    </table>


</div>
</body>
</html>
