import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainServlet extends javax.servlet.http.HttpServlet {
    /**
     * Process post requests
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        processReguest(request, response);
    }

    /**
     * Process get requests
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        processReguest(request, response);
    }

    /**
     * Process all type of requests
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    private void processReguest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // Задание типа кодировки для параметров запроса
        request.setCharacterEncoding("utf-8");
        // Чтение параметра name из запроса
        String stageParam = request.getParameter("stage");
        // Задание типа содержимого для ответа (в том числе кодировки)
        response.setContentType("text/html;charset=UTF-8");
        // Получение потока для вывода ответа

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
            writer.println("<head><title>Список книг</title></head>");
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
            writer.println("</html>");
        }
    }
}
