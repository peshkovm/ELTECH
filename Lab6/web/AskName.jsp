<%@ page import="java.net.URLDecoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Футболисты</title>
</head>
<body>
<%
    Cookie[] c = request.getCookies();
    String color = null;

    if (c != null) {
        for (Cookie cookie : c) {
            if (cookie.getName().equals("color")) {
                color = cookie.getValue();
                break;
            }
        }
        for (int i = 0; i < c.length; i++)
            out.print("<font color=\"" + color + "\"> " + " " + URLDecoder.decode(c[i].getValue(), "UTF-8") + "</font>");
    }

    out.print("<font color=\"" + color + "\"> " + request.getSession().getAttribute("count").toString()+"</font>");
    out.print("<font color=\"" + color + "\"> " + request.getSession().getAttribute("date").toString()+"</font>");
%>
</body>
</html>