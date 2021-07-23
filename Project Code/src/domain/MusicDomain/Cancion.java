package domain.MusicDomain;

public class Cancion {
    
    private String nombreCancion;
    private String songNumber; 
    private String nombreArtista;
    private String mvLink;

    
    
    /** 
     * @return String
     * Devuelve el nombre de la Cancion de los albumes al iterar por el archivo 
     */
    public String getNombreCancion() {
        return nombreCancion;
    }
    
    /** 
     * @param nombreCancion
     * Lee el archivo del album seleccionado y hace el setter apropiado
     */
    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }
    
    /** 
     * @return String
     */
    public String getSongNumber() {
        return songNumber;
    }
    
    /** 
     * @param songNumber
     */
    public void setSongNumber(String songNumber) {
        this.songNumber = songNumber;
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
    public String getMvLink() {
        return mvLink;
    }
    
    /** 
     * @param mvLink
     */
    public void setMvLink(String mvLink) {
        this.mvLink = mvLink;
    }

}
