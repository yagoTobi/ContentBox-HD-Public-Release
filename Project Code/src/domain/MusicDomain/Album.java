package domain.MusicDomain;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;



public class Album {
    
    private String albumTitle;
    private String artistName;
    private String albumCoverDisplayLink;
    private String releaseYear;
    private String albumLink;
    private ArrayList<Cancion> cancionesAlbum = new ArrayList<Cancion>();

    public Album()
    {
         //Now we read the json and assign it's properties to a map 
         try {
            // create Gson instance
            Gson gson = new Gson();
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("resources/files/musicData/albumSelected.json"));
            // convert JSON file to map
            Map<String, ?> map = gson.fromJson(reader, Map.class);

            reader.close();

            // Now our map has all of the movie storage, let's assign it to the Movie class
            //TODO -> EDIT THIS
            this.setAlbumTitle((String) map.get("album_title"));
            this.setArtistName((String) map.get("artist"));
            this.setAlbumCoverDisplayLink((String) map.get("albumCoverLink"));
            this.setAlbumLink((String) map.get("albumLink"));
            this.setCancionesAlbum((ArrayList) map.get("songsAlbum"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    
    
    /** 
     * @return String
     */
    public String getAlbumTitle() {
        return albumTitle;
    }
    
    /** 
     * @param albumTitle
     */
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
    
    /** 
     * @return String
     */
    public String getArtistName() {
        return artistName;
    }
    
    /** 
     * @param artistName
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    
    /** 
     * @return String
     */
    public String getAlbumCoverDisplayLink() {
        return albumCoverDisplayLink;
    }
    
    /** 
     * @param albumCoverDisplayLink
     */
    public void setAlbumCoverDisplayLink(String albumCoverDisplayLink) {
        this.albumCoverDisplayLink = albumCoverDisplayLink;
    }
    
    /** 
     * @return String
     */
    public String getReleaseYear() {
        return releaseYear;
    }
    
    /** 
     * @param releaseYear
     */
    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }
    
    /** 
     * @return String
     */
    public String getAlbumLink() {
        return albumLink;
    }
    
    /** 
     * @param albumLink
     */
    public void setAlbumLink(String albumLink) {
        this.albumLink = albumLink;
    }
    
    /** 
     * @return ArrayList<Cancion>
     */
    public ArrayList<Cancion> getCancionesAlbum() {
        return cancionesAlbum;
    }
    
    /** 
     * @param cancionesAlbum
     */
    public void setCancionesAlbum(ArrayList<Cancion> cancionesAlbum) {
        this.cancionesAlbum = cancionesAlbum;
    }


    




}
