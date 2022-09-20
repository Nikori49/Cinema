<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="language" type="String"--%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="General"/>

<html lang="${language}">
<head>

    <link rel="stylesheet" href="test.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        function changeLanguage(lang) {
            let request
            const url = "${pageContext.request.contextPath }/ChangeLanguage?lang=" + lang;

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }

            try {
                request.open("GET", url, false);
                request.send();
            } catch (e) {
                alert("Unable to connect to server");
            }
            document.location.reload();
        }


    </script>

    <title><fmt:message key="label.error"/></title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand active" href="index.jsp"><fmt:message key="label.mainTitle"/></a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="films.jsp"><fmt:message key="label.films"/></a></li>

            <li><a href="schedule.jsp"><fmt:message key="label.schedule"/></a></li>


        </ul>
        <ul class="nav navbar-nav navbar-right">
            <tg:changeLanguage/>
            <c:if test="${loggedUser==null}">
                <li>
                    <a href="register.jsp"><span class="glyphicon glyphicon-user"></span>
                        <fmt:message key="label.signUp"/>
                    </a>
                </li>
                <li>
                    <a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span>
                        <fmt:message key="label.logIn"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${loggedUser!=null}">
                <li>
                    <a href="${pageContext.request.contextPath }/LogOut">
                        <span class="glyphicon glyphicon glyphicon-log-out"></span>
                        <fmt:message key="label.logOut"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${loggedUser.role=='client'}">
                <li>
                    <a href="client.jsp"><span class="glyphicon glyphicon-user"></span>
                        <fmt:message key="label.profile"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${loggedUser.role=='manager'}">
                <li>
                    <a href="manager.jsp"><span class=" glyphicon glyphicon-briefcase"></span>
                        <fmt:message key="label.managerWorkplace"/>
                    </a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
<div class="container">
    <h3><fmt:message key="label.error"/></h3>
    <fmt:message key="label.errorMessage"/>
</div>



</body>
</html>