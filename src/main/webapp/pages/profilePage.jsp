
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>

    <table align="center" border="0" cellpadding="0" cellspacing="0" style="width: 500px">
        <tr>
            <th scope="col" style="text-align: left;"><p>Hi! <b><security:authentication property="principal.username"/></b></p></th>
            <th scope="col" style="text-align: left;">

                <security:authorize access="hasRole('ADMIN')">
                    <p><form action="/administrator/usersList" method="get">
                    <p align="right"><input type="submit" value="User list"></p>
                    </form></p>
                </security:authorize>

                <p><form action="/logout" method="post">

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <p align="right"><input type="submit" value="Logout"></p>
                </form></p>
            </th>
        </tr>
        <tbody>
        <tr>
            <td>
                <p><b>information</b>:</p>
                <p>session: <font size="2"><b><%=session.getId()%></b></font></p>
            </td>
            <td></td>
        </tr>
        </tbody>
    </table>

</form>

</body>
</html>
