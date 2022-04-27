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

public class Connect {
    private final String url = "jdbc:mysql://localhost:3306/tab_tree_avl";
    private Connection con;
    private final String USER = "admin_tree_avl_final";
    private final String PASSWORD = "Adm1n_Avl";
    private final String MYSQL = "com.mysql.cj.jdbc.Driver";

    public Connect() {
        this.con = connectServert();
    }

    private Connection connectServert() {
        Connection finalConnection = null;
        try {
            Class.forName(MYSQL);
            finalConnection = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Se conecto a la base de datos");
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Error de conneccion base de datos");
        }
        return finalConnection;
    }

    // database request to table tab_image
    public ResultSet visual() {
        this.con = connectServert();
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

    // seved image in table tab_image
    public boolean sevedImagen(BufferedImage image) {
        String isert = "INSERT INTO tab_image (phot) VALUES (?)";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            PreparedStatement result = con.prepareStatement(isert);
            result.setBlob(1, is);
            result.executeUpdate();
            is.close();
            result.close();
            os.close();
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Error en la insercion de la imagen");
            return false;
        }
    }
    // return image in table tab_image as InputStream, latest data
    public InputStream viewImagen() {
        ResultSet result = this.visual();
        Blob image = null;
        InputStream binaryStream = null;
        try {
            while (result.next()) {
                image = result.getBlob("phot");
                binaryStream = image.getBinaryStream(1, image.length());
            }
        } catch (Exception e) {
            System.out.println("Erro en recoger la imagen");
        }
        return binaryStream;
    }
}
