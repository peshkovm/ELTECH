<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<% Map<String, List<String>> map = Map.ofEntries(Map.entry("Спартак", List.of("Денис Глушаков", "Айрон Дантас", "Зе Луиш", "Роман Зобнин")),
        Map.entry("Локомотив", List.of("Федор Смолов", "Джефферсон Фарфан", "Гжегож Крыховяк", "Антон Миранчук")),
        Map.entry("Барселона", List.of("Лионель Месси", "Усман Дембеле", "Филиппе Коутиньо", "Луис Альберто Суарес")),
        Map.entry("Реал Мадрид", List.of("Винисиус Жуниор", "Серхио Рамос", "Марсело Вийера", "Гарет Бейл")));
%>

<%
    List<String> list = map.get(name);
    StringBuilder str = new StringBuilder();
    for (String a : list) {
        String[] arr = a.split(" ");
        str.append("<tr>\n" + "        <td><b>").append(arr[0]).append("</b></td>\n").append("        <td><b>").append(arr[1]).append("</b></td>\n").append("    </tr>");
    }
%>

<table border='1'>
    <tr>
        <td><b>Имя</b></td>
        <td><b>Фамилия</b></td>
    </tr>
    <%=str %>
</table>