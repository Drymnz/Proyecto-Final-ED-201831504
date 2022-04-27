package cunoc.DataBase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Connect {
    private final String url = "jdbc:mysql://localhost:3306/tab_tree_avl";
    private Connection con;
    private final String USER = "admin_tree_avl_final";
    private final String PASSWORD = "Adm1n_Avl";
    private final String MYSQL = "com.mysql.cj.jdbc.Driver";

    public Connect() {
        try {
            Class.forName(MYSQL);
            con = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Se conecto a la base de datos");
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Error de conneccion base de datos");
        }
    }

    public ResultSet visual() {
        ResultSet result = null;
        try {
            PreparedStatement prepared = con.prepareStatement("SELECT * FROM tab_image");
            result = prepared.executeQuery();
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println("Error de consulta");
        }
        return result;
    }

    public boolean sevedImagen(BufferedImage image) {
        String isert = "INSERT INTO tab_image (phot) VALUES (?  )";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            PreparedStatement result = con.prepareStatement(isert);
            result.setBlob(1, is);
            result.executeUpdate();
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Error en la insercion de la imagen");
            return false;
        }
    }

    public void viewImagen(int id) {
        ResultSet result = this.visual();
        try {
            int idTest;
            boolean stopSearch = false;
            while (!stopSearch && result.next()) {
                idTest = result.getInt(1);
            }
            Object fil = new Object();
            Blob phot = result.getBlob(2);

            byte[] data = phot.getBytes(1, (int) phot.length());
            BufferedImage img = null;
            try {
                img = ImageIO.read(new ByteArrayInputStream(data));
            } catch (Exception e) {
                // TODO: handle exception
            }
            ImageIcon icon = new ImageIcon(img);
            fil = new JLabel(icon);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
