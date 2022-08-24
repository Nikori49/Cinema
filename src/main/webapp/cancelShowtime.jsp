<%--
  Created by IntelliJ IDEA.
  User: yohoh
  Date: 09.08.2022
  Time: 13:32
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
    <title><fmt:message key="label.cancelShowtime"/></title>
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
        let request;
        function sendDateInfo() {
            const v = document.showtimeDateForm.showtimeDate.value;
            const url = "${pageContext.request.contextPath }/GetDateShowtime?date=" + v+"&type=cancel";

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
        function getDateShowtimeInfo() {
            if(request.readyState===4){
                document.getElementById('dateShowtime').innerHTML=request.responseText;
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
            <c:if test="${loggedUser.role=='manager'}">
                <li class="active"><a href="manager.jsp"><fmt:message key="label.managerWorkplace"/></a></li>
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
<div class="container" >
    <h1><fmt:message key="label.cancelShowtime"/></h1>
    <label for="txtDate"><fmt:message key="label.cancelShowtime"/></label>
    <form   role="form" name="showtimeDateForm">
        <div class="form-group">
            <input type="date" oninput="sendDateInfo()" name="showtimeDate" id="txtDate" >
        </div>
    </form>

    <span id="dateShowtime"></span>
    <form>
        <button class="btn btn-secondary" formaction="manager.jsp" ><fmt:message key="label.cancel"/></button>
    </form>


</div>


</body>
</html>
