import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class MainServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        processReguest(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        processReguest(request, response);
    }

    private void processReguest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Locale locale;

        //Чтение параметров из строки
        String stageParam = request.getParameter("stage");
        String langParam = request.getParameter("lang");

        if (langParam == null)
            locale = Locale.getDefault();
        else if (langParam.equals("ru"))
            locale = new Locale("ru", "RU");
        else if (langParam.equals("en"))
            locale = Locale.ENGLISH;
        else {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Параметр lang может принимать значения en или ru");
            return;
        }
        // Задание типа содержимого для ответа (в том числе кодировки)
        response.setContentType("text/html;charset=UTF-8");
        // Файлы ресурсов book.properties, book_en.properties и book_ru.properties
        // Установка локализации в соответствии с выбором пользователя
        ResourceBundle resBundle = ResourceBundle.getBundle("f1Prop", locale);

        List<String> stages = new ArrayList<>(Arrays.asList(
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

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(response.getWriter()))) {
            // Создание HTML-страницы
            writer.println("<html>");
            writer.println("<head><title>" + resBundle.getString("header") + "</title></head>");
            writer.println("<body>");
            writer.println("<h1>" + resBundle.getString("header") + "</h1>");
            writer.println("<table border='1'>");
            writer.println("<tr>" +
                    "<td><b>" + resBundle.getString("dateAndTime") + "</b></td>" +
                    "<td><b>" + resBundle.getString("stage") + "</b></td>" +
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
            writer.println("</html>");
        }
    }
}