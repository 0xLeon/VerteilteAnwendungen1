<%-- header.tag --%>
<%@ tag isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://0xleon.github.io/javaee/ELUtilFunctions/1.0/" %>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>${requestScope.pageTitle}</title>
	<meta name="description" content="${f:escapeXML(requestScope.pageDescription, true)}" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />

	<t:scripts />
	<t:styles />

	<jsp:doBody />
</head>
