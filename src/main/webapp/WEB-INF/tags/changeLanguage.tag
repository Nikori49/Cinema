<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<li <c:if test="${language=='en'}">class="active" </c:if>> <a href="#" onclick="changeLanguage('en')" >En</a></li>
<li <c:if test="${language=='uk'}">class="active" </c:if>> <a href="#" onclick="changeLanguage('uk')" >Укр</a></li>