<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page import="com.google.appengine.api.datastore.Key"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String principleUsername = null;
%>

<html>
<head>
</head>
<body>
	<sec:authorize access="isAnonymous()">
		<p>
			<spring:message code="global.login" htmlEscape="false" />
		</p>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<p>
			<spring:message code="global.logout" htmlEscape="false" />
		</p>
		<c:set var="principleUsername">
			<sec:authentication property="principal.username" />
		</c:set>
	</sec:authorize>

	<c:forEach var="mediaObj" items="${itemList}">
   Posted By: ${mediaObj.username}<br>
   Title: ${mediaObj.title}<br>
   Description: ${mediaObj.description}<br>
   Zipcode: ${mediaObj.zipcode}<br>
   Last Modified: ${mediaObj.modification}<br>
   Created At: ${mediaObj.creation}<br>
		<c:if test="${mediaObj.blob != null}">
			<iframe width="300" height="250" seamless="seamless" scrolling="no"
				src="/item/image/${mediaObj.blob.keyString}"></iframe>
		</c:if>
		<c:if test="${editable}">
			<c:if test="${principleUsername == mediaObj.username}">
				<a href="/item/delete/${mediaObj.webSafeKey}">DELETE</a>
				<br>
			</c:if>
		</c:if>
		<hr>
	</c:forEach>
</body>
</html>
