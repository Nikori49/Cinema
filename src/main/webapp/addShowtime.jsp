<%@ page import="java.util.List" %>
<%@ page import="com.epam.dao.entity.Film" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: yohoh
  Date: 03.08.2022
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"  %>
<%--@elvariable id="language" type="String"--%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="General"/>

<%--@elvariable id="filmService" type="com.epam.service.FilmService"--%>
<c:set value="${filmService.allFilms}" var="filmList"/>

<html lang="${language}">
<head>
    <title><fmt:message key="label.addShowtime"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        $(function(){
            let dtToday = new Date();

            let month = dtToday.getMonth() + 1;
            let day = dtToday.getDate();
            let year = dtToday.getFullYear();
            if(month < 10)
                month = '0' + month.toString();
            if(day < 10)
                day = '0' + day.toString();

            let minDate = year + '-' + month + '-' + day;

            $('#txtDate').attr('min', minDate);
        });
    </script>
    <script>
        let request;
        function sendDateInfo() {
            const v = document.showtimeForm.date.value;
            const url = "${pageContext.request.contextPath }/GetDateShowtime?date=" + v +"&type=add";

            if(window.XMLHttpRequest){
                request=new XMLHttpRequest();
            }
            else if(window.ActiveXObject){
                request=new ActiveXObject("Microsoft.XMLHTTP");
            }

            try
            {
                request.onreadystatechange=getDateShowtimeInfo;
                request.open("GET",url,true);
                request.send();
            }
            catch(e)
            {
                alert("Unable to connect to server");
            }
    }

        function sendFilmInfo()
        {
            const v = document.showtimeForm.film.value;
            const url = "${pageContext.request.contextPath }/GetFilm?film=" + v;

            if(window.XMLHttpRequest){
                request=new XMLHttpRequest();
            }
            else if(window.ActiveXObject){
                request=new ActiveXObject("Microsoft.XMLHTTP");
            }

            try
            {
                request.onreadystatechange=getFilmInfo;
                request.open("GET",url,true);
                request.send();
            }
            catch(e)
            {
                alert("Unable to connect to server");
            }
        }

        function getDateShowtimeInfo() {
            if(request.readyState===4){
                const val = request.responseText;
                document.getElementById('dateShowtime').innerHTML=val;
            }
        }
        function getFilmInfo(){
            if(request.readyState===4){
                const val = request.responseText;
                document.getElementById('posterImg').innerHTML=val;
            }
        }
        function validateForm(){
            let startTime=document.showtimeForm.startTime.value;
            let date=document.showtimeForm.date.value;
            let film=document.showtimeForm.film.value;

            if (film==null || film===""){
                alert("Film can't be blank");
                return false;
            }else if (date==null || date===""){
                alert("Date can't be blank");
                return false;
            }else if (startTime==null || startTime===""){
                alert("Start time can't be blank");
                return false;
            }
        }
        function resetError(){
            const url = "${pageContext.request.contextPath }/ResetError";
            if(window.XMLHttpRequest){
                request=new XMLHttpRequest();
            }
            else if(window.ActiveXObject){
                request=new ActiveXObject("Microsoft.XMLHTTP");
            }

            try
            {
                request.open("GET",url,true);
                request.send();
            }
            catch(e)
            {
                alert("Unable to connect to server");
            }


        }
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
            <%--@elvariable id="loggedUser" type="com.epam.dao.entity.User"--%>
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
<div class="container" >
    <h1><fmt:message key="label.addShowtime"/></h1>
    <form  role="form" name="showtimeForm" action="controller" method="post">
        <input type="hidden" name="command" value="addShowtime">
        <div class="form-group">
            <label for="inputFilm"><fmt:message key="label.selectFilm"/></label>
            <select class="form-control" oninput="sendFilmInfo()" id="inputFilm" name="film">

                <c:forEach items="${filmList}" var="film">
                    <option value="${film.id}" >${film.name}</option>
                </c:forEach>
            </select>
            <span id="posterImg"></span>


        </div>

        <div class="form-group">
            <label for="txtDate"><fmt:message key="label.date"/></label>
            <input class="form-control"  id="txtDate" oninput="sendDateInfo()" type="date"  name="date">
            <span id="dateShowtime"></span>
        </div>
        <div class="form-group">
            <label for="inputStartTime"><fmt:message key="label.startTime"/></label>
            <input class="form-control" type="time" id="inputStartTime" name="startTime">
        </div>

        <input class="btn btn-success" onsubmit="return validateForm()" type="submit" value="<fmt:message key="label.addShowtime"/>">
        <button class="btn btn-secondary" formaction="manager.jsp" ><fmt:message key="label.cancel"/>
        </button>
        <%--@elvariable id="showtimeError" type="int"--%>
        <c:if test="${showtimeError==1}">
            <div  class="alert alert-danger  alert-dismissible">
                <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidStartTime"/>
            </div>
        </c:if>
        <c:if test="${showtimeError==2}">
            <div  class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidDate"/>
            </div>
        </c:if>
        <c:if test="${showtimeError==3}">
            <div  class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidFilm"/>
            </div>
        </c:if>





    </form>


</div>
</body>
</html>