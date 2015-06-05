<%-- tEvent.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:root>
	<t:header>
		<script src="./js/event.js"></script>
	</t:header>
	<body>
		<t:browserUpgrade />
		<t:nav />

		<h1 class="text-center">${requestScope.event.eventName}</h1>
		<t:row containerID="event">
			<div class="col-md-6">
				<div id="event-1" class="event text-right">
					<c:set var="i" value="${0}" scope="page" />
					<c:forEach var="entry" items="${requestScope.event.seats.entrySet()}">
						<c:set var="seat" value="${entry.getValue()}" scope="page" />
						<c:choose>
							<c:when test="${seat.isFree()}">
								<span id="seat-${seat.seatID}" class="seat seat-free" data-id="${seat.seatID}">${seat.seatNumber}</span>
							</c:when>
							<c:when test="${seat.isReserved()}">
								<span id="seat-${seat.seatID}" class="seat seat-reserved${(seat.customer.userID == requestScope.user.userID) ? ' seat-own' : ''}" data-id="${seat.seatID}">${seat.seatNumber}</span>
							</c:when>
							<c:when test="${seat.isSold()}">
								<span id="seat-${seat.seatID}" class="seat seat-sold${(seat.customer.userID == requestScope.user.userID) ? ' seat-own' : ''}" data-id="${seat.seatID}">${seat.seatNumber}</span>
							</c:when>
						</c:choose>

						<c:if test="${((i = i + 1) % 10) == 0}">
							<br />
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="col-md-2">
				<div class="action-buttons">
					<form id="ticketForm" method="post" action="/TicketSale/HandleTicket">
						<input type="hidden" id="ticketFormAction" name="ticketFormAction" value="buy" />
						<input type="hidden" id="eventID" name="eventID" value="${requestScope.event.eventID}" />

						<!--<input type="hidden" id="seat-form-input-0" name="seatIDs[]" value="0" />-->
					</form>

					<button type="button" id="submitBuy" class="btn btn-primary btn-block">Kaufen</button>
					<button type="button" id="submitReserve" class="btn btn-default btn-block">Reservieren</button>
					<button type="button" id="submitCancel" class="btn btn-defalt btn-block">Stornieren</button>
				</div>
				<div class="seat-legend">
					<ul class="list-group">
						<li class="list-group-item">
							<span class="seat seat-free"></span>&nbsp;Frei
						</li>
						<li class="list-group-item">
							<span class="seat seat-marked"></span>&nbsp;Ausgewählt
						</li>
						<li class="list-group-item">
							<span class="seat seat-reserved"></span>&nbsp;Reserviert
						</li>
						<li class="list-group-item">
							<span class="seat seat-sold"></span>&nbsp;Verkauft
						</li>
					</ul>
				</div>
			</div>
			<div class="col-md-4"></div>
		</t:row>

		<t:row>
			<div class="col-md-6">
				<div class="text-right">
					<p>Von dir gekaufte oder reservierte Sitze sind grün umrandet.</p>
					<p>Reservierungen möglich bis <fmt:formatDate value="${requestScope.event.reservationDeadline}" pattern="dd.MM.yyyy, HH:mm" /> Uhr.</p>
					<p>Vorverkauf möglich bis <fmt:formatDate value="${requestScope.event.purchaseDeadline}" pattern="dd.MM.yyy, HH:mm" /> Uhr.</p>
				</div>
			</div>
			<div class="col-md-6"></div>
		</t:row>

		<t:footer />
	</body>
</t:root>
