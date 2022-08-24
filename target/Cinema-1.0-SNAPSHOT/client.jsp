<%--
  Created by IntelliJ IDEA.
  User: yohoh
  Date: 11.08.2022
  Time: 19:20
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
    <%--@elvariable id="loggedUser" type="DB.entity.User"--%>
    <title><fmt:message key="label.welcome"/>${loggedUser.name}</title>
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
            <li ><a href="films.jsp"><fmt:message key="label.films"/></a></li>
            <li><a href="schedule.jsp"><fmt:message key="label.schedule"/></a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <tg:changeLanguage/>
            <c:if test="${loggedUser!=null}">
                <li>
                    <a href="${pageContext.request.contextPath }/LogOut" >
                        <span class="glyphicon glyphicon glyphicon-log-out"></span><fmt:message key="label.logOut"/>
                    </a>
                </li>
            </c:if>
            <li class="active"><a href="client.jsp"><span class="glyphicon glyphicon-user"></span><fmt:message key="label.profile"/></a></li>
        </ul>
    </div>
</nav>
<div class="container">
    <h2><fmt:message key="label.yourTickets"/></h2>
    <%--@elvariable id="userTickets" type="java.util.List<DB.entity.Ticket>"--%>
    <c:if test="${userTickets==null}">
        <fmt:message key="label.noTickets"/>
    </c:if>
    <c:if test="${userTickets!=null}">
        <c:forEach items="${userTickets}" var="ticket">
            <div class="container">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title" >
                                <%--@elvariable id="filmList" type="java.util.List<DB.entity.Film>"--%>

                        <%--@elvariable id="showtimeList" type="java.util.List<Showtime>"--%>
                        <c:forEach items="${showtimeList}" var="showtime">
                            <c:if test="${showtime.id==ticket.showTimeId}">
                                <c:forEach items="${filmList}" var="film">
                                    <c:if test="${film.id==showtime.filmId}">
                                        ${film.name}
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </c:forEach></h3>
                    </div>
                    <div class="panel-body">
                        <c:forEach items="${showtimeList}" var="showtime">
                            <c:if test="${showtime.id==ticket.showTimeId}">
                               <fmt:message key="label.date"/>:${showtime.date}  <fmt:message key="label.startEndTime"/>:${showtime.startTime}-${showtime.endTime}  <fmt:message key="label.seat"/>:${ticket.seat}
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>

        </c:forEach>

    </c:if>
</div>
</body>
</html>
