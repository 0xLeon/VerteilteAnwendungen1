<%@ page import="com.leon.hfu.web.ticketSale.Event" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:root>
	<t:header />
	<body>
		<t:browserUpgrade />
		<t:nav />

		<t:mainContainer>
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
			<div class="col-md-2 vertical-center">
				<div class="action-buttons">
					<form method="post" action="/TicketSale/Buy">
						<input type="hidden" id="action" name="action" value="buy" />
					</form>

					<button type="button" class="btn btn-primary btn-block">Kaufen</button>
					<button type="button" class="btn btn-default btn-block">Reservieren</button>
				</div>
			</div>
			<div class="col-md-3"></div>
		</t:mainContainer>

		<footer>
			<p>&copy; Stefan Hahn, 2015</p>
		</footer>
	</body>
</t:root>
