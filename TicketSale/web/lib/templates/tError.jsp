<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:root>
	<t:header />
	<body>
		<t:browserUpgrade />
		<t:nav />

		<t:mainContainer containerID="error">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="panel panel-danger">
					<div class="panel-heading">Fehler</div>
					<div class="panel-body">
						<p>Die Operation wurde konnte nicht ausgeführt werden.</p>
						<p>Ursache: ${requestScope.exception.message}</p>

						<div class="well">
							<pre>${requestScope.stacktrace}</pre>
						</div>
					</div>
				</div>
				<a href="/TicketSale/Index">Weiter zur Startseite</a>
			</div>
			<div class="col-md-3"></div>
		</t:mainContainer>
	</body>
</t:root>
