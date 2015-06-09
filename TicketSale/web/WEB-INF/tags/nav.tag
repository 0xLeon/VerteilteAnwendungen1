<%-- nav.tag --%>
<%@ tag pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://0xleon.github.io/javaee/ELUtilFunctions/1.0/" %>
<div id="top"></div>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
				data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/TicketSale/Index">Ticket Sale</a>
		</div>

		<c:choose>
			<c:when test="${sessionScope.user.userID == 0}">
				<div class="navbar-collapse collapse">
					<form class="navbar-form navbar-right" role="form">
						<div class="form-group">
							<input type="text" placeholder="Email" class="form-control">
						</div>
						<div class="form-group">
							<input type="password" placeholder="Password" class="form-control">
						</div>
						<button type="submit" class="btn btn-success">Sign in</button>
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<div id="user-${sessionScope.user.userID}">
					<ul class="nav navbar-nav navbar-right">
						<li class="dorpdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">${f:escapeXML(sessionScope.user.username, false)} <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">Profil</a></li>
								<li><a href="#">Logout</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</nav>
