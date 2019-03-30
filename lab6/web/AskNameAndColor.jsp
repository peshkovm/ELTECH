<%@page import="java.net.URLDecoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding=
        "UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Введите имя пользователя и цвет страницы вывода информации</title>
</head>
<body>
<form METHOD=GET action="InDataProcessor">
    Введите имя
    <br><INPUT TYPE=TEXT NAME="userName"
    <%
        // Выбор всех Cookie
        Cookie [] c = request.getCookies();
        if(c != null)
            for(int i = 0; i < c.length; i++)
                if("userName".equals(c[i].getName())) {
                    // Запись значения в поле ввода, если найден Cookie
                    out.print(" userName='" + URLDecoder.decode(c[i].getValue(), "UTF-8") + "' ");
                    break;
            }
    %>
><br>

    <br>
    Введите цвет
    <br><INPUT TYPE=TEXT NAME="color"
    <%
        // Выбор всех Cookie
        //c = request.getCookies();
        if(c != null)
            for(int i = 0; i < c.length; i++)
                if("color".equals(c[i].getName())) {
                    // Запись значения в поле ввода, если найден Cookie
                    out.print(" color='" + URLDecoder.decode(c[i].getValue(), "UTF-8") + "' ");
                    break;
            }
    %>
><br>

    <INPUT TYPE=SUBMIT value="Ввод">

</form>
</body>
</html>