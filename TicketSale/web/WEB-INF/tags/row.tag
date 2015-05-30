<%-- row.tag --%>
<%@ tag isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="containerID" required="false" %>
<c:set var="containerCounter" value="${0}" scope="page" />
<div id="${!(empty(containerID)) ? containerID : ('container-'.concat((containerID = containerID + 1)))}" class="container">
	<div class="row">
		<jsp:doBody />
	</div>
</div>
