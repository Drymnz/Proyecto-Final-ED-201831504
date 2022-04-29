package cunoc.Logic.Converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import cunoc.Logic.Letter.Letter;

public class BufferedReaderToArrayListTypeLetter {
    private  BufferedReader re;

    public BufferedReaderToArrayListTypeLetter(BufferedReader re) {
        this.re = re;
    }

    public ArrayList<Letter> converterArraylistTypeLetter(){
        int read;
        int data = 0;
        int type = 0;
        String json = "";
        ArrayList<Letter> listLetter = new ArrayList<>();
        //response.getWriter().print(json);
        try {
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
                    if (!(read == 163 | read == 165 | read == 166 | 160 == read 
                    | read == 9824| read ==9830| read ==9829| read ==9827)) {
                        json += Character.toString(ch);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listLetter;
    }
    
}
