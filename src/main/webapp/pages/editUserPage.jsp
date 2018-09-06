<%@ page import="net.model.Roles" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>edit user</title>
</head>
<body>

    <form style="align-content: center" action="/editUser" method="POST">

        <p>ID: <input name="id" type="text" readonly="readonly" value="<c:out value="${user.id}" />" /></p>
        <c:if test="${user.role != Roles.ADMIN}">
            <p>Role: <select name="role"><option value="admin">Admin</option><option selected="selected" value="user">User</option></select></p>
        </c:if>
        <c:if test="${user.role  == Roles.ADMIN}">
            <p>Role: <select name="role"><option selected="selected" value="admin">Admin</option><option value="user">User</option></select></p>
        </c:if>
        <p>Login: <input name="login" type="text" readonly = "readonly" value="<c:out value="${user.login}" />" /></p>
        <p>Password: <input name="password" type="text" value="<c:out value="${user.password}" />" /></p>

        <p>Name: <input name="name" type="text" value="<c:out value="${user.name}" />"  /></p>
        <p><input type="submit" value="save user"/></p>

    </form>

</body>
</html>
