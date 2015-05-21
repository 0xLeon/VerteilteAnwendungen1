<%@ page import="com.leon.hfu.web.ticketSale.Event" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:root>
	<t:header />
	<body>
		<t:browserUpgrade />
		<t:nav />

		<t:mainContainer containerID="index">
			<div class="col-md-7">
				<div id="event-1" class="event">
					<c:set var="i" value="${0}" scope="page" />
					<c:forEach var="seat" items="${applicationScope.event.seats}">
						<c:choose>
							<c:when test="${seat.isFree()}">
								<span id="seat-${seat.seatID}" class="seat seat-free"></span>
							</c:when>
							<c:when test="${seat.isReserved()}">
								<span id="seat-${seat.seatID}" class="seat seat-reserved"></span>
							</c:when>
							<c:when test="${seat.isSold()}">
								<span id="seat-${seat.seatID}" class="seat seat-sold"></span>
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
			<div class="col-md-3"></div>
		</t:mainContainer>

		<footer>
			<p>&copy; Stefan Hahn, 2015</p>
		</footer>
	</body>
</t:root>
