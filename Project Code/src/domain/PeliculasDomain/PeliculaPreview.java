package domain.PeliculasDomain;

public class PeliculaPreview {

    private String peliculaPosterLink;
    private String peliculaTitle;
    private String peliculaOverview;
    private String peliculaReleaseDate;
    private int peliculaId;

    public PeliculaPreview() {

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
     * @return int
     */
    public int getPeliculaId() {
        return peliculaId;
    }

    
    /** 
     * @param peliculaId
     */
    public void setPeliculaId(int peliculaId) {
        this.peliculaId = peliculaId;
    }

}
