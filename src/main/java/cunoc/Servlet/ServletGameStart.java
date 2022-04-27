package cunoc.Servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cunoc.DataBase.Connect;
import cunoc.Logic.Converter.TextLetter;
import cunoc.Logic.Converter.TreeGraphConverter;
import cunoc.Logic.Letter.Letter;
import cunoc.Logic.Tree.NodeBinary;
import cunoc.Logic.Tree.Tree;

public class ServletGameStart extends HttpServlet {

    public static Tree<Letter> treeAVL = new Tree<>();
    public static Connect conec = new Connect();

    public static final int SIZE_TEXT = 50;
    public static final String NAME_IMAGE = "/Game/Imagen";
    public static final String WINDOW_LINUX = System.getProperty("file.separator");

    public static final int STATUS_OK = 200;
    public static final int NOT_LETTER_TREE = 404;// La carta no se encuentra en el árbol avl (eliminar)
    public static final int NOT_SUM_TREE_13 = 406;// Los valores de las cartas no suman 13
    public static final int NOT_DELET_CHILDREN = 409;// La carta no se puede eliminar ya que cuenta con hijos
    public static final int LETTER_DUPLICATED = 406;// La carta a insertar esta duplicada
    public static final int ERROR = 400;// Cualquier otro error

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gson conver = new Gson();
        BufferedReader re = request.getReader();
        int read;
        int data = 0;
        int type = 0;
        String json = "";
        ArrayList<Letter> listLetter = new ArrayList<>();

        while ((read = re.read()) != -1) {
            char ch = (char) read;
            boolean registrar = (read == 226 | read == 153);
            if ((read == 34 | data > 0) && !registrar) {
                if (read == 34) {
                    data++;
                }
                if (data == 4) {
                    data = 0;
                    listLetter.add(new TextLetter(json, type).converter());
                    json = "";
                }
                type = read;
                if (!(read == 163 | read == 165 | read == 166 | 160 == read)) {
                    json += Character.toString(ch);
                }
            }

        }
        re.close();
        for (Letter letter : listLetter) {
            if (!(treeAVL.addBoolean(new NodeBinary<Letter>(letter, letter.getValue().getValue())))) {
                response.setStatus(LETTER_DUPLICATED);
            } else {
                response.setStatus(STATUS_OK);
            }
        }
        if (listLetter.isEmpty()) {
            response.setStatus(ERROR);
        }
        // Persona per = conver.fromJson(re,Persona.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean erro = true;
        if (!treeAVL.isEmpty()) {
            if (erro = conec.sevedImagen(new TreeGraphConverter<Letter>(treeAVL, SIZE_TEXT).converter())) {
                resp.getWriter().println(pathJson(NAME_IMAGE));
                resp.setStatus(STATUS_OK);
            }
        }
        if (erro) {
            resp.setStatus(ERROR);
        }
    }

    private String pathJson(String url) {
        return "{\n \"path\":\"" + url + "\" \n}";
    }
    /*
     * response.setContentType("text/html;charset=UTF-8");
     * try (PrintWriter out = response.getWriter()) {
     * out.println("<!DOCTYPE html>");
     * out.println("<html>");
     * out.println("<head>");
     * out.println("<title>Servlet ServletGameStart</title>");
     * out.println("</head>");
     * out.println("<body>");
     * out.println(
     * "<h1>Servlet ServletGameStart at " + request.getContextPath() + json +
     * "</h1>");
     * out.println("</body>");
     * out.println("</html>");
     * }
     */
}