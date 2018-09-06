<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>add user</title>
</head>
<body>
    <form style="align-content: center" action="/addUser" method="POST">

        <p>Role: <select name="role"><option value="Admin">Admin</option><option value="User">User</option></select></p>
        <p>Login: <input name="login" type="text" /></p>
        <p>Password: <input name="password" type="password" /></p>
        <p>Name: <input name="name" type="text" /></p>
        <p><input type="submit" value="add user" /></p>

    </form>

</body>
</html>
