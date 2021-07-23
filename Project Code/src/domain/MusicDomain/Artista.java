package domain.MusicDomain;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Artista {
    
    private String nombreArtista; 
    private String biografiaArtista;
    private int idArtista;
    private String genero;
    private String fotoArtistaLink;
    private JFrame warningSearch = new JFrame();

    public Artista()
    {
        try{
            InputStream is = new FileInputStream("resources/files/musicData/artistaSelected.json");
            String artistaJSonText = IOUtils.toString(is, "UTF-8");
            JSONObject outerObject = new JSONObject(artistaJSonText);

            JSONArray resultArray = outerObject.getJSONArray("artists");

            JSONObject objectinArray = resultArray.getJSONObject(0);
            String[] elementNames = JSONObject.getNames(objectinArray);

            for(String elementName: elementNames)
            {
                if(elementName.equals("strArtist"))
                {
                    this.setNombreArtista(objectinArray.getString(elementName));
                }
                else if(elementName.equals("strBiographyES"))
                {
                    if(objectinArray.get(elementName).equals(null))
                    {
                        this.setBiografiaArtista(objectinArray.getString("strBiographyEN"));
                    }else 
                    {
                        this.setBiografiaArtista(objectinArray.getString(elementName));
                    }
                    
                }
                else if(elementName.equals("idArtist"))
                {
                    this.setIdArtista(Integer.parseInt(objectinArray.getString(elementName)));
                }
                else if(elementName.equals("strGenre"))
                {
                    this.setGenero(objectinArray.getString(elementName));
                }
                else if(elementName.equals("strArtistThumb"))
                {
                    this.setFotoArtistaLink(objectinArray.getString(elementName));
                }
            }
            
        
        }catch(Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(warningSearch, "Artista no encontrado, intente meter el nombre exacto", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    
    /** 
     * @return String
     */
    public String getNombreArtista() {
        return nombreArtista;
    }

    
    /** 
     * @param nombreArtista
     */
    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    
    /** 
     * @return String
     */
    public String getBiografiaArtista() {
        return biografiaArtista;
    }

    
    /** 
     * @param biografiaArtista
     */
    public void setBiografiaArtista(String biografiaArtista) {
        this.biografiaArtista = biografiaArtista;
    }

    
    /** 
     * @return int
     */
    public int getIdArtista() {
        return idArtista;
    }

    
    /** 
     * @param idArtista
     */
    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    
    /** 
     * @return String
     */
    public String getGenero() {
        return genero;
    }

    
    /** 
     * @param genero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    
    /** 
     * @return String
     */
    public String getFotoArtistaLink() {
        return fotoArtistaLink;
    }

    
    /** 
     * @param fotoArtistaLink
     */
    public void setFotoArtistaLink(String fotoArtistaLink) {
        this.fotoArtistaLink = fotoArtistaLink;
    }
}


