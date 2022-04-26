package cunoc.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cunoc.Logic.Converter.TextLetter;
import cunoc.Logic.Letter.Letter;
import cunoc.Logic.Letter.ListSimbol;

public class ServletGameStart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson conver = new Gson();
        BufferedReader re = request.getReader();
        int r;
        String json = "";
        int data = 0;
        ArrayList<Letter> listLetter = new ArrayList<>();
        ListSimbol type = null;
        while ((r = re.read()) != -1) {
            char ch = (char) r;
            boolean registrar = (r == 226 | r == 153);
            if ((r == 34 | data > 0) && !registrar) {
                switch (r) {
                    case 163:
                        type = ListSimbol.CLOVER;
                        break;
                    case 165:
                        type = ListSimbol.HEART;
                        break;
                    case 166:
                        type = ListSimbol.DIAMOND;
                        break;
                    case 160:
                        type = ListSimbol.APPLE;
                        break;
                    default:
                        break;
                }
                if (r == 34) {
                    data++;
                }
                if (data == 4) {
                    data = 0;
                    listLetter.add(new TextLetter(json, type).converter());
                    json = "";
                    type = null;
                }
                if (type == null) {
                    json += Character.toString(ch);
                }
            }

        }
        for (Letter letter : listLetter) {
            System.out.println(letter.toString());
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
