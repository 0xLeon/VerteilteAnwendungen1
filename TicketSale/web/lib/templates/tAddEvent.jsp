<%-- tAddEvent.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://0xleon.github.io/javaee/ELUtilFunctions/1.0/" %>
<t:root>
	<t:header>
		<script src="./js/addEvent.js"></script>
	</t:header>
	<body>
		<t:browserUpgrade />
		<t:nav />

		<h1 class="text-center">${requestScope.title}</h1>
		<t:row containerID="addEvent">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<c:if test="${requestScope.error != null}">
					<div class="panel panel-danger">
						<div class="panel-heading">Fehler</div>
						<div class="panel-body">${f:escapeXML(requestScope.error.message, false)}</div>
					</div>
				</c:if>

				<form method="post" action="/TicketSale/AddEvent">
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-text-size"></span></span>
							<input type="text" class="form-control" id="eventName" name="eventName" placeholder="Event-Name" required="required" maxlength="45" value="${f:escapeXML(((requestScope.eventName != null) ? requestScope.eventName : ''), true)}" />
						</div>
					</div>
					<div class="form-group">
						<textarea class="form-control" id="description" name="description" placeholder="Beschreibung">${f:escapeXML(((requestScope.eventDescription != null) ? requestScope.eventDescription : ''), false)}</textarea>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
							<input type="number" class="form-control" id="seatCount" name="seatCount" min="1" required="required" placeholder="Sitz-Anzahl" value="${f:escapeXML(((requestScope.seatCount != null) ? requestScope.seatCount : ''), true)}" />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group date">
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
							<input type="date" class="form-control" id="reservationDeadline" name="reservationDeadline" placeholder="Reservierungs-Deadline" required="required" value="${f:escapeXML(((requestScope.reservationDeadline != null) ? requestScope.reservationDeadline : ''), true)}" />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group date">
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
							<input type="date" class="form-control" id="purchaseDeadline" name="purchaseDeadline" placeholder="Kauf-Deadline" required="required" value="${f:escapeXML(((requestScope.purchaseDeadline != null) ? requestScope.purchaseDeadline : ''), true)}" />
						</div>
					</div>
					<button type="submit" class="btn btn-primary">Speichern</button>
				</form>
			</div>
			<div class="col-md-3"></div>
		</t:row>
	</body>
</t:root>
