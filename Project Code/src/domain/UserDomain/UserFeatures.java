package domain.UserDomain;

import java.util.ArrayList;
import java.util.HashMap;

import domain.PeliculasDomain.Pelicula;
import domain.SeriesDomain.Serie;

//Archivo que nos va a indicar las cosas como si el usuario esta metido, o cual esta metido etc...
//Este es el objeto que le vamos a pasar al escritor de objetos para nuestro archivo 
public class UserFeatures extends User {
    
    //Cuando le demos al boton verde <- Añadir a la pestaña de ver mas tarde 
    //Cuando le demos al boton marron <- Ya vistas 
    //Cuando le demos al boton amarillo <- Guardar el JTextArea 

    //Debemos de implementar el contador de peliculas y series con su nombre y la imagen de perfil 
    private ArrayList<Pelicula> peliculasVistas = new ArrayList<>();
    private ArrayList<Pelicula> peliculasPorVer = new ArrayList<>();

    private ArrayList<Serie> seriesVistas = new ArrayList<>();
    private ArrayList<Serie> seriesPorVer = new ArrayList<>();

    //Esto no nos funciona, cuando volvemos a iniciar sesión, ya que apunta a un objeto que 
    //se destroza al cerrar el uso de la ventana. Por lo tanto nos tenemos que hacer un apaño 
    //Vamos a intentar hacer este apaño pasando por referencia el ID Link 
    private HashMap<Integer, String> seriesCriticadas = new HashMap<>();
    private HashMap<Integer, String> peliculasCriticadas = new HashMap<>();

    //Como apaño vamos a usar los ArrayList ya que respetan el orden de insercion;
    
    public UserFeatures(String usuario, String clave)
    {
        super(usuario,clave);
    }
    
    /** 
     * @param peliculas
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getIdPeliculas(ArrayList<Pelicula> peliculas)
    {
         ArrayList<Integer> contentId = new ArrayList<>();

         for(Pelicula p:peliculas)
         {
             contentId.add(p.getPeliculaId());
         }

         return contentId;

    }

    
    /** 
     * @param peliculas
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getIdPeliculasMap(HashMap<Pelicula, String> peliculas)
    {
        ArrayList<Integer> contentId = new ArrayList<>();

        for(Pelicula p:peliculas.keySet())
        {
            contentId.add(p.getPeliculaId());
        }

        return contentId;

    }

    
    /** 
     * @param series
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getIdSeriesMap(HashMap<Serie, String> series)
    {
        ArrayList<Integer> contentId = new ArrayList<>();

        for(Serie s:series.keySet())
        {
            contentId.add(s.getserieId());
        }

        return contentId;
    }

    
    /** 
     * @param series
     * @return ArrayList<Integer>
     * 
     * Visto a que todos los objetos que metemos en el archivo van a ser objetos nuevos, los metodos
     * para quitar las series no funcionarian, por lo tanto lo basamos en los Id's
     *
     */
    public ArrayList<Integer> getIdSeries(ArrayList<Serie> series)
    {
         ArrayList<Integer> contentId = new ArrayList<>();

         for(Serie s:series)
         {
             contentId.add(s.getserieId());
         }
         return contentId;
    }


    
    /** 
     * @return ArrayList<Pelicula>
     */
    public ArrayList<Pelicula> getPeliculasVistas() {
        return peliculasVistas;
    }

    
    /** 
     * @return ArrayList<Pelicula>
     */
    public ArrayList<Pelicula> getPeliculasPorVer() {
        return peliculasPorVer;
    }


    
    /** 
     * @return HashMap<Pelicula, String>
     */
    public HashMap<Integer, String> getPeliculasCriticadas() {
        return peliculasCriticadas;
    }

    
    /** 
     * @return ArrayList<Serie>
     */
    public ArrayList<Serie> getSeriesVistas() {
        return seriesVistas;
    }

    
    /** 
     * @return ArrayList<Serie>
     */
    public ArrayList<Serie> getSeriesPorVer() {
        return seriesPorVer;
    }

    
    /** 
     * @return HashMap<Serie, String>
     */
    public HashMap<Integer, String> getSeriesCriticadas() {
        return seriesCriticadas;
    }

    
    /** 
     * @param s
     */
    public void addSerieVista(Serie s)
    {
        seriesVistas.add(s);
    }

    
    /** 
     * @param id
     */
    public void removeSerieVista(int id)
    {
        //serie Id 
        for(Serie s:seriesVistas)
        {
            if(s.getserieId() == id)
            {
                seriesVistas.remove(s);
            }
        }
    }


    
    /** 
     * @param s
     */
    public void addSeriePorVer(Serie s)
    {
        seriesPorVer.add(s);
    }

    
    /** 
     * @param id
     */
    public void removeSeriesPorVer(int id)
    {
        //serie Id 
        for(Serie s:seriesPorVer)
        {
            if(s.getserieId() == id)
            {
                seriesVistas.remove(s);
            }
        }
    }


    
    /** 
     * @param s
     * @param critica
     */
    public void addSerieCriticada(int idSerie, String critica)
    {
        seriesCriticadas.put(idSerie,critica);
    }

    
    /** 
     * @param id
     */
    public void removeSeriesCriticada(int id)
    {
       
        for(Integer idLinks:seriesCriticadas.keySet())
        {
            if(idLinks == id)
            {
                seriesCriticadas.remove(id);
            }
            
        }
    }


    
    /** 
     * @param p
     */
    public void addPeliculaVista(Pelicula p)
    {
        peliculasVistas.add(p);
    }

    
    /** 
     * @param id
     */
    public void removePeliculaVista(int id)
    {
        //serie Id 
        for(Pelicula p:peliculasVistas)
        {
            if(p.getPeliculaId() == id)
            {
                peliculasVistas.remove(p);
            }
        }
    }

    
    /** 
     * @param p
     */
    public void addPeliculasPorVer(Pelicula p)
    {
        peliculasPorVer.add(p);
    }

    
    /** 
     * @param id
     */
    public void removePeliculasPorVer(int id)
    {
        
        //serie Id 
        for(Pelicula p:peliculasPorVer)
        {
            if(p.getPeliculaId() == id)
            {
                peliculasPorVer.remove(p);
            }
        }
    }


    
    /** 
     * @param p
     * @param critica
     */
    public void addPeliculasCriticadas(Integer peliculaId, String critica)
    {
        peliculasCriticadas.put(peliculaId,critica);
    }

    
    /** 
     * @param id
     */
    public void removePeliculasCriticadas(int id)
    {

        for(Integer idLinks:peliculasCriticadas.keySet())
        {
            if(idLinks == id)
            {
                peliculasCriticadas.remove(id);
            }
        }
    }

}
