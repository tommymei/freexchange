<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<sec:authorize access="isAuthenticated()">
	<p>
	<a
		href="/item/list/<sec:authentication property="principal.username" />">My
		Items</a>
	</p>
	<p>
		<a href="/item/post">Post Item</a>
	</p>
</sec:authorize>
<a href="/item/listall">All Items</a>
<p>