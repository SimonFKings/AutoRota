<%--
  Created by IntelliJ IDEA.
  User: simonfkings
  Date: 07/11/2019
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Register </title>
    <link href="/resources/css/autoRota.css" rel="stylesheet" type="text/css">


    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script
            src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

    <script>
        $(function () {

            $("#pickdate").datepicker({
                firstDay: 1,
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                yearRange: "1930:2010",
                defaultDate: "-1930y-m-d",


            })


        });

    </script>
    <style>

        .ui-datepicker {
            width: 216px;

        }

        div.ui-datepicker {
            font-size: 63.5%;
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

    <h1>Register Form</h1>


    <c:if test="${error == false}">
        <b>The password for ${firstname} is ${password}</b>
    </c:if>
    <%--@elvariable id="user" type="autorota"--%>
    <form:form name="register" modelAttribute="user" action="/register/newUser" method="POST">

        <table>
            <tr>
                <td><form:label path="forename">First Name</form:label></td>
                <td><form:input path="forename"/></td>
                <td><form:errors path="forename" cssClass="error"></form:errors></td>

            </tr>

            <tr>
                <td><form:label path="lastname">Surname</form:label></td>
                <td><form:input path="lastname"/></td>
                <td><form:errors path="lastname" cssClass="error"></form:errors></td>
            <tr>
                <td><form:label path="jobTitle">Job Title</form:label></td>
                <td><form:input path="jobTitle"/></td>
                <td><form:errors path="jobTitle" cssClass="error"></form:errors></td>

            </tr>
            <tr>
                <td><form:label path="dob">Date of Birth</form:label></td>
                <td><form:input path="dob" id="pickdate" readonly="true" cssClass="form-control"/></td>
                <td><form:errors path="dob" cssClass="error"></form:errors></td>

            </tr>
            <tr>
                <td><form:label path="phoneNum">Phone Number</form:label></td>
                <td><form:input path="phoneNum"/></td>
                <td><form:errors path="phoneNum" cssClass="error"></form:errors></td>

            </tr>
            <tr>
                <td><form:label path="email">Email Address</form:label></td>
                <td><form:input path="email"/></td>
                <td><form:errors path="email" cssClass="error"></form:errors></td>

            </tr>
            <tr>
                <td><form:label path="salary">Salary per hour</form:label> £</td>
                <td><form:input path="salary"/></td>
                <td><form:errors path="salary" cssClass="error"></form:errors></td>

            </tr>
            <tr>
                <td><label path="userType">User Type </label></td>
                <td><select class="classic" id="userType" name="userType">
                    <option disabled selected value> -- select an option --</option>

                    <c:forEach var="userType" items="${roleList}">
                        <option value="${userType}" label="${userType}">${userType}</option>
                    </c:forEach>
                </select>
                <td><form:errors path="userType" cssClass="error"></form:errors></td>
                </td>
            </tr>


        </table>
        <input type="submit" value="Submit"/> </form:form>

</div>
</body>
</html>
