<%@ page import="net.model.RolesTypes" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>edit user</title>
</head>
<body>

    <form style="align-content: center" action="/administrator/editUser" method="POST">

        <p>ID: <input name="id" type="text" readonly="readonly" value="<c:out value="${user.id}" />" /></p>
        <p>Login: <input name="login" type="text" readonly = "readonly" value="<c:out value="${user.login}" />" /></p>
        <p>Password: <input name="password" type="text" value="<c:out value="${user.password}" />" /></p>

        <p>Name: <input name="name" type="text" value="<c:out value="${user.name}" />"  /></p>

        <p>Roles:</p>

        </p>
        <select multiple = "true" name="userRoles">
            <c:forEach items="${roles}" var="role">
                <c:choose>
                    <c:when test="${role.value == true}">
                        <option value="${role.key}" selected="true">${role.key}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${role.key}">${role.key}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        </p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <p><input type="submit" value="save user"/></p>

    </form>

</body>
</html>
