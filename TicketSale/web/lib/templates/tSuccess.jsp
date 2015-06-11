<%-- tSuccess.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://0xleon.github.io/javaee/ELUtilFunctions/1.0/" %>
<c:set var="customRedirect" value="${!(empty(requestScope.redirectURL) || empty(requestScope.redirectText))}" scope="page" />
<t:root>
	<t:header>
		<c:choose>
			<c:when test="${customRedirect}">
				<meta http-equiv="refresh" content="3; url=${f:escapeXML(requestScope.redirectURL, true)}" />
			</c:when>
			<c:otherwise>
				<meta http-equiv="refresh" content="3; url=/TicketSale/Index" />
			</c:otherwise>
		</c:choose>
	</t:header>
	<body>
		<t:browserUpgrade />
		<t:nav />

		<t:row containerID="success">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="panel panel-success">
					<div class="panel-heading">Erfolreich</div>
					<div class="panel-body">Die Operation wurde erfolgreich ausgeführt.</div>
				</div>

				<c:choose>
					<c:when test="${customRedirect}">
						<a href="${f:escapeXML(requestScope.redirectURL, true)}">${f:escapeXML(requestScope.redirectText, false)}</a>
					</c:when>
					<c:otherwise>
						<a href="/TicketSale/Index">Weiter zur Startseite</a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-md-3"></div>
		</t:row>
	</body>
</t:root>
