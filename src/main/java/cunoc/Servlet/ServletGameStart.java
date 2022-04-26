package cunoc.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ServletGameStart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson conver = new Gson();
        BufferedReader re = request.getReader();
        int r;
        String json = "";
        while ((r = re.read()) != -1) {
            char ch = (char) r;
            System.out.println(r + Character.toString(ch));
            json += Character.toString(ch);
        }

        // Persona per = conver.fromJson(re,Persona.class);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletGameStart</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(
                    "<h1>Servlet ServletGameStart at " + request.getContextPath() + json + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
