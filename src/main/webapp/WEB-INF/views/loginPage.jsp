<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="/resources/css/autoRota.css" rel="stylesheet" type="text/css">

    <title>Login page</title>
    <style>

        body {
            background-color: #6312c7;
        }

        .format {

            width: 360px;
            position: relative;
            z-index: 1;
            background: #FFFFFF;
            max-width: 360px;
            margin: 0 auto 100px;
            padding: 45px;
            font-family: Cairo, sans-serif;


        }


        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #6312c7;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }


        button:hover {
            opacity: 0.8;
        }


    </style>
</head>

<body>

<div class="format">


    <h1>Login</h1>


    <p>
        <c:if test="${error == true}">
            <b class="error">Invalid email or password.</b>
        </c:if>
        <c:if test="${logout == true}">
            <b class="error">You have been logged out.</b>
        </c:if>

    </p>

    <c:url value="/login" var="loginUrl"/>
    <form name="${loginUrl}" action="${loginUrl}" method="POST" modelAttribute="user">

        <label for="email">Email Address</label>

        <input type="text" id="email" name="email"/>
        <label for="password">Password</label>

        <input type="password" id="password" name="password"/>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <button type="submit" class="btn">Login</button>
    </form>
</div>

</body>
</html>