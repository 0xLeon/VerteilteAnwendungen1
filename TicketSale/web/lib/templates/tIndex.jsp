<%-- tIndex.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:root>
	<t:header/>
	<body>
		<t:browserUpgrade/>
		<t:nav/>

		<t:row containerID="error">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<c:choose>
					<c:when test="${requestScope.events.size() > 0}">
						<c:forEach var="event" items="${requestScope.events}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h2 class="panel-title"><a href="/TicketSale/Event?eventID=${event.eventID}">${event.eventName}</a></h2>
								</div>
								<div class="panel-body">
									<p>${event.description}</p>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="panel panel-info">
							<div class="panel-heading">Information</div>
							<div class="panel-body">
								<p>Keine Veranstaltungen vorhanden.</p>
							</div>
						</div>
					</c:otherwise>
				</c:choose>

			</div>
			<div class="col-md-3"></div>
		</t:row>
	</body>
</t:root>