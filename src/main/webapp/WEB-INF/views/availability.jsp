<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 29/11/2019
  Time: 00:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <title>Availability</title>
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
    <h1>What days can you work?</h1>

    <c:if test="${error == false}">
        <b>You have successfully added your availability</b>
    </c:if>

    <%--@elvariable id="availability" type=""--%>
    <form:form name="availability" modelAttribute="availability" action="/availability/add" method="POST">
        <table>


            <tr>
                <td>
                    <form:label path="mon">Monday </form:label>
                </td>
                <td><form:select class="classic" path="mon">

                    <c:if test="${monday == 1}">
                        <option value="1" selected>I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>

                    <c:if test="${monday == 0}">
                        <option value="1">I want to work this day</option>
                        <option value="0" selected>I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>

                    <c:if test="${monday == -1}">
                        <option value="1">I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1" selected> I don't want to work this day</option>
                    </c:if>


                </form:select>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="tue">Tuesday </form:label>
                </td>
                <td><form:select class="classic" path="tue">
                    <c:if test="${tuesday == 1}">
                        <option value="1" selected>I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>
                    <c:if test="${tuesday == 0}">
                        <option value="1">I want to work this day</option>
                        <option value="0" selected>I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>

                    <c:if test="${tuesday == -1}">
                        <option value="1">I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1" selected> I don't want to work this day</option>
                    </c:if>

                </form:select>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="wed">Wednesday </form:label>
                </td>
                <td><form:select class="classic" path="wed">
                    <c:if test="${wednesday == 1}">
                        <option value="1" selected>I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>
                    <c:if test="${wednesday == 0}">
                        <option value="1">I want to work this day</option>
                        <option value="0" selected>I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>
                    <c:if test="${wednesday == -1}">
                        <option value="1">I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1" selected> I don't want to work this day</option>
                    </c:if>

                </form:select>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="thu">Thursday </form:label>
                </td>
                <td><form:select class="classic" path="thu">
                    <c:if test="${thursday == 1}">
                        <option value="1" selected>I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>
                    <c:if test="${thursday == 0}">
                        <option value="1">I want to work this day</option>
                        <option value="0" selected>I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>
                    <c:if test="${thursday == -1}">
                        <option value="1">I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1" selected> I don't want to work this day</option>
                    </c:if>

                </form:select>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="fri">Friday </form:label>
                </td>
                <td><form:select class="classic" path="fri">
                    <c:if test="${friday == 1}">
                        <option value="1" selected>I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>

                    <c:if test="${friday == 0}">
                        <option value="1">I want to work this day</option>
                        <option value="0" selected>I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>

                    <c:if test="${friday == -1}">
                        <option value="1">I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1" selected> I don't want to work this day</option>
                    </c:if>

                </form:select>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="sat">Saturday </form:label>
                </td>
                <td><form:select class="classic" path="sat">

                    <c:if test="${saturday == 1}">
                        <option value="1" selected>I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>

                    <c:if test="${saturday == 0}">
                        <option value="1">I want to work this day</option>
                        <option value="0" selected>I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>

                    <c:if test="${saturday == -1}">
                        <option value="1">I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1" selected> I don't want to work this day</option>
                    </c:if>
                </form:select>
                </td>
            </tr>
            <tr>
                <td>
                    <form:label path="sun">Sunday </form:label>
                </td>
                <td><form:select class="classic" path="sun">
                    <c:if test="${sunday == 1}">
                        <option value="1" selected>I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>
                    </c:if>

                    <c:if test="${sunday == 0}">
                        <option value="1">I want to work this day</option>
                        <option value="0" selected>I don't mind working this day</option>
                        <option value="-1"> I don't want to work this day</option>

                    </c:if>

                    <c:if test="${sunday == -1}">
                        <option value="1">I want to work this day</option>
                        <option value="0">I don't mind working this day</option>
                        <option value="-1" selected> I don't want to work this day</option>
                    </c:if>
                </form:select>
                </td>
            </tr>
        </table>


        <input type="submit" value="Submit"/>


    </form:form>
</div>

</body>
</html>
