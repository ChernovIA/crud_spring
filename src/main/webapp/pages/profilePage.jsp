<%@ page import="servlet.property.ServletURL" %>
<%@ page import="dbService.dataSets.Roles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>

    <table align="center" border="0" cellpadding="0" cellspacing="0" style="width: 500px">
        <tr>
            <th scope="col" style="text-align: left;"><p>Hi! <b>${sessionScope.user.name}</b></p></th>
            <th scope="col" style="text-align: left;">

                <c:if test="${sessionScope.user.role == Roles.ADMIN}">
                    <p><form action="<%=ServletURL.ServletUserControl%>" method="post">
                    <p align="right"><input type="submit" value="User list"></p>
                    </form></p>
                </c:if>

                <p><form action="<%=ServletURL.ServletLogout%>" method="post">
                    <p align="right"><input type="submit" value="Logout"></p>
                </form></p>
            </th>
        </tr>
        <tbody>
        <tr>
            <td>
                <p><b>User information</b>:</p>
                <p >Login: <font size="2"><b>${sessionScope.user.login}</b></font></p>
                <p>Name: <font size="2"><b>${sessionScope.user.name}</b></font></p>
                <p>session: <font size="2"><b><%=session.getId()%></b></font></p>
            </td>
            <td></td>
        </tr>
        </tbody>
    </table>

</form>

</body>
</html>
