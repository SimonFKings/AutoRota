<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 26/04/2020
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Bookings</title>
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
            padding: 20px;
            text-align: center;

        }

        textarea {

            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
            border: thin solid rebeccapurple;
            border-radius: 4px;
            font-family: Roboto, sans-serif;
            line-height: 1.5em;
            padding: 0.5em 3.5em 0.5em 1em;

        }


    </style>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script
            src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

    <script>
        $(function () {
            var year

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
    <h1>Bookings</h1>

    <c:if test="${success == true}">
        <p> Booking added successfully</p>
    </c:if>

    <%--@elvariable id="booking" type="autorota"--%>
    <form:form name="booking" modelAttribute="booking" action="/booking/new" method="POST">


    <form:label path="customer">Customer Name</form:label>
    <form:input path="customer"></form:input>
    <form:errors path="customer" cssClass="error"></form:errors>


    <br>
    <br>


    <form:label path="dateString"> Date of Booking </form:label>
    <form:input path="dateString" id="pickdate" readonly="true" cssClass="form-control"></form:input>
    <form:errors path="dateString" cssClass="error"></form:errors>


    <br>
    <br>

    <form:label path="startTime"> Start Time</form:label>
    <form:select class="classic" path="startTime">
        <c:forEach var="bookingTime" items="${bookingTimes}">
            <form:option value="${bookingTime}" label="${bookingTime}">${bookingTime}</form:option>
        </c:forEach>
    </form:select>
    <form:errors path="startTime" cssClass="error"></form:errors>


    <form:label path="endTime"> End Time</form:label>
    <form:select class="classic" path="endTime">
        <c:forEach var="bookingTime" items="${bookingTimes}">
            <form:option value="${bookingTime}" label="${bookingTime}">${bookingTime}</form:option>
        </c:forEach>
    </form:select>
    <form:errors path="endTime" cssClass="error"></form:errors>
    <br>


    <br>

    <form:label path="description">Description of Booking</form:label>

    <form:textarea path="description"></form:textarea>
    <form:errors path="description" cssClass="error"></form:errors>


    <input type="submit" value="Submit"/>

    <table border="1">

        <tr>
            <th> Customer Name</th>
            <th> Date</th>
            <th> Start Time</th>
            <th> End Time</th>
            <th> Description</th>

        </tr>
        <c:forEach items="${bookingList}" var="booking" varStatus="status">
        <tr>
            <td>
                    ${booking.getCustomer()}
            </td>

            <td>
                    ${booking.getDateString()}

            </td>
            <td>
                    ${booking.getStartTime()}

            </td>

            <td>
                    ${booking.getEndTime()}

            </td>

            <td>
                    ${booking.getDescription()}

            </td>
        </tr>


        </c:forEach>


        </form:form>

</div>
</body>
</html>
