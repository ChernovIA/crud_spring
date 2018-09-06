<%@ page import="servlet.property.ServletURL" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<table align="center" border="0" cellpadding="0" cellspacing="0" style="width: 500px">
    <tbody>
    <tr>
        <td>
            <form style="align-content: center" action="<%=ServletURL.ServletLogin%>" method="POST">

                <p>Login: <input name="login" type="text" value="<c:out value="${loginText}"/>" /></p>
                <p>Password: <input name="password" type="password" value="<c:out value="${passText}"/>"/></p>
                <p><input type="submit" value="login" /></p>
                <p><font color="#FF0000"><c:out value="${message}"/></font></p>
            </form>
        </td>
    </tr>
    </tbody>
</table>

</body>
</html>
