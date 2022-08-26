<%--@elvariable id="loggedUser" type="User"--%>
<%--
  Created by IntelliJ IDEA.
  User: yohoh
  Date: 03.08.2022
  Time: 20:05
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>${loggedUser.name} <fmt:message key="label.workplace"/></title>

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
            <c:if test="${loggedUser==null}">
                <li><a href="register.jsp"><span class="glyphicon glyphicon-user"></span><fmt:message key="label.signUp"/></a></li>
                <li><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span><fmt:message key="label.logIn"/></a></li>
            </c:if>
            <c:if test="${loggedUser!=null}">
                <li>
                    <a href="${pageContext.request.contextPath }/LogOut" >
                        <span class="glyphicon glyphicon glyphicon-log-out"></span><fmt:message key="label.logOut"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${loggedUser.role=='client'}">
                <li><a href="client.jsp"><span class="glyphicon glyphicon-user"></span><fmt:message key="label.profile"/></a></li>
            </c:if>
            <c:if test="${loggedUser.role=='manager'}">
                <li><a href="manager.jsp"><span class=" glyphicon glyphicon-briefcase"></span><fmt:message key="label.managerWorkplace"/></a></li>
            </c:if>
        </ul>
    </div>
</nav>
<div style="top:15%;left: 50%;" class="container">
    <h4><fmt:message key="label.welcome"/> ${loggedUser.name}</h4>
    <form role="presentation">
        <button class="btn btn-info btn-sm btn-block" formaction="addShowtime.jsp"><fmt:message key="label.addShowtime"/></button>
        <br>
        <button class="btn btn-info btn-sm btn-block" formaction="addFilm.jsp"><fmt:message key="label.addFilm"/></button>
        <br>
        <button class="btn btn-info btn-sm btn-block" formaction="cancelShowtime.jsp"><fmt:message key="label.cancelShowtime"/></button>
        <br>
        <button class="btn btn-info btn-sm btn-block" formaction="statistics.jsp"><fmt:message key="label.statistics"/></button>
    </form>

</div>

</body>
</html>
