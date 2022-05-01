package cunoc.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cunoc.DataBase.Connect;
import cunoc.Logic.Converter.BufferedReaderToArrayListTypeLetter;
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
        if (request.getRequestURI().toString().equalsIgnoreCase("/Game/start")) {
            // fetch json data
            request.setCharacterEncoding("UTF-8"); // identify character
            BufferedReader requestBufferd = request.getReader();
            ArrayList<Letter> listLetter = new BufferedReaderToArrayListTypeLetter(requestBufferd)
                    .converterArraylistTypeLetter();
            requestBufferd.close();
            // enter the cards
            for (Letter letter : listLetter) {
                if (!(treeAVL.addBoolean(new NodeBinary<Letter>(letter, letter.getWeight())))) {
                    response.setStatus(LETTER_DUPLICATED);
                } else {
                    response.setStatus(STATUS_OK);
                }
            }
            if (listLetter.isEmpty()) {
                response.setStatus(ERROR);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean erro = true;
        if (!treeAVL.isEmpty() && req.getRequestURI().toString().equalsIgnoreCase("/Game/status-avltree")) {
            erro = conec.sevedImagen(new TreeGraphConverter<Letter>(treeAVL, SIZE_TEXT).converter());
            if (erro) {
                resp.setContentType("orden/json"); // identify character
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(pathJson(NAME_IMAGE));
                // System.out.println(req.getContextPath().toString());/Game
                // System.out.println(req.getRequestURI().toString()); /Game/status-avltree
                resp.getWriter().close();
            }
        }
        if (erro) {
            resp.setStatus(ERROR);
        }
    }

    // json structure for a url
    private String pathJson(String url) {
        return "{\n \"path\":\"" + url + "\" \n}";
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getRequestURI().toString());
        if (req.getRequestURI().toString().equalsIgnoreCase("/Game/delete")) {
            req.setCharacterEncoding("UTF-8"); // identify character
            BufferedReader requestBufferd = req.getReader();
            ArrayList<Letter> listLetter = new BufferedReaderToArrayListTypeLetter(requestBufferd)
                    .converterArraylistTypeLetter();
            if (!listLetter.isEmpty() && listLetter.size() > 1 && !treeAVL.isEmpty()) {
                Letter letterOne = listLetter.get(0);
                // if the letter k comes
                if (listLetter.size() == 1 && letterOne.getValue().getValue() == 13) {
                    if (!treeAVL.leafDeletion(new NodeBinary<Letter>(letterOne, letterOne.getWeight()))) {
                        resp.setStatus(NOT_DELET_CHILDREN);
                    } else {
                        resp.setStatus(NOT_DELET_CHILDREN);
                    }
                } else if (listLetter.size() == 2) {
                    Letter letterTwo = listLetter.get(1);
                    if ((letterOne.getValue().getValue() + letterTwo.getValue().getValue()) == 13) {
                        // if the nodes is son delete in the tree
                        boolean letterSonOne = ServletGameStart.treeAVL
                                .nodeIsSon(new NodeBinary<Letter>(letterOne, letterOne.getWeight()));
                        boolean letterSonTwo = ServletGameStart.treeAVL
                                .nodeIsSon(new NodeBinary<Letter>(letterTwo, letterTwo.getWeight()));
                        if (letterSonOne && letterSonTwo) {
                            letterSonOne = ServletGameStart.treeAVL
                                    .leafDeletion(new NodeBinary<Letter>(letterOne, letterOne.getWeight()));
                            letterSonTwo = ServletGameStart.treeAVL
                                    .leafDeletion(new NodeBinary<Letter>(letterTwo, letterTwo.getWeight()));
                            resp.setStatus(STATUS_OK);
                        } else {
                            resp.setStatus(NOT_DELET_CHILDREN);
                        }
                    } else {
                        resp.setStatus(NOT_SUM_TREE_13);
                    }
                } else {
                    resp.setStatus(NOT_SUM_TREE_13);
                }

            } else {
                resp.setStatus(ERROR);
            }
        }
    }

}
