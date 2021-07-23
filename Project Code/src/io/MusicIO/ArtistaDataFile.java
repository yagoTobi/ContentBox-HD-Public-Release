package io.MusicIO;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class ArtistaDataFile {

    public ArtistaDataFile(String selectedArtistaLink) throws IOException
    {
        JSONObject json = new JSONObject(IOUtils.toString(new URL(selectedArtistaLink), Charset.forName("UTF-8")));
        String searchData = json.toString();
        FileWriter fw = new FileWriter("resources/files/musicData/artistaSelected.json");
        fw.write(searchData);
        fw.close();
    }
    
}
