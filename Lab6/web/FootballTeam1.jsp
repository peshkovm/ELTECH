BookList1.jsp
<%@page import="java.net.URLDecoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding=
        "UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Запрос</title>
</head>
<body>
<form METHOD=GET action="FootballTeam">
    Введите имя и цвет <br>
    <INPUT TYPE=TEXT NAME="name"> <br> <br>
    <INPUT TYPE=TEXT NAME="color"> <br>
    <INPUT TYPE=SUBMIT value="Ввод">
</form>
</body>
</html>