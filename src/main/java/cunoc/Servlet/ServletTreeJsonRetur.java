package cunoc.Servlet;

import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cunoc.Logic.Converter.BufferedReaderToArrayListTypeLetter;
import cunoc.Logic.Letter.Letter;
import cunoc.Logic.Tree.NodeBinary;

public class ServletTreeJsonRetur extends HttpServlet {

    private final String PARAMETER_ORDEN = "transversal";
    private final String PARAMETER_LEVEL = "level";

    private final String PRE_ORDEN = "preOrder";
    private final String POS_ORDEN = "postOrder";
    private final String IN_ORDEN = "inOrder";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ServletGameStart.treeAVL.isEmpty()) {
            resp.setStatus(ServletGameStart.ERROR);
        } else {
            String json = doGetReusable(req, resp);
            resp.setContentType("orden/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
            resp.getWriter().close();
            resp.setStatus(ServletGameStart.STATUS_OK);
        }
    }

    private String doGetReusable(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String json = "";
        String orden = req.getParameter(PARAMETER_ORDEN);
        if (orden != null) {
            switch (orden) {
                case PRE_ORDEN:
                    json = converterNodeStringJson(ServletGameStart.treeAVL.arrayTreeInPreOrden());
                    break;
                case POS_ORDEN:
                    json = converterNodeStringJson(ServletGameStart.treeAVL.arrayTreeInPosOrden());
                    break;
                case IN_ORDEN:
                    json = converterNodeStringJson(ServletGameStart.treeAVL.arrayTreeInOrden());
                    break;
                default:
                    resp.setStatus(ServletGameStart.ERROR);
                    break;
            }
            resp.setStatus(ServletGameStart.STATUS_OK);
        } else {
            String level = req.getParameter(PARAMETER_LEVEL);
            if (level != null) {
                int levelRetur = Integer.valueOf(level);
                //System.out.println(ServletGameStart.treeAVL.getHeight());
                if (levelRetur < (ServletGameStart.treeAVL.getHeight() ) && levelRetur > 0) {
                    json = converterNodeStringJson(ServletGameStart.treeAVL.arrayNodeLevel(levelRetur-1));
                    resp.setStatus(ServletGameStart.STATUS_OK);
                } else {
                    resp.setStatus(ServletGameStart.ERROR);
                }
            } else {
                resp.setStatus(ServletGameStart.ERROR);
            }
        }
        return json;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        ArrayList<Letter> listLetter = new BufferedReaderToArrayListTypeLetter(req.getReader()).converterArraylistTypeLetter();
        ArrayList<Letter> listLetterGood = new ArrayList<>();
        if (!ServletGameStart.treeAVL.isEmpty() && !listLetter.isEmpty() ) {
            for (Letter iterable_element : listLetter) {
                if (ServletGameStart.treeAVL.addBoolean(new NodeBinary<Letter>(iterable_element, iterable_element.getWeight()))) {
                    listLetterGood.add(iterable_element);
                    resp.setStatus(ServletGameStart.STATUS_OK);
                }else{
                    resp.setStatus(ServletGameStart.LETTER_DUPLICATED);
                    break;
                }
            }
        }
    }
    private String converterNodeStringJson(NodeBinary[] arrayNode) {
        String send = "{";
        for (int i = 0; i < arrayNode.length; i++) {
            if (arrayNode[i] != null) {
                String jump = (i < arrayNode.length - 1) ? "," : "";
                send += "\n\"" + i + "\"" + ":\"" + arrayNode[i].getData().toString() + "\"" + jump;
            }
        }
        send += "\n}";
        return send;
    }
}
