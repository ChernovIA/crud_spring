
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="users" class="java.util.ArrayList" scope="session"/>
<html>
<head>
    <title>Users list</title>
</head>
<body>
<table border="0" cellpadding="1" cellspacing="1" style="width:500px">

    <table align="center" border="1" cellpadding="1" cellspacing="1" style="width:500px">
        <caption><p>Users list</p> <p><a href="/administrator/addUser">Add User</a>, <a href="/profile">Profile</a></p></caption>
        <tbody>
        <tr>
            <td style="text-align:center"><b>Login</b></td>
            <td style="text-align:center"><b>Name</b></td>
            <th colspan=2><b>Action</b></th>
        </tr>


        <c:forEach items="${users}" var="usr">
            <tr><td style="text-align:center">${usr.login}</td>
                <td style="text-align:center">${usr.name}</td>
                <td style="text-align:center"><a href="/administrator/editUser?id=<c:out value="${usr.id}"/>">Update</a></td>
                <td style="text-align:center"><a href="/administrator/deleteUser?&id=<c:out value="${usr.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>

        </tbody>
    </table>

</table>

</body>
</html>
