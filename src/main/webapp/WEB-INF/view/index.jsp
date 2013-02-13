<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <title><spring:message code="index.title" /></title>
    </head>
    <body>
        <sec:authorize access="isAnonymous()">
            <p><spring:message code="global.login" htmlEscape="false" /></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p><spring:message code="global.logout" htmlEscape="false" /></p>
        </sec:authorize>
        <p>
            Server: <%= application.getServerInfo() %><br/>
            Servlet Specification: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %><br/>
            JSP Version: <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %>
        </p>
    </body>
</html>
