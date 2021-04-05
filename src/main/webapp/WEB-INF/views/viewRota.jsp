<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 06/02/2020
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>View Rota</title>
    <link href="/resources/css/autoRota.css" rel="stylesheet" type="text/css">

    <style>
        table {
            text-align: center;
            margin: 0 auto;


        }


        th {
            background-color: #6312c7;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #cccccc
        }

        th, td {
            padding: 26px;
        }

        body {
            background-color: #FFFFFF;
        }

        .submitt {
            background-color: #6312c7;
            /*color: white;*/
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            text-align: center;


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


                onSelect: function (dateText, inst) {
                    var date = new Date(dateText)
                    $(this).val($.datepicker.iso8601Week(date));

                    document.getElementById('year').value = date.getFullYear();


                }
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
<table border="1">
    <tr>
        <th></th>
        <th colspan="2">Monday <br>${mon}
            <c:if test="${validRota == true && rota.getDaysList().get(0).isApiCalled() }">
                <br>
                <br>Temp: ${rota.getDaysList().get(0).getTemp()}°C
                <br>
                Rain: ${rota.getDaysList().get(0).getPop()}%
                <br>
                ${rota.getDaysList().get(0).getWeatherDes()}
            </c:if>

            <c:if test="${validRota == true && rota.getDaysList().get(0).isBankHoliday()}">
                <br><br>${rota.getDaysList().get(0).getBankHolidayTitle()}
            </c:if>
            <c:if test="${validRota == true}">

                <br> <br>   ${rota.getDaysList().get(0).getNoBookings()} Bookings
            </c:if>
        </th>


        <th colspan="2">Tuesday <br> ${tue}
            <c:if test="${validRota == true && rota.getDaysList().get(1).isApiCalled()}">
                <br>
                <br>
                Temp: ${rota.getDaysList().get(1).getTemp()}°C
                <br>
                Rain: ${rota.getDaysList().get(1).getPop()}%
                <br>
                ${rota.getDaysList().get(1).getWeatherDes()}
            </c:if>
            <c:if test="${validRota == true && rota.getDaysList().get(1).isBankHoliday()}">
                <br><br>${rota.getDaysList().get(1).getBankHolidayTitle()}
            </c:if>
            <c:if test="${validRota == true}">

                <br> <br>   ${rota.getDaysList().get(1).getNoBookings()} Bookings
            </c:if>

        </th>
        <th colspan="2">Wednesday <br> ${wed}
            <c:if test="${validRota == true  && rota.getDaysList().get(2).isApiCalled()}">

                <br>
                <br>

                Temp: ${rota.getDaysList().get(2).getTemp()}°C
                <br>
                Rain: ${rota.getDaysList().get(2).getPop()}%
                <br>
                ${rota.getDaysList().get(2).getWeatherDes()}
            </c:if>
            <c:if test="${validRota == true && rota.getDaysList().get(2).isBankHoliday()}">
                <br><br>${rota.getDaysList().get(2).getBankHolidayTitle()}
            </c:if>
            <c:if test="${validRota == true}">

                <br> <br>   ${rota.getDaysList().get(2).getNoBookings()} Bookings

            </c:if>
        </th>
        <th colspan="2">Thursday <br> ${thu}
            <c:if test="${validRota == true && rota.getDaysList().get(3).isApiCalled()}">

                <br>
                <br>

                Temp: ${rota.getDaysList().get(3).getTemp()}°C
                <br>
                Rain: ${rota.getDaysList().get(3).getPop()}%
                <br>
                ${rota.getDaysList().get(3).getWeatherDes()}
            </c:if>
            <c:if test="${validRota == true && rota.getDaysList().get(3).isBankHoliday()}">
                <br><br>${rota.getDaysList().get(3).getBankHolidayTitle()}
            </c:if>
            <c:if test="${validRota == true}">

                <br> <br>   ${rota.getDaysList().get(3).getNoBookings()} Bookings
            </c:if>
        </th>
        <th colspan="2">Friday <br> ${fri}
            <c:if test="${validRota == true && rota.getDaysList().get(4).isApiCalled()}">

                <br>
                <br>

                Temp: ${rota.getDaysList().get(4).getTemp()}°C
                <br>
                Rain: ${rota.getDaysList().get(4).getPop()}%
                <br>
                ${rota.getDaysList().get(4).getWeatherDes()}
            </c:if>
            <c:if test="${validRota == true && rota.getDaysList().get(4).isBankHoliday()}">
                <br><br>${rota.getDaysList().get(4).getBankHolidayTitle()}
            </c:if>
            <c:if test="${validRota == true}">

                <br> <br>   ${rota.getDaysList().get(4).getNoBookings()} Bookings
            </c:if>
        </th>
        <th colspan="2">Saturday <br> ${sat}
            <c:if test="${validRota == true && rota.getDaysList().get(5).isApiCalled()}">

                <br>
                <br>

                Temp: ${rota.getDaysList().get(5).getTemp()}°C
                <br>
                Rain: ${rota.getDaysList().get(5).getPop()}%
                <br>
                ${rota.getDaysList().get(5).getWeatherDes()}
            </c:if>
            <c:if test="${validRota == true && rota.getDaysList().get(5).isBankHoliday()}">
                <br><br>${rota.getDaysList().get(5).getBankHolidayTitle()}
            </c:if>
            <c:if test="${validRota == true}">

                <br> <br>   ${rota.getDaysList().get(5).getNoBookings()} Bookings
            </c:if>
        </th>
        <th colspan="2">Sunday<br> ${sun}
            <c:if test="${validRota == true && rota.getDaysList().get(6).isApiCalled()}">

                <br>
                <br>

                Temp: ${rota.getDaysList().get(6).getTemp()}°C
                <br>
                Rain: ${rota.getDaysList().get(6).getPop()}%
                <br>
                ${rota.getDaysList().get(6).getWeatherDes()}
            </c:if>
            <c:if test="${validRota == true && rota.getDaysList().get(6).isBankHoliday()}">
                <br><br>${rota.getDaysList().get(6).getBankHolidayTitle()}
            </c:if>
            <c:if test="${validRota == true}">
                <br> <br>   ${rota.getDaysList().get(6).getNoBookings()} Bookings

            </c:if>
        </th>
    </tr>


    <%--@elvariable id="rota" type="autorota"--%>
    <form:form name="rota" modelAttribute="rota" action="/rota/" method="GET">

        <br>

        <form:label path="weekNum">Select the week of the rota you would like to Create, View or Edit </form:label>
        <form:input path="weekNum" id="pickdate" cssClass="form-control"></form:input>

        <form:label path="year">for the year</form:label>
        <form:input path="year" id="year" readonly="true"></form:input>


        <c:if test="${error == true}">

            <p class="error">  ${message} week ${rota.getWeekNum()} year ${rota.getYear()}</p>


        </c:if>


        <c:if test="${admin == true}">


            <input type="submit" name="create" value="Create"/>


            <input type="submit" name="edit" value="Edit"/>


        </c:if>
        <input type="submit" name="view" value="View"/>


    </form:form>

    <tr/>
    <c:if test="${validRota == true}">


    <c:forEach items="${rota.getDaysList().get(0).getShiftList()}" var="shift" varStatus="status">

    <tr>
        <td>

            <c:out value="${shift.getUser().getForename()}"/>

            <c:out value="${shift.getUser().getLastname()}"/>

        </td>

        <td>


                ${rota.getDaysList().get(0).getShiftList().get(status.index).shiftStart}


        </td>
        <td>
                ${rota.getDaysList().get(0).getShiftList().get(status.index).shiftEnd}


        </td>
        <td>
                ${rota.getDaysList().get(1).getShiftList().get(status.index).shiftStart}


        </td>
        <td>
                ${rota.getDaysList().get(1).getShiftList().get(status.index).shiftEnd}

        </td>
        <td>
                ${rota.getDaysList().get(2).getShiftList().get(status.index).shiftStart}

        </td>
        <td>
                ${rota.getDaysList().get(2).getShiftList().get(status.index).shiftEnd}

        </td>
        <td>
                ${rota.getDaysList().get(3).getShiftList().get(status.index).shiftStart}

        </td>
        <td>
                ${rota.getDaysList().get(3).getShiftList().get(status.index).shiftEnd}

        </td>
        <td>
                ${rota.getDaysList().get(4).getShiftList().get(status.index).shiftStart}

        </td>
        <td>
                ${rota.getDaysList().get(4).getShiftList().get(status.index).shiftEnd}

        </td>
        <td>
                ${rota.getDaysList().get(5).getShiftList().get(status.index).shiftStart}

        </td>
        <td>
                ${rota.getDaysList().get(5).getShiftList().get(status.index).shiftEnd}

        </td>
        <td>
                ${rota.getDaysList().get(6).getShiftList().get(status.index).shiftStart}

        </td>
        <td>
                ${rota.getDaysList().get(6).getShiftList().get(status.index).shiftEnd}

        </td>
        </c:forEach>

        </c:if>
    </tr>


</table>
</body>
</html>
