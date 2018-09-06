<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 15.08.2018
  Time: 0:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <p style="text-align: center;">Something was wrong!</p>
    <p style="text-align: center;">Server sad: <c:out value="${result.desc}"/></p>
</body>
</html>
