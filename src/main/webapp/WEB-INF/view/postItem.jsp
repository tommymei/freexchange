<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title><spring:message code="post_item.title" /></title>
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
	</sec:authorize>


	<form:form modelAttribute="item" action="${uploadUrl}" method="post"
		enctype="multipart/form-data">
		<fieldset>
			<p>
				<form:select path="type">
					<form:option value="OFFER" label="Offer" />
					<form:option value="WANTED" label="Wanted" />
				</form:select>
			</p>
			<p>
				<form:label for="title" path="title" cssErrorClass="error">
					<spring:message code="post_item.label.title" />
				</form:label>
				<form:input path="title" />
				<form:errors path="title" cssClass="error" />
			</p>
			<p>
				<form:label for="description" path="description"
					cssErrorClass="error">
					<spring:message code="post_item.label.description" />
				</form:label>
				<form:textarea path="description" />
				<form:errors path="description" cssClass="error" />
			</p>
			<p>
				<form:label for="zipcode" path="zipcode" cssErrorClass="error">
					<spring:message code="post_item.label.zipcode" />
				</form:label>
				<form:input path="zipcode" />
				<form:errors path="zipcode" cssClass="error" />
			</p>

			<p>
				<form:label for="fileData" path="fileData" cssErrorClass="error">
					<spring:message code="post_item.label.fileData" />
				</form:label>
				<form:input path="fileData" type="file" />
			</p>
			<p>
				<input name="reset" type="reset"
					value="<spring:message code="global.reset" />" /> <input
					name="submit" type="submit"
					value="<spring:message code="global.submit" />" />
			</p>
		</fieldset>
	</form:form>
</body>
</html>
