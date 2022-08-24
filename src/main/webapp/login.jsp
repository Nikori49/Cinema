<%--
  Created by IntelliJ IDEA.
  User: yohoh
  Date: 11.08.2022
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"  %>
<%--@elvariable id="language" type="String"--%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="General"/>

<html lang="${language}">
<head>
    <title><fmt:message key="label.logIn"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        function changeLanguage(lang) {
            let request
            const url = "${pageContext.request.contextPath }/ChangeLanguage?lang=" + lang;

            if(window.XMLHttpRequest){
                request=new XMLHttpRequest();
            }
            else if(window.ActiveXObject){
                request=new ActiveXObject("Microsoft.XMLHTTP");
            }

            try
            {
                request.open("GET",url,false);
                request.send();
            }
            catch(e)
            {
                alert("Unable to connect to server");
            }
            document.location.reload();
        }
    </script>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp"><fmt:message key="label.mainTitle"/></a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="films.jsp"><fmt:message key="label.films"/></a></li>
            <li><a href="schedule.jsp"><fmt:message key="label.schedule"/></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <tg:changeLanguage/>
            <%--@elvariable id="loggedUser" type="User"--%>
            <c:if test="${loggedUser==null}">
                <li><a href="register.jsp"><span class="glyphicon glyphicon-user"></span><fmt:message key="label.signUp"/></a></li>
                <li class="active"><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span><fmt:message key="label.logIn"/></a></li>
            </c:if>

        </ul>
    </div>
</nav>

<div class="container">
    <h3 class="text-center"><fmt:message key="label.logIn"/></h3>
    <div class="form-group">
        <form role="form" action="controller" method="post">
            <input type="hidden" name="command" value="login">
            <div class="form-group">
                <label for="login"><fmt:message key="label.enterLogin"/></label>
                <input class="form-control" id="login" name="login">
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="label.enterPassword"/></label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="label.logIn"/></button>

            <div>
                <fmt:message key="label.dontHaveAnAccount"/><a href="register.jsp"><fmt:message key="label.register"/></a>
            </div>
        </form>
    </div>
    <%--@elvariable id="loginError" type="int"--%>
    <c:if test="${loginError==1}">
        <div class="alert alert-danger alert- alert-dismissible">
            <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
            <strong><fmt:message key="label.error"/></strong><fmt:message key="label.failedLogin"/>
        </div>
    </c:if>
    <c:if test="${loginError==2}">
        <div class="alert alert-danger alert- alert-dismissible">
            <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
            <strong><fmt:message key="label.error"/></strong><fmt:message key="label.wrongPassword"/>
        </div>
    </c:if>

</div>
</body>
</html>
