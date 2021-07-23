package domain.SeriesDomain;

public class SeriePreview {
    
    private String seriePosterLink;
    private String serieTitle;
    private String serieOverview;
    private String serieReleaseDate;
    private int serieId;

    public SeriePreview()
    {

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
     * Devuelve los resumenes encontrados en la API al pasarselos
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
     * @return int
     */
    public int getserieId() {
        return serieId;
    }

    
    /** 
     * @param serieId
     */
    public void setserieId(int serieId) {
        this.serieId = serieId;
    }

}
