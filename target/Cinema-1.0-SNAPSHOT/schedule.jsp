<%--
  Created by IntelliJ IDEA.
  User: yohoh
  Date: 11.08.2022
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"  %>
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
    <script>
        let request;
        function changePage(sus) {

            const url = "${pageContext.request.contextPath }/ChangePage?value=" +sus;

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

        }


    </script>
    <title><fmt:message key="label.schedule"/></title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp"><fmt:message key="label.mainTitle"/></a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="films.jsp"><fmt:message key="label.films"/></a></li>
            <li class="active"><a href="schedule.jsp"><fmt:message key="label.schedule"/></a></li>
            <c:if test="${loggedUser.role=='manager'}">
                <li><a href="manager.jsp"><fmt:message key="label.managerWorkplace"/></a></li>
            </c:if>

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
<div class="container">
    <%--@elvariable id="thisWeekShowtimeList" type="java.util.List"--%>
    <c:forEach items="${thisWeekShowtimeList}" begin="${schedulePage}" end="${schedulePage}" var="showtimeList"   varStatus="status">
        <label for="table">
            <c:forEach items="${weekDays}" var="weekDay" varStatus="dayStatus">
                <c:if test="${status.index==dayStatus.index}">
                    <fmt:message key="label.day${weekDay}"/>
                </c:if>
            </c:forEach>
            <c:forEach items="${weekDates}" var="weekDate" varStatus="dateStatus">
                <c:if test="${status.index==dateStatus.index}">
                     ${weekDate}
                </c:if>
            </c:forEach>
        </label>
        <table class="table table-condensed table-striped" id="table">
            <tr>
                <th><fmt:message key="label.films"/></th>
                <th><fmt:message key="label.startTime"/></th>
                <th><fmt:message key="label.endTime"/></th>
            </tr>
            <c:forEach items="${showtimeList}" var="showtime">
                <tr>
                    <td><a href="${pageContext.request.contextPath }/ShowtimePage?id=${showtime.id}">
                        <c:forEach items="${filmList}" var="film">
                        <c:if test="${film.id==showtime.filmId}">${film.name}</c:if>
                        </c:forEach></a></td>
                    <td>
                        ${showtime.startTime}
                    </td>
                    <td>
                        ${showtime.endTime}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:forEach>
        <ul class="pagination">
            <c:forEach items="${weekDays}" var="day" varStatus="status">
                <li <c:if test="${schedulePage==status.index}">class="active" </c:if> ><a <c:if test="${schedulePage!=status.index}">href="schedule.jsp" onclick="changePage(${status.index})" </c:if> ><fmt:message key="label.day${day}"/></a></li>
            </c:forEach>

        </ul>

</div>
</body>
</html>
