<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Содержимое cookie</title>
</head>
<body>
<h1>Содержимое cookie</h1>
<%
    String userName = (String) session.getAttribute("userName");
    String color = (String) session.getAttribute("color");
    long lastAccessedTime = (long) session.getAttribute("lastAccessedTime");
    int countOfRequests = (int) session.getAttribute("countOfRequests");
    Date date = new Date(lastAccessedTime);
%>

<h4 style="color:<%=color%>">userName= <%=userName%>
</h4>
<h4 style="color:<%=color%>">lastAccessedTime= <%=date%>
</h4>
<h4 style="color:<%=color%>">countOfRequests= <%=countOfRequests%>
</h4>

</body>
</html>