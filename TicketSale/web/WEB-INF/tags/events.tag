<%-- events.tag --%>
<%@ tag pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://0xleon.github.io/javaee/ELUtilFunctions/1.0/" %>
<%@ attribute name="showAddButton" %>
<c:choose>
	<c:when test="${requestScope.events.size() > 0}">
		<c:forEach var="event" items="${requestScope.events}">
			<div class="panel panel-default">
				<div class="panel-heading" style="min-height: 110%;">
					<h2 class="panel-title" style="display: inline-block;"><a href="/TicketSale/Event?eventID=${event.eventID}">${event.eventName}</a></h2>
					<c:if test="${sessionScope.user.isInGroup('user.admin')}">
						<div class="pull-right">
							<div class="btn-group">
								<button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-cog"></span></button>
								<ul class="dropdown-menu dropdown-menu-right" role="menu">
									<li><a href="#">Event bearbeiten</a></li>
									<li><a href="/TicketSale/CancelReservations?eventID=${event.eventID}">Reservierungen löschen</a></li>
									<li><a href="/TicketSale/DeleteEvent?eventID=${event.eventID}">Event löschen</a></li>
								</ul>
							</div>
						</div>
					</c:if>
				</div>
				<div class="panel-body">
					<p>${f:escapeXML(event.description, false)}</p>
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

<c:if test="${showAddButton && sessionScope.user.isInGroup('user.admin')}">
	<div class="well">
		<a class="btn btn-primary btn-lg btn-block" href="/TicketSale/AddEvent">Event hinzufügen</a>
	</div>
</c:if>
