<%-- tError.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://0xleon.github.io/javaee/ELUtilFunctions/1.0/" %>
<t:root>
	<t:header />
	<body>
		<t:browserUpgrade />
		<t:nav />

		<t:row containerID="error">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div class="panel panel-danger">
					<div class="panel-heading">Fehler</div>
					<div class="panel-body">
						<p>Die Operation wurde konnte nicht ausgeführt werden.</p>
						<p>Ursache: ${f:escapeXML(requestScope.exception.message)}</p>

						<div class="well">
							<pre>${f:escapeXML(requestScope.stacktrace)}</pre>
						</div>
					</div>
				</div>
				<a href="/TicketSale/Index">Weiter zur Startseite</a>
			</div>
			<div class="col-md-2"></div>
		</t:row>
	</body>
</t:root>
