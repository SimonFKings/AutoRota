<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 11/04/2020
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Rota</title>
    <link href="/resources/css/autoRota.css" rel="stylesheet" type="text/css">
    <style>
        table {
            margin-top: 10px;

        }


        th {
            background-color: #6312c7;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #cccccc
        }

        th, td {
            padding: 9px;
            text-align: center;
        }

        body {
            background-color: #FFFFFF;
        }

        input {
            text-align: center;
            font-size: 10px;
            border: thin solid rebeccapurple;
            border-radius: 4px;
            /*display: inline-block;*/
            font-family: Roboto, sans-serif;
            line-height: 1.5em;
            width: 70px;
            height: 27px;


        }

        select {
            font-size: 10px;
            border: thin solid rebeccapurple;
            border-radius: 4px;
            font-family: Roboto, sans-serif;
            line-height: 1.5em;
            padding: 0.5em 3.5em 0.5em 1em;


            margin: 0;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            -webkit-appearance: none;
            -moz-appearance: none;
        }


        select.classic {
            background-image: linear-gradient(45deg, transparent 50%, #6312c7 50%),
            linear-gradient(135deg, #6312c7 50%, transparent 50%),
            linear-gradient(to right, #b7a6eb, #b7a6eb);
            background-position: calc(104% - 19px) calc(1em + 2px),
            calc(104% - 15px) calc(1em + 2px),
            100% 0;
            background-size: 5px 5px,
            5px 5px,
            2.5em 2.5em;
            background-repeat: no-repeat;
        }

    </style>

</head>
<body>
<ul>
    <li><a href="<c:url value="/home" />">Home Page</a></li>
    <li><a href="<c:url value="/register/" />">Register</a></li>
    <li><a href="<c:url value="/my-profile/" />">Profile</a></li>
    <li><a href="<c:url value="/rota/today" />">Rota</a></li>
    <li><a href="<c:url value="/availability/" />">Availability</a></li>
    <li><a href="<c:url value="/holiday/" />">Holidays</a></li>
    <li><a href="<c:url value="/booking/" />">Bookings</a></li>
    <li><a href="<c:url value="/settings/" />">Settings</a></li>
    <li style="float:right"><a href="<c:url value="/user-logout"/>">Log Out</a></li>

</ul>

<%--@elvariable id="rota" type="autorota"--%>
<form:form name="rota" modelAttribute="rota" action="/rota/edited" method="POST">

    <table border="1">
        <tr>
            <th></th>
            <th colspan="2">Monday <br>${mon}
                <c:if test="${rota.getDaysList().get(0).isApiCalled()}">
                    <br>
                    <br>
                    Temp: ${rota.getDaysList().get(0).getTemp()}°C
                    <br>
                    Rain: ${rota.getDaysList().get(0).getPop()}%
                    <br>
                    ${rota.getDaysList().get(0).getWeatherDes()}

                </c:if>
                <c:if test="${rota.getDaysList().get(0).isBankHoliday()}">
                    <br><br>${rota.getDaysList().get(0).getBankHolidayTitle()}
                </c:if>
                <br> <br> ${rota.getDaysList().get(0).getNoBookings()} Bookings
            </th>
            <th colspan="2">Tuesday <br> ${tue}
                <c:if test="${rota.getDaysList().get(1).isApiCalled()}">
                    <br>
                    <br>

                    Temp: ${rota.getDaysList().get(1).getTemp()}°C
                    <br>
                    Rain: ${rota.getDaysList().get(1).getPop()}%
                    <br>
                    ${rota.getDaysList().get(1).getWeatherDes()}
                </c:if>
                <c:if test="${rota.getDaysList().get(1).isBankHoliday()}">
                    <br><br>${rota.getDaysList().get(1).getBankHolidayTitle()}
                </c:if>
                <br> <br> ${rota.getDaysList().get(1).getNoBookings()} Bookings


            </th>
            <th colspan="2">Wednesday <br> ${wed}
                <c:if test="${rota.getDaysList().get(2).isApiCalled()}">

                    <br>
                    <br>

                    Temp: ${rota.getDaysList().get(2).getTemp()}°C
                    <br>
                    Rain: ${rota.getDaysList().get(2).getPop()}%
                    <br>
                    ${rota.getDaysList().get(2).getWeatherDes()}
                </c:if>
                <c:if test="${rota.getDaysList().get(2).isBankHoliday()}">
                    <br><br>${rota.getDaysList().get(2).getBankHolidayTitle()}
                </c:if>
                <br> <br> ${rota.getDaysList().get(2).getNoBookings()} Bookings

            </th>
            <th colspan="2">Thursday <br> ${thu}
                <c:if test="${rota.getDaysList().get(3).isApiCalled()}">

                    <br>
                    <br>

                    Temp: ${rota.getDaysList().get(3).getTemp()}°C
                    <br>
                    Rain: ${rota.getDaysList().get(3).getPop()}%
                    <br>
                    ${rota.getDaysList().get(3).getWeatherDes()}
                </c:if>
                <c:if test="${rota.getDaysList().get(3).isBankHoliday()}">
                    <br><br>${rota.getDaysList().get(3).getBankHolidayTitle()}
                </c:if>
                <br> <br> ${rota.getDaysList().get(3).getNoBookings()} Bookings

            </th>
            <th colspan="2">Friday <br> ${fri}
                <c:if test="${rota.getDaysList().get(4).isApiCalled()}">

                    <br>
                    <br>

                    Temp: ${rota.getDaysList().get(4).getTemp()}°C
                    <br>
                    Rain: ${rota.getDaysList().get(4).getPop()}%
                    <br>
                    ${rota.getDaysList().get(4).getWeatherDes()}
                </c:if>
                <c:if test="${rota.getDaysList().get(4).isBankHoliday()}">
                    <br><br>${rota.getDaysList().get(4).getBankHolidayTitle()}
                </c:if>
                <br> <br> ${rota.getDaysList().get(4).getNoBookings()} Bookings

            </th>
            <th colspan="2">Saturday <br> ${sat}
                <c:if test="${rota.getDaysList().get(5).isApiCalled()}">

                    <br>
                    <br>

                    Temp: ${rota.getDaysList().get(5).getTemp()}°C
                    <br>
                    Rain: ${rota.getDaysList().get(5).getPop()}%
                    <br>
                    ${rota.getDaysList().get(5).getWeatherDes()}
                </c:if>
                <c:if test="${rota.getDaysList().get(5).isBankHoliday()}">
                    <br><br>${rota.getDaysList().get(5).getBankHolidayTitle()}
                </c:if>
                <br> <br> ${rota.getDaysList().get(5).getNoBookings()} Bookings

            </th>
            <th colspan="2">Sunday<br> ${sun}
                <c:if test="${rota.getDaysList().get(6).isApiCalled()}">

                    <br>
                    <br>
                    Temp: ${rota.getDaysList().get(6).getTemp()}°C
                    <br>
                    Rain: ${rota.getDaysList().get(6).getPop()}%
                    <br>
                    ${rota.getDaysList().get(6).getWeatherDes()}
                </c:if>
                <c:if test="${rota.getDaysList().get(6).isBankHoliday()}">
                    <br><br>${rota.getDaysList().get(6).getBankHolidayTitle()}
                </c:if>
                <br> <br> ${rota.getDaysList().get(6).getNoBookings()} Bookings

            </th>

            <br>
            <form:label path="weekNum">You are editing a rota for Week </form:label>
                <form:input path="weekNum" readonly="true"></form:input>


            <form:label path="year">for the year</form:label>
                <form:input path="year" readonly="true"></form:input>


        <tr/>

        <c:forEach items="${userList}" var="user" varStatus="status">

            <tr>


                <td>

                    <c:out value="${user.getForename()}"/>
                </td>

                <c:if test="${user.getAvailability().getMon() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getMon() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getMon() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>


                        <%--    Monday Start--%>


                    <form:label path="daysList[0].shiftList[${status.index}].shiftStart">Start </form:label>
                    <c:choose>
                        <c:when test="${rota.getDaysList().get(0).getShiftList().get(status.index).isOnHoliday()}">
                            <form:input path="daysList[0].shiftList[${status.index}].shiftStart" readonly="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:select class="classic" path="daysList[0].shiftList[${status.index}].shiftStart">
                                <c:forEach var="shiftTime" items="${shiftTimesList}">
                                    <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>

                </td>

                <td hidden>
                    <form:select class="classic" path="daysList[0].shiftList[${status.index}].user.id">
                        <form:option value="${user.getId()} ">
                        </form:option>
                    </form:select>
                </td>
                <form:errors path="daysList[0].shiftList[${status.index}].shiftStart" cssClass="error"> </form:errors>

                <c:if test="${user.getAvailability().getMon() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getMon() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getMon() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>

                        <%--    Monday Finish--%>

                    <form:label path="daysList[0].shiftList[${status.index}].shiftEnd">Finish </form:label>
                    <c:choose>
                        <c:when test="${rota.getDaysList().get(0).getShiftList().get(status.index).isOnHoliday()}">
                            <form:input path="daysList[0].shiftList[${status.index}].shiftEnd" readonly="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:select class="classic" path="daysList[0].shiftList[${status.index}].shiftEnd">
                                <c:forEach var="shiftTime" items="${shiftTimesList}">
                                    <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>


                </td>
                <form:errors path="daysList[0].shiftList[${status.index}].shiftEnd" cssClass="error"> </form:errors>


                <c:if test="${user.getAvailability().getTue() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getTue() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getTue() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>

                        <%--    Tuesday Start--%>


                    <form:label path="daysList[1].shiftList[${status.index}].shiftStart">Start </form:label>
                    <c:choose>
                    <c:when test="${rota.getDaysList().get(1).getShiftList().get(status.index).isOnHoliday()}">
                        <form:input path="daysList[1].shiftList[${status.index}].shiftStart" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                    <form:select class="classic" path="daysList[1].shiftList[${status.index}].shiftStart">
                    <c:forEach var="shiftTime" items="${shiftTimesList}">
                        <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                    </c:forEach>
                    </form:select>
                    </c:otherwise>
                    </c:choose>


                <td hidden>
                    <form:select class="classic" path="daysList[1].shiftList[${status.index}].user.id">
                        <form:option value="${user.getId()} ">
                        </form:option>
                    </form:select>
                </td>
                <form:errors path="daysList[1].shiftList[${status.index}].shiftStart" cssClass="error"> </form:errors>


                </td>
                <c:if test="${user.getAvailability().getTue() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getTue() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getTue() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>


                        <%--    Tuesday finish--%>

                    <form:label path="daysList[1].shiftList[${status.index}].shiftEnd">Finish </form:label>
                    <c:choose>
                        <c:when test="${rota.getDaysList().get(1).getShiftList().get(status.index).isOnHoliday()}">
                            <form:input path="daysList[1].shiftList[${status.index}].shiftEnd" readonly="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:select class="classic" path="daysList[1].shiftList[${status.index}].shiftEnd">
                                <c:forEach var="shiftTime" items="${shiftTimesList}">
                                    <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>


                </td>
                <form:errors path="daysList[1].shiftList[${status.index}].shiftEnd" cssClass="error"> </form:errors>

                <c:if test="${user.getAvailability().getWed() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getWed() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getWed() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>

                        <%--    wed Start--%>

                    <form:label path="daysList[2].shiftList[${status.index}].shiftStart">Start </form:label>
                    <c:choose>
                    <c:when test="${rota.getDaysList().get(2).getShiftList().get(status.index).isOnHoliday()}">
                        <form:input path="daysList[2].shiftList[${status.index}].shiftStart" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                    <form:select class="classic" path="daysList[2].shiftList[${status.index}].shiftStart">
                    <c:forEach var="shiftTime" items="${shiftTimesList}">
                        <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                    </c:forEach>
                    </form:select>
                    </c:otherwise>
                    </c:choose>


                <td hidden>
                    <form:select class="classic" path="daysList[2].shiftList[${status.index}].user.id">
                        <form:option value="${user.getId()} ">
                        </form:option>
                    </form:select>


                </td>
                <form:errors path="daysList[2].shiftList[${status.index}].shiftStart" cssClass="error"> </form:errors>

                </td>
                <c:if test="${user.getAvailability().getWed() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getWed() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getWed() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>

                        <%--    wed finish--%>

                    <form:label path="daysList[2].shiftList[${status.index}].shiftEnd">Finish </form:label>
                    <c:choose>
                        <c:when test="${rota.getDaysList().get(2).getShiftList().get(status.index).isOnHoliday()}">
                            <form:input path="daysList[2].shiftList[${status.index}].shiftEnd" readonly="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:select class="classic" path="daysList[2].shiftList[${status.index}].shiftEnd">
                                <c:forEach var="shiftTime" items="${shiftTimesList}">
                                    <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>


                </td>

                <form:errors path="daysList[2].shiftList[${status.index}].shiftEnd" cssClass="error"> </form:errors>

                <c:if test="${user.getAvailability().getThu() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getThu() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getThu() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>

                        <%--    thu Start--%>

                    <form:label path="daysList[3].shiftList[${status.index}].shiftStart">Start </form:label>
                    <c:choose>
                    <c:when test="${rota.getDaysList().get(3).getShiftList().get(status.index).isOnHoliday()}">
                        <form:input path="daysList[3].shiftList[${status.index}].shiftStart" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                    <form:select class="classic" path="daysList[3].shiftList[${status.index}].shiftStart">
                    <c:forEach var="shiftTime" items="${shiftTimesList}">
                        <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                    </c:forEach>
                    </form:select>
                    </c:otherwise>
                    </c:choose>
                <td hidden>
                    <form:select class="classic" path="daysList[3].shiftList[${status.index}].user.id">
                        <form:option value="${user.getId()} ">
                        </form:option>
                    </form:select>


                </td>

                <form:errors path="daysList[3].shiftList[${status.index}].shiftStart" cssClass="error"> </form:errors>


                </td>
                <c:if test="${user.getAvailability().getThu() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getThu() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getThu() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>

                        <%--    thu finish--%>

                    <form:label path="daysList[3].shiftList[${status.index}].shiftEnd">Finish </form:label>
                    <c:choose>
                        <c:when test="${rota.getDaysList().get(3).getShiftList().get(status.index).isOnHoliday()}">
                            <form:input path="daysList[3].shiftList[${status.index}].shiftEnd" readonly="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:select class="classic" path="daysList[3].shiftList[${status.index}].shiftEnd">
                                <c:forEach var="shiftTime" items="${shiftTimesList}">
                                    <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>


                </td>
                <form:errors path="daysList[3].shiftList[${status.index}].shiftEnd" cssClass="error"> </form:errors>

                <c:if test="${user.getAvailability().getFri() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getFri() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getFri() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>
                        <%--    fri Start--%>

                    <form:label path="daysList[4].shiftList[${status.index}].shiftStart">Start </form:label>
                    <c:choose>
                    <c:when test="${rota.getDaysList().get(4).getShiftList().get(status.index).isOnHoliday()}">
                        <form:input path="daysList[4].shiftList[${status.index}].shiftStart" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                    <form:select class="classic" path="daysList[4].shiftList[${status.index}].shiftStart">
                    <c:forEach var="shiftTime" items="${shiftTimesList}">
                        <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                    </c:forEach>
                    </form:select>
                    </c:otherwise>
                    </c:choose>
                <td hidden>
                    <form:select class="classic" path="daysList[4].shiftList[${status.index}].user.id">
                        <form:option value="${user.getId()} ">
                        </form:option>
                    </form:select>

                </td>
                </td>
                <form:errors path="daysList[4].shiftList[${status.index}].shiftStart" cssClass="error"> </form:errors>

                <c:if test="${user.getAvailability().getFri() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getFri() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getFri() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>

                        <%--    fri finish--%>

                    <form:label path="daysList[4].shiftList[${status.index}].shiftEnd">Finish </form:label>
                    <c:choose>
                        <c:when test="${rota.getDaysList().get(4).getShiftList().get(status.index).isOnHoliday()}">
                            <form:input path="daysList[4].shiftList[${status.index}].shiftEnd" readonly="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:select class="classic" path="daysList[4].shiftList[${status.index}].shiftEnd">
                                <c:forEach var="shiftTime" items="${shiftTimesList}">
                                    <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>

                </td>
                <form:errors path="daysList[4].shiftList[${status.index}].shiftEnd" cssClass="error"> </form:errors>

                <c:if test="${user.getAvailability().getSat() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getSat() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getSat() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>
                        <%--    sat Start--%>

                    <form:label path="daysList[5].shiftList[${status.index}].shiftStart">Start </form:label>
                    <c:choose>
                    <c:when test="${rota.getDaysList().get(5).getShiftList().get(status.index).isOnHoliday()}">
                        <form:input path="daysList[5].shiftList[${status.index}].shiftStart" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                    <form:select class="classic" path="daysList[5].shiftList[${status.index}].shiftStart">
                    <c:forEach var="shiftTime" items="${shiftTimesList}">
                        <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                    </c:forEach>
                    </form:select>
                    </c:otherwise>
                    </c:choose>

                <td hidden>
                    <form:select class="classic" path="daysList[5].shiftList[${status.index}].user.id">
                        <form:option value="${user.getId()} ">
                        </form:option>
                    </form:select>
                </td>
                <form:errors path="daysList[5].shiftList[${status.index}].shiftStart" cssClass="error"> </form:errors>

                </td>
                <c:if test="${user.getAvailability().getSat() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getSat() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getSat() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>
                        <%--    sat finish--%>

                    <form:label path="daysList[5].shiftList[${status.index}].shiftEnd">Finish </form:label>
                    <c:choose>
                        <c:when test="${rota.getDaysList().get(5).getShiftList().get(status.index).isOnHoliday()}">
                            <form:input path="daysList[5].shiftList[${status.index}].shiftEnd" readonly="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:select class="classic" path="daysList[5].shiftList[${status.index}].shiftEnd">
                                <c:forEach var="shiftTime" items="${shiftTimesList}">
                                    <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>

                </td>
                <form:errors path="daysList[5].shiftList[${status.index}].shiftEnd" cssClass="error"> </form:errors>

                <c:if test="${user.getAvailability().getSun() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getSun() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getSun() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>
                        <%--    sun Start--%>


                    <form:label path="daysList[6].shiftList[${status.index}].shiftStart">Start </form:label>
                    <c:choose>
                    <c:when test="${rota.getDaysList().get(6).getShiftList().get(status.index).isOnHoliday()}">
                        <form:input path="daysList[6].shiftList[${status.index}].shiftStart" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                    <form:select class="classic" path="daysList[6].shiftList[${status.index}].shiftStart">
                    <c:forEach var="shiftTime" items="${shiftTimesList}">
                        <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                    </c:forEach>
                    </form:select>
                    </c:otherwise>
                    </c:choose>


                <td hidden>
                    <form:select class="classic" path="daysList[6].shiftList[${status.index}].user.id">
                        <form:option value="${user.getId()} ">
                        </form:option>
                    </form:select>
                </td>
                </td>

                <form:errors path="daysList[6].shiftList[${status.index}].shiftStart" cssClass="error"> </form:errors>

                <c:if test="${user.getAvailability().getSun() == 1}">
                <td bgcolor="#adff2f">
                    </c:if>
                    <c:if test="${user.getAvailability().getSun() == 0}">
                <td bgcolor="#ffa500">
                    </c:if>
                    <c:if test="${user.getAvailability().getSun() == -1}">
                <td bgcolor="#F74C05">
                    </c:if>
                        <%--    sun finish--%>

                    <form:label path="daysList[6].shiftList[${status.index}].shiftEnd">Finish </form:label>
                    <c:choose>
                        <c:when test="${rota.getDaysList().get(6).getShiftList().get(status.index).isOnHoliday()}">
                            <form:input path="daysList[6].shiftList[${status.index}].shiftEnd" readonly="true"/>
                        </c:when>
                        <c:otherwise>
                            <form:select class="classic" path="daysList[6].shiftList[${status.index}].shiftEnd">
                                <c:forEach var="shiftTime" items="${shiftTimesList}">
                                    <form:option value="${shiftTime}" label="${shiftTime}">${shiftTime}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>

                </td>
                <form:errors path="daysList[6].shiftList[${status.index}].shiftEnd" cssClass="error"> </form:errors>

            </tr>

        </c:forEach>


    </table>
    <input type="submit" value="Submit"/> </form:form>

</body>
</html>
