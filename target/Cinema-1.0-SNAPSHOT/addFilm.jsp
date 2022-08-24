<%--
  Created by IntelliJ IDEA.
  User: yohoh
  Date: 03.08.2022
  Time: 21:19
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
    <title><fmt:message key="label.addFilm"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        function validateForm(){
            let name=document.filmForm.name.value;
            let description=document.filmForm.description.value;
            let genre=document.filmForm.genre.value;
            let runtime=document.filmForm.runtime.value;
            let posterImg=document.filmForm.posterImg.value;

            if (name==null || name===""){
                alert("Name can't be blank");
                return false;
            }else if (description==null || description===""){
                alert("Description can't be blank");
                return false;
            }else if (genre==null || genre===""){
                alert("Genre can't be blank");
                return false;
            }else if (runtime==null || genre==="" || isNaN(runtime) ){
                alert("Runtime can't be blank or not numerical");
                return false;
            }else if (posterImg==null || genre===""){
                alert("Poster image can't be blank");
                return false;
            }else {return true}
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
<div class="container" style="position: center">
    <h1><fmt:message key="label.addFilm"/></h1>
    <form style="width:50%" role="form" name="filmForm" action="controller" method="post" enctype="multipart/form-data">
        <input type="hidden" name="command" value="addFilm">
        <div class="form-group">
            <label for="inputName"><fmt:message key="label.name"/></label>
            <input class="form-control" type="text" id="inputName" name="name">
        </div>
        <div class="form-group">
            <label for="inputDescription"><fmt:message key="label.description"/></label>
            <textarea class="form-control" type="text" id="inputDescription" name="description"></textarea>
        </div>
        <div class="form-group">
            <label for="inputGenre"><fmt:message key="label.genre"/></label>
            <input class="form-control" type="text" id="inputGenre" name="genre">
        </div>
        <div class="form-group">
            <label for="inputDirector"><fmt:message key="label.director"/></label>
            <input class="form-control" type="text" id="inputDirector" name="director">
        </div>
        <div class="form-group">
            <label for="inputRuntime"><fmt:message key="label.runtime"/></label>
            <input class="form-control" type="text" id="inputRuntime" name="runtime">
        </div>
        <div class="form-group">
            <label for="inputPosterImg"><fmt:message key="label.posterImg"/></label>
            <input type="file" class="form-control-file" id="inputPosterImg" name="posterImg">
        </div>
        <input class="btn btn-success" type="submit"  onsubmit="return validateForm()" value="<fmt:message key="label.addFilm"/>">
        <button class="btn btn-secondary" formaction="manager.jsp" ><fmt:message key="label.cancel"/></button>
    </form>

    <%--@elvariable id="filmError" type="int"--%>
    <c:if test="${filmError==1}">
        <div  class="alert alert-danger alert- alert-dismissible">
            <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
            <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidFilmName"/>
        </div>
    </c:if>
    <c:if test="${filmError==2}">
        <div  class="alert alert-danger alert- alert-dismissible">
            <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
            <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidDescription"/>
        </div>
    </c:if>
    <c:if test="${filmError==3}">
        <div  class="alert alert-danger alert- alert-dismissible">
            <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
            <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidGenre"/>
        </div>
    </c:if>
    <c:if test="${filmError==4}">
        <div  class="alert alert-danger alert- alert-dismissible">
            <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
            <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidDirector"/>
        </div>
    </c:if>
    <c:if test="${filmError==5}">
        <div  class="alert alert-danger alert- alert-dismissible">
            <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
            <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidRuntime"/>
        </div>
    </c:if>
    <c:if test="${filmError==6}">
        <div  class="alert alert-danger alert- alert-dismissible">
            <button type="button" class="close" data-dismiss="alert"    onclick="resetError()">×</button>
            <strong><fmt:message key="label.error"/></strong><fmt:message key="label.invalidPosterImg"/>
        </div>
    </c:if>
</div>

</body>
</html>
