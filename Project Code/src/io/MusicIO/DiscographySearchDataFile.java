package io.MusicIO;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class DiscographySearchDataFile {
    
    public DiscographySearchDataFile(String albumLink) throws IOException
    {
        JSONObject json = new JSONObject(IOUtils.toString(new URL(albumLink), Charset.forName("UTF-8")));
        String searchData = json.toString();
        FileWriter fw = new FileWriter("resources/files/musicData/discografiaArtista.json");
        fw.write(searchData);
        fw.close();
    }
}
