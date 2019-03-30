import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

@WebServlet(name = "InDataProcessor")
public class InDataProcessor extends HttpServlet {
    private HashMap<String, Integer> usersCountOfRequests = new HashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        // Получение параметра из строки запроса
        String parameterUserName = request.getParameter("userName");
        System.out.println(parameterUserName);
        if (parameterUserName == null) {
            // Сообщение об ошибке, если сервлет вызван без параметра
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не задано имя пользователя");
            return;
        }

        if (!usersCountOfRequests.containsKey(parameterUserName))
            usersCountOfRequests.put(parameterUserName, 1);
        else
            usersCountOfRequests.replace(parameterUserName, usersCountOfRequests.get(parameterUserName) + 1);

        // Сохранение имени пользователя в сессии
        request.getSession().setAttribute("userName", parameterUserName);
        // Сохранение имени пользователя в Cookie
        Cookie c = new Cookie("userName", URLEncoder.encode(parameterUserName, "UTF-8"));

        // Получение параметра из строки запроса
        String parameterColor = request.getParameter("color");
        System.out.println(parameterColor);
        if (parameterColor == null) {
            // Сообщение об ошибке, если сервлет вызван без параметра
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не задан цвет");
            return;
        }
        // Сохранение цвета в сессии
        request.getSession().setAttribute("color", parameterColor);
        // Сохранение цвета в Cookie
        Cookie c1 = new Cookie("color", URLEncoder.encode(parameterColor, "UTF-8"));

        request.getSession().setAttribute("lastAccessedTime", request.getSession().getLastAccessedTime());
        request.getSession().setAttribute("countOfRequests", usersCountOfRequests.get(parameterUserName));

        // Установка времени жизни Cookie в секундах
        c.setMaxAge(100);
        c1.setMaxAge(100);
        response.addCookie(c);
        response.addCookie(c1);
        // Перенаправление на страницу книг
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() +
                "/ShowCookieAndSession.jsp"));
    }
}
