package domain.UserDomain;

public class User {

    //Ambos valen null por definicion, ya que al comenzar esto, el usuario no ha iniciado sesion 
    private String usuario = null;
    private String clave = null;

    public User()
    {

    }

    public User(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }


    @Override 
    public String toString()
    {
        return usuario + clave;
    }
    
    /** 
     * @return String
     */
    public String getUsuario() {
        return usuario;
    }

    
    /** 
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    /** 
     * @return String
     */
    public String getClave() {
        return clave;
    }

    
    /** 
     * @param clave
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

}