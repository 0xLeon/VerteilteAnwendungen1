<%-- tIndex.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://0xleon.github.io/javaee/ELUtilFunctions/1.0/" %>
<t:root>
	<t:header />
	<body>
		<t:browserUpgrade />
		<t:nav />

		<t:row containerID="index">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<t:events showAddButton="${true}" />
			</div>
			<div class="col-md-3"></div>
		</t:row>
	</body>
</t:root>
