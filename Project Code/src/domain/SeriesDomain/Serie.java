package domain.SeriesDomain;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

//Class which contains the info that we're gonna show through seriesTemplate.java 
//Not the preview

public class Serie {

    private String seriePosterLink;
    private ArrayList serieGenero;
    private String serieHomePageLink; //
    private String serieTitle; //
    private String serieOverview; //
    private String serieReleaseDate; //
    private double serieRating; //
    private Double serieId;
    private ArrayList serieRuntime; //
    private String serieWallpaperLink;

    public Serie() {
        // Now we read the json and assign it's properties to a map
        try {
            // create Gson instance
            Gson gson = new Gson();
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("resources/files/seriesData/serieSelected.json"));
            // convert JSON file to map
            Map<String, ?> map = gson.fromJson(reader, Map.class);
            reader.close();


            // Now our map has all of the movie storage, let's assign it to the Movie class
            this.setserieTitle((String) map.get("original_name"));
            this.setserieHomePageLink((String) map.get("homepage"));
            this.setserieOverview((String) map.get("overview"));
            this.setserieRating((double) map.get("vote_average"));
            this.setserieReleaseDate((String) map.get("release_date"));
            this.setserieRuntime((ArrayList) map.get("episode_run_time"));
            this.setserieGenero((ArrayList) map.get("genres"));
            
            this.setserieWallpaperLink((String) map.get("backdrop_path"));
            this.setseriePosterLink((String) map.get("poster_path"));
            this.setserieId((Double) map.get("id"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getserieId() {
        return serieId.intValue();
    }


    public void setserieId(Double serieId) {
        this.serieId = serieId;
    }


    
    /** 
     * @return String
     */
    public String getseriePosterLink() {
        return seriePosterLink;
    }

    
    /** 
     * @param seriePosterLink
     */
    public void setseriePosterLink(String seriePosterLink) {
        this.seriePosterLink = seriePosterLink;
    }

    
    /** 
     * @return ArrayList
     */
    public ArrayList getserieGenero() {
        return serieGenero;
    }

    
    /** 
     * @param serieGenero
     */
    public void setserieGenero(ArrayList serieGenero) {
        this.serieGenero = serieGenero;
    }

    
    /** 
     * @return String
     */
    public String getserieHomePageLink() {
        return serieHomePageLink;
    }

    
    /** 
     * @param serieHomePageLink
     */
    public void setserieHomePageLink(String serieHomePageLink) {
        this.serieHomePageLink = serieHomePageLink;
    }

    
    /** 
     * @return String
     */
    public String getserieTitle() {
        return serieTitle;
    }

    
    /** 
     * @param serieTitle
     */
    public void setserieTitle(String serieTitle) {
        this.serieTitle = serieTitle;
    }

    
    /** 
     * @return String
     */
    public String getserieOverview() {
        return serieOverview;
    }

    
    /** 
     * @param serieOverview
     */
    public void setserieOverview(String serieOverview) {
        this.serieOverview = serieOverview;
    }

    
    /** 
     * @return String
     */
    public String getserieReleaseDate() {
        return serieReleaseDate;
    }

    
    /** 
     * @param serieReleaseDate
     */
    public void setserieReleaseDate(String serieReleaseDate) {
        this.serieReleaseDate = serieReleaseDate;
    }

    
    /** 
     * @return double
     */
    public double getserieRating() {
        return serieRating;
    }

    
    /** 
     * @param serieRating
     */
    public void setserieRating(double serieRating) {
        this.serieRating = serieRating;
    }

    
    /** 
     * @return ArrayList
     */
    public ArrayList getserieRuntime() {
        return serieRuntime;
    }

    
    /** 
     * @param arrayCheck
     */
    public void setserieRuntime(ArrayList arrayCheck) {
        this.serieRuntime = arrayCheck;
    }

    
    /** 
     * @return String
     */
    public String getserieWallpaperLink() {
        return serieWallpaperLink;
    }

    
    /** 
     * @param serieWallpaperLink
     */
    public void setserieWallpaperLink(String serieWallpaperLink) {
        this.serieWallpaperLink = serieWallpaperLink;
    }

}
