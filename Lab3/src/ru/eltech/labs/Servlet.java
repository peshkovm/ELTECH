package ru.eltech.labs;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// библиотечный класс для работы с потоками вывода
import java.io.PrintWriter;
import java.util.*;

/**
 * Servlet implementation class BooksList
 */
@WebServlet("/FootballTeam")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Map<String, List<String>> map = Map.ofEntries(Map.entry("Спартак", List.of("Денис Глушаков", "Айрон Дантас", "Зе Луиш", "Роман Зобнин")),
            Map.entry("Локомотив", List.of("Федор Смолов", "Джефферсон Фарфан", "Гжегож Крыховяк", "Антон Миранчук")),
            Map.entry("Барселона", List.of("Лионель Месси", "Усман Дембеле", "Филиппе Коутиньо", "Луис Альберто Суарес")),
            Map.entry("Реал Мадрид", List.of("Винисиус Жуниор", "Серхио Рамос", "Марсело Вийера", "Гарет Бейл")));

    public Servlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        String name = request.getParameter("name");
        String lang = request.getParameter("lang");

        if (!"en".equalsIgnoreCase(lang) && !"ru".equalsIgnoreCase(lang) && lang != null) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Параметр lang может принимать значения en или ru или null");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        ResourceBundle res = ResourceBundle.getBundle("FootballTeam", "en".equalsIgnoreCase(lang) ? Locale.ENGLISH : new Locale("ru", "RU"));
        try {

            out.println("<html>");
            out.println("<head><title>" + res.getString("title") + "</title></head>");
            out.println("<body>");
            out.println(map.get(name));
            out.println("</body>");
            out.println("</html>");
        } finally {

            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
