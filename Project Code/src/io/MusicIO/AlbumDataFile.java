package io.MusicIO;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import domain.MusicDomain.Cancion;

public class AlbumDataFile {

    public AlbumDataFile(String albumId) throws IOException {
        // Escribimos los datos del link en el fichero
        StringBuilder sbId = new StringBuilder("https://theaudiodb.com/api/v1/json/523532/track.php?m=");
        sbId.append(albumId);
        JSONObject json = new JSONObject(IOUtils.toString(new URL(sbId.toString()), Charset.forName("UTF-8")));
        String searchData = json.toString();
        FileWriter fw = new FileWriter("resources/files/musicData/albumSelected.json");
        fw.write(searchData);
        fw.close();
    }


    
    /** 
     * @return ArrayList<Cancion>
     * @throws IOException
     * 
     * Se encarga de obtener las canciones del album seleccionado en el programa, del cual se ha generado un archiv. en el caso de que no haya video musical obtenido en la API. 
     * No se va a mostrar ningun boton en la ventana correspondiente
     */
    public ArrayList<Cancion> cancionesAlbum() throws IOException
    {
        
        // Despues de leer el fichero vamos a pasar un ArrayList de Canciones
        ArrayList<Cancion> cancionesAlbum = new ArrayList<>();
        // Leemos los datos del fichero
        InputStream is = new FileInputStream("resources/files/musicData/albumSelected.json");
        String albumCanciones = IOUtils.toString(is, "UTF-8");
        JSONObject outerObject = new JSONObject(albumCanciones);
        JSONArray resultArray = outerObject.getJSONArray("track");

        // Iteracion para cada cancion
        for (int i = 0; i < resultArray.length(); i++) {
            Cancion cancion = new Cancion();
            JSONObject objectInArray = resultArray.getJSONObject(i);
            String[] elementNames = JSONObject.getNames(objectInArray);

            for (String elementName : elementNames) {
                try {
                    if (elementName.equals("intTrackNumber")) {
                        cancion.setSongNumber(objectInArray.getString(elementName));
                    } else if (elementName.equals("strTrack")) {
                        cancion.setNombreCancion(objectInArray.getString(elementName));
                    } else if (elementName.equals("strArtist")) {
                        cancion.setNombreArtista(objectInArray.getString(elementName));
                    } else if (elementName.equals("strMusicVid")) {
                        if (objectInArray.get(elementName).equals(null)) {
                            cancion.setMvLink("null");
                        } else {
                            cancion.setMvLink(objectInArray.getString(elementName));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            cancionesAlbum.add(cancion);
        }
        return cancionesAlbum;
    }
}
