<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 3/10/19
  Time: 6:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="/ErrorManager.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>FootballTeam</title>
</head>
<body>
<% request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    if (name == null)
        throw new IllegalArgumentException("Name expected");
%>
<h1> Футбольная команда  <%=name%></h1>
<%@include file="ListData.jsp"%>
</body>
</html>
