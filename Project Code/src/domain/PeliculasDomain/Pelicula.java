package domain.PeliculasDomain;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

//Class which contains the info that we're gonna show through seriesTemplate.java 
//Not the preview

public class Pelicula {

    private String peliculaPosterLink;
    private ArrayList peliculaGenre;
    private String peliculaHomepageLink; 
    private String peliculaTitle; 
    private String peliculaOverview; 
    private String peliculaReleaseDate; 
    private double peliculaRating; 
    private double peliculaRunTime; 
    private Double peliculaId;
    private String peliculaWallpaperLink;
    

    public Pelicula() {
        // Now we read the json and assign it's properties to a map
        try {
            // create Gson instance
            Gson gson = new Gson();
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("resources/files/movieData/movieSelected.json"));
            // convert JSON file to map
            Map<String, ?> map = gson.fromJson(reader, Map.class);
            // print map entries
            reader.close();

            // Now our map has all of the movie storage, let's assign it to the Movie class
            this.setPeliculaTitle((String) map.get("original_title"));
            this.setPeliculaHomepageLink((String) map.get("homepage"));
            this.setPeliculaOverview((String) map.get("overview"));
            this.setPeliculaRating((double) map.get("vote_average"));
            this.setPeliculaReleaseDate((String) map.get("release_date"));
            this.setPeliculaRunTime((double) map.get("runtime"));
            this.setPeliculaGenre((ArrayList) map.get("genres"));
            this.setPeliculaWallpaperLink((String) map.get("backdrop_path"));
            this.setPeliculaPosterLink((String) map.get("poster_path"));
            this.setPeliculaId( (Double) map.get("id"));
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int getPeliculaId() {
        return peliculaId.intValue();
    }


    public void setPeliculaId(Double peliculaId) {
        this.peliculaId = peliculaId;
    }


    /** 
     * @return String
     */
    public String getPeliculaPosterLink() {
        return peliculaPosterLink;
    }

    
    /** 
     * @param peliculaPosterLink
     */
    public void setPeliculaPosterLink(String peliculaPosterLink) {
        this.peliculaPosterLink = peliculaPosterLink;
    }

    
    /** 
     * @return ArrayList
     */
    public ArrayList getPeliculaGenre() {
        return peliculaGenre;
    }

    
    /** 
     * @param peliculaGenre
     */
    public void setPeliculaGenre(ArrayList peliculaGenre) {
        this.peliculaGenre = peliculaGenre;
    }

    
    /** 
     * @return String
     */
    public String getPeliculaHomepageLink() {
        return peliculaHomepageLink;
    }

    
    /** 
     * @param peliculaHomepageLink
     */
    public void setPeliculaHomepageLink(String peliculaHomepageLink) {
        this.peliculaHomepageLink = peliculaHomepageLink;
    }

    
    /** 
     * @return String
     */
    public String getPeliculaTitle() {
        return peliculaTitle;
    }

    
    /** 
     * @param peliculaTitle
     */
    public void setPeliculaTitle(String peliculaTitle) {
        this.peliculaTitle = peliculaTitle;
    }

    
    /** 
     * @return String
     */
    public String getPeliculaOverview() {
        return peliculaOverview;
    }

    
    /** 
     * @param peliculaOverview
     */
    public void setPeliculaOverview(String peliculaOverview) {
        this.peliculaOverview = peliculaOverview;
    }

    
    /** 
     * @return String
     */
    public String getPeliculaReleaseDate() {
        return peliculaReleaseDate;
    }

    
    /** 
     * @param peliculaReleaseDate
     */
    public void setPeliculaReleaseDate(String peliculaReleaseDate) {
        this.peliculaReleaseDate = peliculaReleaseDate;
    }

    
    /** 
     * @return double
     */
    public double getPeliculaRating() {
        return peliculaRating;
    }

    
    /** 
     * @param peliculaRating
     */
    public void setPeliculaRating(double peliculaRating) {
        this.peliculaRating = peliculaRating;
    }

    
    /** 
     * @return double
     */
    public double getPeliculaRunTime() {
        return peliculaRunTime;
    }

    
    /** 
     * @param peliculaRunTime
     */
    public void setPeliculaRunTime(double peliculaRunTime) {
        this.peliculaRunTime = peliculaRunTime;
    }

    
    /** 
     * @return String
     */
    public String getPeliculaWallpaperLink() {
        return peliculaWallpaperLink;
    }

    
    /** 
     * @param peliculaWallpaperLink
     */
    public void setPeliculaWallpaperLink(String peliculaWallpaperLink) {
        this.peliculaWallpaperLink = peliculaWallpaperLink;
    }

}
