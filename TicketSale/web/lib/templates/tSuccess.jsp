<%-- tSuccess.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:root>
	<t:header>
		<meta http-equiv="refresh" content="3; url=/TicketSale/Index" />
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
				<a href="/TicketSale/Index">Weiter zur Startseite</a>
			</div>
			<div class="col-md-3"></div>
		</t:row>
	</body>
</t:root>
