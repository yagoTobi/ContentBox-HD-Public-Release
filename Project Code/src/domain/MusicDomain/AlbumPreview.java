package domain.MusicDomain;

public class AlbumPreview {


    private String albumCoverLink; 
    private String albumTitle;
    private String releaseData; 
    private String albumId;

    public AlbumPreview()
    {
        
    }
    
    
    /** 
     * @return String
     */
    public String getAlbumCoverLink() {
        return albumCoverLink;
    }
    
    /** 
     * @param albumCoverLink
     */
    public void setAlbumCoverLink(String albumCoverLink) {
        this.albumCoverLink = albumCoverLink;
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
    public String getReleaseData() {
        return releaseData;
    }
    
    /** 
     * @param releaseData
     */
    public void setReleaseData(String releaseData) {
        this.releaseData = releaseData;
    }

    
    /** 
     * @return String
     */
    public String getAlbumId() {
        return albumId;
    }
    
    /** 
     * @param albumId
     */
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    } 

}
