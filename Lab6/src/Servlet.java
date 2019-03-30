import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FootballTeam")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String[] authors = {"Булгаков", "Пелевин"};

    public Servlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String name = request.getParameter("name");
        String color = request.getParameter("color");

        if (name == null || color == null) {
            // Сообщение об ошибке, если сервлет вызван без параметра
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не заданы праметры");
            return;
        }


        request.getSession().setAttribute("name", name);
        request.getSession().setAttribute("color", color);

        Integer count = (Integer) request.getSession().getAttribute("count");

        if (count == null) {
            count = 0;
        }

        request.getSession().setAttribute("count", ++count);
        request.getSession().setAttribute("date", new Date(request.getSession().getLastAccessedTime()));

        Cookie c1 = new Cookie("name", URLEncoder.encode(name, "UTF-8"));
        Cookie c2 = new Cookie("color", URLEncoder.encode(color, "UTF-8"));

        c1.setMaxAge(100);
        response.addCookie(c1);
        c2.setMaxAge(100);
        response.addCookie(c2);

        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() +
                "/AskName.jsp"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}