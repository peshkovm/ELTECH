<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.BufferedWriter" %><%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 23.03.2019
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<% List<String> stages = new ArrayList<>(Arrays.asList(
        "<tr>" +
                "<td>1 декабря 2017</td>" +
                "<td>1 этап</td>" +
                "<td>гонка в России</td>" +
                "</tr>",
        "<tr>" +
                "<td>2 декабря 2017</td>" +
                "<td>2 этап</td>" +
                "<td>гонка в Финляндии</td>" +
                "</tr>",
        "<tr>" +
                "<td>3 декабря 2017</td>" +
                "<td>3 этап</td>" +
                "<td>гонка в Японии</td>" +
                "</tr>",
        "<tr>" +
                "<td>4 декабря 2017</td>" +
                "<td>4 этап</td>" +
                "<td>гонка в Норвегии</td>" +
                "</tr>",
        "<tr>" +
                "<td>5 декабря 2017</td>" +
                "<td>5 этап</td>" +
                "<td>гонка в Америке</td>" +
                "</tr>"
));

    int stage;
%>

<%
    try (PrintWriter writer = new PrintWriter(new BufferedWriter(response.getWriter()))) {
// Создание HTML-страницы
        writer.println("<html>");
        writer.println("<head><title>Формула-1 2017</title></head>");
        writer.println("<body>");
        writer.println("<h1>Формула-1 2017</h1>");
        writer.println("<table border='1'>");
        writer.println("<tr>" +
                "<td><b>Дата, время</b></td>" +
                "<td><b>Этап</b></td>" +
                "<td><b></b></td>" +
                "</tr>");
//print stages
        if (stageParam == null || Integer.parseInt(stageParam) > stages.size()) {
            stages.forEach(writer::println);
        } else {
            stage = Integer.parseInt(stageParam);
            writer.println(stages.get(stage - 1));
        }

        writer.println("</table>");
        writer.println("</body>");
    }
%>