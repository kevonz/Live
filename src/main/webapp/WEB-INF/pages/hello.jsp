<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
	<h1>${message}</h1>

    <form METHOD=POST ACTION="login">
        用户名：<input TYPE="text" NAME="username" value="admin"><br>
        密 码：<input TYPE="password" NAME="password" value="">
        <br>
        <input TYPE="submit">
    </form>
</body>
</html>