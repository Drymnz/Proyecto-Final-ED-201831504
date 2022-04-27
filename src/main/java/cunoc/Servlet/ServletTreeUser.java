package cunoc.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletTreeUser extends HttpServlet {
    // MEDIUMBLOBpara 16777215 bytes (16 MB) como máximo.
    public final int SIZE_IMAGE = 16777215;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream resultado = ServletGameStart.conec.viewImagen();
        resp.setContentType("image/png");
        resp.setHeader("Content-disposition", "attachment; filename=image.png");
        try (InputStream in = resultado;
                OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[SIZE_IMAGE];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }
}
