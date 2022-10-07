<%--@elvariable id="userService" type="com.epam.service.UserService"--%>
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
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="language" type="String"--%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="General"/>

<%--@elvariable id="showtimeService" type="com.epam.service.ShowtimeService"--%>
<c:set var="showtimeList" value="${showtimeService.plannedShowtime}"/>
<%--@elvariable id="ticketService" type="com.epam.service.TicketService"--%>
<c:set var="userTickets" value="${ticketService.getUserTickets(sessionScope.get('loggedUser').id)}"/>
<%--@elvariable id="filmService" type="com.epam.service.FilmService"--%>
<c:set var="filmList" value="${filmService.allFilms}"/>

<html lang="${language}">
<head>
    <%--@elvariable id="loggedUser" type="com.epam.dao.entity.User"--%>
    <title><fmt:message key="label.welcome"/>${loggedUser.name}</title>
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
            <c:if test="${loggedUser!=null}">
                <li>
                    <a href="${pageContext.request.contextPath }/LogOut">
                        <span class="glyphicon glyphicon glyphicon-log-out"></span><fmt:message key="label.logOut"/>
                    </a>
                </li>
            </c:if>
            <li class="active"><a href="client.jsp"><span class="glyphicon glyphicon-user"></span><fmt:message
                    key="label.profile"/></a></li>
        </ul>
    </div>
</nav>
<div class="container">
    <fmt:message key="label.yourBalance"/> ${userService.getUserBalance(sessionScope.get('loggedUser').id)}
    <fmt:message key="label.hrn"/>
    <form role="form" action="controller" method="post">
        <input name="command" value="addBalance" hidden>
        <select name="sum">
            <option class="active" value="75">75</option>
            <option value="150">150</option>
            <option value="300">300</option>
            <option value="1000">1000</option>
            <option value="3000">3000</option>
            <option value="10000">10000</option>
        </select>
        <input class="btn btn-success btn-sm" type="submit" value="<fmt:message key="label.addBalance"/>">
    </form>
</div>
<div class="container">
    <h2><fmt:message key="label.yourTickets"/></h2>
    <%--@elvariable id="userTickets" type="java.util.List<com.epam.dao.entity.Ticket>"--%>

    <c:if test="${empty userTickets}">
        <fmt:message key="label.noTickets"/>
    </c:if>
    <c:if test="${!empty userTickets}">
        <c:forEach items="${userTickets}" var="ticket">
            <div class="container">
                <div class="panel <c:forEach items="${showtimeList}" var="showtime">
                                <c:if test="${showtime.id==ticket.showTimeId}">
                                    <c:if test="${showtime.status=='finished' && ticket.status!='refunded'}">
                                     panel-success </c:if>
                                     <c:if test="${showtime.status=='canceled'}">
                                     panel-danger </c:if>
                                     <c:if test="${showtime.status=='planned' && ticket.status!='refunded'}">
                                     panel-info </c:if>
                                     <c:if test="${(showtime.status=='planned' || showtime.status=='finished')  && ticket.status=='refunded'}">
                                     panel-warning </c:if>
                                </c:if>
                            </c:forEach> ">
                    <div class="panel-heading">
                        <h3 class="panel-title">

                            <c:forEach items="${showtimeList}" var="showtime">
                                <c:if test="${showtime.id==ticket.showTimeId}">
                                    <c:forEach items="${filmList}" var="film">
                                        <c:if test="${film.id==showtime.filmId}">
                                            ${film.name}
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </h3>
                    </div>
                    <div class="panel-body container">

                        <c:forEach items="${showtimeList}" var="showtime">
                            <c:if test="${showtime.id==ticket.showTimeId}">
                                <fmt:message key="label.date"/>:${showtime.date}  <fmt:message
                                    key="label.startEndTime"/>:${showtime.startTime}-${showtime.endTime}  <fmt:message
                                    key="label.seat"/>:${ticket.seat}
                            </c:if>
                            <c:if test="${showtime.id==ticket.showTimeId && ticket.status=='purchased' && showtime.status=='planned'}">
                                <form style="display: inline;position: relative; left: 55%" class="form-inline" role="form" action="controller" method="post">
                                    <input type="hidden" name="command" value="refund">
                                    <input type="hidden" name="id" value="${ticket.id}">
                                    <input style="display: inline" class="btn-danger btn btn-sm" type="submit"
                                           value="<fmt:message key="label.refund"/>">
                                </form>
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
