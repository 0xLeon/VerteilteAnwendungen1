<%-- header.tag --%>
<%@ tag isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>${requestScope.title}</title>
	<meta name="description" content="${requestScope.description}" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />

	<t:scripts />
	<t:styles />

	<jsp:doBody />
</head>
