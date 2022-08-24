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
    <title><fmt:message key="label.signUp"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script>
        function validateEmail() {
            let email = document.inputForm.email.value;

            const url = "${pageContext.request.contextPath }/ValidateSignUpField?value=" + email + "&type=email";

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }

            try {
                request.onreadystatechange = getEmailValidationResult;
                request.open("GET", url, true);
                request.send();
            } catch (e) {
                alert("Unable to connect to server");
            }
        }

        function getEmailValidationResult() {
            if (request.readyState === 4) {
                document.getElementById('email').innerHTML = request.responseText;
            }

        }

        function validatePhoneNumber() {
            let phoneNumber = document.inputForm.phoneNumber.value;
            const url = "${pageContext.request.contextPath }/ValidateSignUpField?value=" + phoneNumber + "&type=phoneNumber";

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }

            try {
                request.onreadystatechange = getPhoneNumberValidationResult;
                request.open("GET", url, true);
                request.send();
            } catch (e) {
                alert("Unable to connect to server");
            }
        }

        function getPhoneNumberValidationResult() {
            if (request.readyState === 4) {
                document.getElementById('phoneNumber').innerHTML = request.responseText;
            }
        }

        function validateName() {
            let name = document.inputForm.name.value;
            const url = "${pageContext.request.contextPath }/ValidateSignUpField?value=" + name + "&type=name";

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }

            try {
                request.onreadystatechange = getNameValidationResult;
                request.open("GET", url, true);
                request.send();
            } catch (e) {
                alert("Unable to connect to server");
            }
        }

        function getNameValidationResult() {
            if (request.readyState === 4) {
                document.getElementById('name').innerHTML = request.responseText;
            }
        }
        function validateSurname() {
            let surname = document.inputForm.surname.value;
            const url = "${pageContext.request.contextPath }/ValidateSignUpField?value=" + surname + "&type=surname";

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }

            try {
                request.onreadystatechange = getSurnameValidationResult;
                request.open("GET", url, true);
                request.send();
            } catch (e) {
                alert("Unable to connect to server");
            }
        }

        function getSurnameValidationResult() {
            if (request.readyState === 4) {
                document.getElementById('surname').innerHTML = request.responseText;
            }
        }
        function validateLogin() {
            let login = document.inputForm.login.value;
            const url = "${pageContext.request.contextPath }/ValidateSignUpField?value=" + login + "&type=login";

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }

            try {
                request.onreadystatechange = getLoginValidationResult;
                request.open("GET", url, true);
                request.send();
            } catch (e) {
                alert("Unable to connect to server");
            }
        }

        function getLoginValidationResult() {
            if (request.readyState === 4) {
                document.getElementById('login').innerHTML = request.responseText;
            }
        }
        function validatePassword() {
            let password = document.inputForm.password.value;
            const url = "${pageContext.request.contextPath }/ValidateSignUpField?value=" + password + "&type=password";

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }

            try {
                request.onreadystatechange = getPasswordValidationResult;
                request.open("GET", url, true);
                request.send();
            } catch (e) {
                alert("Unable to connect to server");
            }
        }

        function getPasswordValidationResult() {
            if (request.readyState === 4) {
                document.getElementById('password').innerHTML = request.responseText;
            }
        }
        function validateRepeatedPassword() {
            let password = document.inputForm.password.value;
            let repeatedPassword = document.inputForm.repeatPassword.value;
            const url = "${pageContext.request.contextPath }/ValidateSignUpField?value=" + repeatedPassword +"&value2="+ password + "&type=repeatedPassword";

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }

            try {
                request.onreadystatechange = getRepeatedPasswordValidationResult;
                request.open("GET", url, true);
                request.send();
            } catch (e) {
                alert("Unable to connect to server");
            }
        }

        function getRepeatedPasswordValidationResult() {
            if (request.readyState === 4) {
                document.getElementById('repeatedPassword').innerHTML = request.responseText;
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
            <%--@elvariable id="loggedUser" type="DB.entity.User"--%>
            <c:if test="${loggedUser.role=='manager'}">
                <li><a href="manager.jsp"><fmt:message key="label.managerWorkplace"/></a></li>
            </c:if>

        </ul>
        <ul class="nav navbar-nav navbar-right">
            <tg:changeLanguage/>
            <c:if test="${loggedUser==null}">
                <li class="active"><a href="register.jsp"><span class="glyphicon glyphicon-user"></span><fmt:message key="label.signUp"/></a></li>
                <li><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span><fmt:message key="label.logIn"/></a>
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
    <h2><fmt:message key="label.signUp"/></h2>
    <div class="form-group">
        <form name="inputForm" role="form" action="controller" method="post">
            <input class="hidden" name="command" value="register">
            <div class="form-group">
                <label for="inputEmail"><fmt:message key="label.email"/></label>
                <input class="form-control" type="email" onchange="validateEmail()" id="inputEmail" name="email">
                <span id="email"></span>
            </div>
            <div class="form-group">
                <label for="inputPhoneNumber"><fmt:message key="label.phoneNumber"/></label>
                <input class="form-control" type="text" onchange="validatePhoneNumber()" id="inputPhoneNumber"
                       name="phoneNumber">
                <span id="phoneNumber"></span>
            </div>
            <div class="form-group">
                <label for="inputName"><fmt:message key="label.firstname"/></label>
                <input class="form-control" type="text" onchange="validateName()" id="inputName" name="name">
                <span id="name"></span>
            </div>
            <div class="form-group">
                <label for="inputSurname"><fmt:message key="label.surname"/></label>
                <input class="form-control" type="text" onchange="validateSurname()" id="inputSurname" name="surname">
                <span id="surname"></span>
            </div>
            <div class="form-group">
                <label for="inputLogin"><fmt:message key="label.login"/></label>
                <input class="form-control" type="text" onchange="validateLogin()" id="inputLogin" name="login">
                <span id="login"></span>
            </div>
            <div class="form-group">
                <label for="inputPassword"><fmt:message key="label.password"/></label>
                <input class="form-control" type="password" onchange="validatePassword()" id="inputPassword" name="password">
                <span id="password"></span>
            </div>
            <div class="form-group">
                <label for="inputRepeatPassword"><fmt:message key="label.repeatPassword"/></label>
                <input class="form-control" type="password" onchange="validateRepeatedPassword()" id="inputRepeatPassword" name="repeatPassword">
                <span id="repeatedPassword"></span>
            </div>
            <input type="submit" class="btn btn-success" value="<fmt:message key="label.signUp"/>">
            <input type="submit" formaction="index.jsp" class="btn btn-secondary"
                   value="<fmt:message key="label.cancel"/>">
        </form>
        <%--@elvariable id="registerError" type="int"--%>
        <c:if test="${registerError==1}">
            <div class="alert alert-danger alert- alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.invalidEmail"/>
            </div>
        </c:if>
        <c:if test="${registerError==2}">
            <div class="alert alert-danger alert- alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.emailAlreadyTaken"/>
            </div>
        </c:if>
        <c:if test="${registerError==3}">
            <div class="alert alert-danger alert- alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.invalidLogin"/>
            </div>
        </c:if>
        <c:if test="${registerError==4}">
            <div class="alert alert-danger alert- alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.loginAlreadyTaken"/>
            </div>
        </c:if>
        <c:if test="${registerError==5}">
            <div class="alert alert-danger alert- alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.invalidPhoneNumber"/>
            </div>
        </c:if>
        <c:if test="${registerError==6}">
            <div class="alert alert-danger alert- alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.phoneNumberAlreadyTaken"/>
            </div>
        </c:if>
        <c:if test="${registerError==7}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.passwordsDontMatch"/>
            </div>
        </c:if>
        <c:if test="${registerError==8}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.invalidName"/>
            </div>
        </c:if>
        <c:if test="${registerError==9}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.invalidSurname"/>
            </div>
        </c:if>
        <c:if test="${registerError==10}">
            <div class="alert alert-danger alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" onclick="resetError()">×</button>
                <strong><fmt:message key="label.error"/></strong> <fmt:message key="label.invalidPassword"/>
            </div>
        </c:if>
    </div>

</div>

</body>
</html>
