package ui.AppManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import domain.PeliculasDomain.Pelicula;
import domain.SeriesDomain.Serie;
import domain.UserDomain.UserFeatures;
import io.PeliculasIO.PeliculaDataFile;
import io.SeriesIO.SerieDataFile;
import io.UserIO.Register;

public class ProfileTemplate extends JFrame {

    // Declaracion de todos los componentes involucrados en la ventana
    private JPanel pnlNorte = new JPanel();
    private JPanel pnlCentro = new JPanel();
    private JPanel pnlSur = new JPanel();

    private JPanel pnlVisualInfo = new JPanel();
    private JPanel pnlTextInfo = new JPanel();
    private JPanel pnlInfo = new JPanel();
    private JPanel pnlButtons = new JPanel();
    private JPanel pnlMediaSummary = new JPanel();

    private JButton btnLogotype = new JButton("btnLogotype");
    private JButton btnAjustes = new JButton("Ajustes");

    private JLabel author = new JLabel("Hecho por Yago Tobio Souto - Universidad Pontificia de Comillas 2021");

    private String catalogo_disp[] = { "Catalogo", "Peliculas", "Series", "Artistas (Musica)", "Libros" };
    private JComboBox comboBoxCatalogo = new JComboBox<>(catalogo_disp);
    public Color bgColor = Color.decode("#171710");
    public Font fontType = new Font("Arial", Font.PLAIN, 20);

    public ProfileTemplate(Register userLogin) throws IOException{

        super("Content-Box");
        // Propiedades de la ventana
        pnlNorte.setLayout(new FlowLayout());
        pnlCentro.setLayout(new BorderLayout());
        pnlSur.setLayout(new FlowLayout());
        pnlVisualInfo.setLayout(new FlowLayout());
        pnlTextInfo.setLayout(new BorderLayout());
        pnlButtons.setLayout(new FlowLayout());
        pnlMediaSummary.setLayout(new FlowLayout());
        pnlInfo.setLayout(new BorderLayout());
        userLogin.setVisible(false);

        //Esto siempre va a ser el caso, ya que si el usuario no ha iniciado sesion no se puede entrar a la pestaña
        UserFeatures uf = userLogin.getLoggedInUser();

        try {
            fontType = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Roboto-Light.ttf"))
                    .deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Roboto-Light.ttf")));
        } catch (FontFormatException | IOException e) {
            System.out.println("Error importing Font");
        }

        // Contenido del panel norte

        Icon btnLogotype_icon = new ImageIcon("resources/images/LogotypesButtons/logo_1.png");
        btnLogotype.setPreferredSize(new DimensionUIResource(480, 79));
        btnLogotype.setBorderPainted(false);
        btnLogotype.setFocusPainted(false);
        btnLogotype.setHorizontalAlignment(SwingConstants.CENTER);
        btnLogotype.setForeground(Color.decode("#ffffff"));
        btnLogotype.setFont(fontType);
        btnLogotype.setIcon(btnLogotype_icon);
        btnLogotype.setBackground(bgColor);
        btnLogotype.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeScreen(userLogin);
                dispose();
            }
        });

        // Dropdown Box Catalogo
        comboBoxCatalogo.setBackground(bgColor);
        comboBoxCatalogo.setForeground(Color.decode("#ffffff"));
        comboBoxCatalogo.setFont(fontType);
        comboBoxCatalogo.setPreferredSize(new DimensionUIResource(150, 30));

        pnlNorte.add(btnLogotype);
        pnlNorte.add(Box.createHorizontalStrut(500));
        pnlNorte.add(Box.createHorizontalStrut(30));
        pnlNorte.add(comboBoxCatalogo);
        pnlNorte.setBackground(bgColor);

        // Contenido del Panel del centro
        //Last one, here we go
        //TODO ESTO VA ADENTRO DEL PANEL CENTRO 
        //Paso 1 -> Crea la parte de la izquierda -> Foto de Perfil + info de peliculas vistas con series
        //Paso 3 -> Esos 2 tabs van a tener otros 3 Tabs -> Vistos, Por Ver, Criticas
        //Paso 4 -> No va a haber informacion con la que interactuar, solo es para visualizar 

        //Comenzamos con el tocho que el el pnlUserFeed 
        JTabbedPane tp = new JTabbedPane();
        
        JPanel pnlUserFeedSerie = new JPanel(new BorderLayout()); //Panel con el tabbed Pane -> 1 
        JPanel pnlUserFeedPelicula = new JPanel(new BorderLayout()); //Panel con el tabbed Pane -> 2
        
        //Subnivel 
        JPanel pnlUserFeedPeliculaPorVerDisplay = new JPanel(new BorderLayout());
        JPanel pnlUserFeedPeliculaVistaDisplay = new JPanel(new BorderLayout());
        JPanel pnlUserFeedPeliculaCriticadaDisplay = new JPanel(new BorderLayout());

        JPanel pnlUserFeedSeriePorVerDisplay = new JPanel(new BorderLayout());
        JPanel pnlUserFeedSerieVistaDisplay = new JPanel(new BorderLayout());
        JPanel pnlUserFeedSerieCriticadaDisplay = new JPanel(new BorderLayout());

        //Tenemos que montar 6 ScrollPanes verticales en el caso de que tengamos mas peliculas de las que quepan en la pantalla
        JPanel pnlUserFeedPeliculaPorVer = new JPanel(new FlowLayout());
        JPanel pnlUserFeedPeliculaVista = new JPanel(new FlowLayout());
        JPanel pnlUserFeedPeliculaCriticada = new JPanel(new FlowLayout());

        JPanel pnlUserFeedSeriePorVer = new JPanel(new FlowLayout());
        JPanel pnlUserFeedSerieVista = new JPanel(new FlowLayout());
        JPanel pnlUserFeedSerieCriticada = new JPanel(new FlowLayout());

        //1.-> pnlUserFeedSeriePorVer
        for(Serie s: uf.getSeriesPorVer())
        {
            JPanel seriePreview = new JPanel(new BorderLayout());
            JButton serieTitle = new JButton(s.getserieTitle());
            //Solo vamos a incluir la imagen y el titulo
            //Cogemos el link para el thumbnail 
            StringBuilder sbSerieThumbnail = new StringBuilder("https://image.tmdb.org/t/p/w200");
            sbSerieThumbnail.append(s.getseriePosterLink());

            URL posterUrl;

            try {
                posterUrl = new URL(sbSerieThumbnail.toString());
                BufferedImage c = ImageIO.read(posterUrl);
                ImageIcon tstImagesmol = new ImageIcon(c);
    
                JLabel serieThumbnail = new JLabel("", tstImagesmol, JLabel.CENTER);
                serieThumbnail.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), tstImagesmol.getIconHeight()));

                seriePreview.add(serieThumbnail, BorderLayout.CENTER);
                seriePreview.add(serieTitle, BorderLayout.SOUTH);

                serieTitle.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent ae)
                    {
                        try {
                            //No entiendo porque nos da 0 
                            System.out.println(s.getserieId());

                            StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/tv/");
                            idLink.append(s.getserieId());
                            idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");
                            new SerieDataFile( idLink.toString());
                            new SerieTemplate(userLogin);
                            dispose();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });

              
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pnlUserFeedSeriePorVer.add(seriePreview);

        }

        //2.-> pnlUserFeedSerieVista 
        for(Serie s: uf.getSeriesVistas())
        {
            JPanel seriePreview = new JPanel(new BorderLayout());
            JButton serieTitle = new JButton(s.getserieTitle());
            //Solo vamos a incluir la imagen y el titulo
            //Cogemos el link para el thumbnail 
            StringBuilder sbSerieThumbnail = new StringBuilder("https://image.tmdb.org/t/p/w200");
            sbSerieThumbnail.append(s.getseriePosterLink());

            URL posterUrl;

            try {
                posterUrl = new URL(sbSerieThumbnail.toString());
                BufferedImage c = ImageIO.read(posterUrl);
                ImageIcon tstImagesmol = new ImageIcon(c);
    
                JLabel serieThumbnail = new JLabel("", tstImagesmol, JLabel.CENTER);
                serieThumbnail.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), tstImagesmol.getIconHeight()));

                seriePreview.add(serieThumbnail, BorderLayout.CENTER);
                seriePreview.add(serieTitle, BorderLayout.SOUTH);

                serieTitle.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent ae)
                    {
                        try {
                            //No entiendo porque nos da 0 
                            System.out.println(s.getserieId());

                            StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/tv/");
                            idLink.append(s.getserieId());
                            idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");
                            new SerieDataFile( idLink.toString());
                            new SerieTemplate(userLogin);
                            dispose();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });

              
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pnlUserFeedSerieVista.add(seriePreview);

        }

        //3.-> pnlUserFeedSerieCriticada 
        for(int serieId: uf.getSeriesCriticadas().keySet())
        {
            JPanel seriePreview = new JPanel(new BorderLayout());

            //Ahora basado en el serieId que tenemos, necesitamos obtener la serie 
            //Montamos el stringBuilder para el link, creamos un nuevoDataFile y leemos la serie 
            StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/tv/");
            idLink.append(serieId);
            idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");

            //Creamos el fichero con la serie y procedemos a asignarle esto a un objeto de serie
            new SerieDataFile(idLink.toString());
            Serie s = new Serie();


            JButton serieTitle = new JButton(s.getserieTitle());
            //Solo vamos a incluir la imagen y el titulo
            //Cogemos el link para el thumbnail 
            StringBuilder sbSerieThumbnail = new StringBuilder("https://image.tmdb.org/t/p/w200");
            sbSerieThumbnail.append(s.getseriePosterLink());

            URL posterUrl;

            try {
                posterUrl = new URL(sbSerieThumbnail.toString());
                BufferedImage c = ImageIO.read(posterUrl);
                ImageIcon tstImagesmol = new ImageIcon(c);
    
                JLabel serieThumbnail = new JLabel("", tstImagesmol, JLabel.CENTER);
                serieThumbnail.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), tstImagesmol.getIconHeight()));

                //TODO -> Meter logica para que muestre el TextArea Pls 
                serieTitle.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent ae)
                    {
                        try {
                            //No entiendo porque nos da 0 
                            System.out.println(s.getserieId());

                            StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/tv/");
                            idLink.append(s.getserieId());
                            idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");
                            new SerieDataFile( idLink.toString());
                            new SerieTemplate(userLogin);
                            dispose();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });


                seriePreview.add(serieThumbnail, BorderLayout.CENTER);
                seriePreview.add(serieTitle, BorderLayout.SOUTH);
              
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pnlUserFeedSerieCriticada.add(seriePreview);

        }

        //1.-> pnlUserFeedPeliculaPorVer
        for(Pelicula s: uf.getPeliculasPorVer())
        {
            JPanel peliculaPreview = new JPanel(new BorderLayout());
            JButton peliTitle = new JButton(s.getPeliculaTitle());
            //Solo vamos a incluir la imagen y el titulo
            //Cogemos el link para el thumbnail 
            StringBuilder sbPeliThumb = new StringBuilder("https://image.tmdb.org/t/p/w200");
            sbPeliThumb.append(s.getPeliculaPosterLink());

            URL posterUrl;

            try {
                posterUrl = new URL(sbPeliThumb.toString());
                BufferedImage c = ImageIO.read(posterUrl);
                ImageIcon tstImagesmol = new ImageIcon(c);
    
                JLabel peliThumbnail = new JLabel("", tstImagesmol, JLabel.CENTER);
                peliThumbnail.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), tstImagesmol.getIconHeight()));

                peliculaPreview.add(peliThumbnail, BorderLayout.CENTER);
                peliculaPreview.add(peliTitle, BorderLayout.SOUTH);

                peliTitle.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent ae)
                    {
                        try {
                            //No entiendo porque nos da 0 
                            System.out.println(s.getPeliculaId());

                            StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/movie/");
                            idLink.append(s.getPeliculaId());
                            idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");
                            new PeliculaDataFile( idLink.toString());
                            new PeliculaTemplate(userLogin);
                            dispose();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
              
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            pnlUserFeedPeliculaPorVer.add(peliculaPreview);

        }

        //2.-> pnlUserFeedPeliculaVista 
        for(Pelicula s: uf.getPeliculasVistas())
        {
            JPanel peliculaPreview = new JPanel(new BorderLayout());
            JButton peliTitle = new JButton(s.getPeliculaTitle());
            //Solo vamos a incluir la imagen y el titulo
            //Cogemos el link para el thumbnail 
            StringBuilder sbPeliThumb = new StringBuilder("https://image.tmdb.org/t/p/w200");
            sbPeliThumb.append(s.getPeliculaPosterLink());

            URL posterUrl;

            try {
                posterUrl = new URL(sbPeliThumb.toString());
                BufferedImage c = ImageIO.read(posterUrl);
                ImageIcon tstImagesmol = new ImageIcon(c);
    
                JLabel peliThumbnail = new JLabel("", tstImagesmol, JLabel.CENTER);
                peliThumbnail.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), tstImagesmol.getIconHeight()));

                peliculaPreview.add(peliThumbnail, BorderLayout.CENTER);
                peliculaPreview.add(peliTitle, BorderLayout.SOUTH);

                peliTitle.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent ae)
                    {
                        try {
                            //No entiendo porque nos da 0 
                            System.out.println(s.getPeliculaId());

                            StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/movie/");
                            idLink.append(s.getPeliculaId());
                            idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");
                            new PeliculaDataFile( idLink.toString());
                            new PeliculaTemplate(userLogin);
                            dispose();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });

              
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pnlUserFeedPeliculaVista.add(peliculaPreview);

        }

        //3.-> pnlUserFeedPeliculaCritica 
        for(int peliculaId: uf.getPeliculasCriticadas().keySet())
        {
            JPanel peliculaPreview = new JPanel(new BorderLayout());

            //Ahora basado en el serieId que tenemos, necesitamos obtener la serie 
            //Montamos el stringBuilder para el link, creamos un nuevoDataFile y leemos la serie 
            StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/movie/");
            idLink.append(peliculaId);
            idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");

            //Creamos el fichero con la serie y procedemos a asignarle esto a un objeto de serie
            try {
                new PeliculaDataFile(idLink.toString());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            Pelicula s = new Pelicula();

            JButton peliTitle = new JButton(s.getPeliculaTitle());
            //Solo vamos a incluir la imagen y el titulo
            //Cogemos el link para el thumbnail 
            StringBuilder sbPeliThumb = new StringBuilder("https://image.tmdb.org/t/p/w200");
            sbPeliThumb.append(s.getPeliculaPosterLink());

            URL posterUrl;

            try {
                posterUrl = new URL(sbPeliThumb.toString());
                BufferedImage c = ImageIO.read(posterUrl);
                ImageIcon tstImagesmol = new ImageIcon(c);
    
                JLabel peliThumbnail = new JLabel("", tstImagesmol, JLabel.CENTER);
                peliThumbnail.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), tstImagesmol.getIconHeight()));

                peliTitle.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent ae)
                    {
                        try {
                            //No entiendo porque nos da 0 
                            System.out.println(s.getPeliculaId());

                            StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/movie/");
                            idLink.append(s.getPeliculaId());
                            idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");
                            new PeliculaDataFile( idLink.toString());
                            new PeliculaTemplate(userLogin);
                            dispose();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });

                peliculaPreview.add(peliThumbnail, BorderLayout.CENTER);
                peliculaPreview.add(peliTitle, BorderLayout.SOUTH);
              
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pnlUserFeedPeliculaCriticada.add(peliculaPreview);

        }

        //Ahora debemos de añadir los Titulos a los paneles individuales para que el usuario entienda que fila es cual 
        JLabel lblPorVerPeliculas = new JLabel("Ver más tarde:");
        JLabel lblVistasPeliculas = new JLabel("Historial: ");
        JLabel lblCriticasPeliculas = new JLabel("Con Opiniones: ");
        JLabel lblPorVerSeries = new JLabel("Ver más tarde:");
        JLabel lblVistasSeries = new JLabel("Historial: ");
        JLabel lblCriticasSeries = new JLabel("Con Opiniones: ");


        lblPorVerPeliculas.setFont(fontType.deriveFont(25f));
        lblVistasPeliculas.setFont(fontType.deriveFont(25f));
        lblCriticasPeliculas.setFont(fontType.deriveFont(25f));
        lblPorVerSeries.setFont(fontType.deriveFont(25f));
        lblVistasSeries.setFont(fontType.deriveFont(25f));
        lblCriticasSeries.setFont(fontType.deriveFont(25f));

        pnlUserFeedSerieVistaDisplay.setBackground(bgColor);
        lblVistasSeries.setForeground(Color.decode("#ffffff"));
        pnlUserFeedSerieVistaDisplay.add(lblVistasSeries, BorderLayout.NORTH);

        pnlUserFeedSerieVista.setBackground(bgColor);
        pnlUserFeedSerieVista.setForeground(Color.decode("#ffffff"));
        pnlUserFeedSerieVistaDisplay.add(pnlUserFeedSerieVista, BorderLayout.CENTER);
        
        pnlUserFeedSeriePorVerDisplay.setBackground(bgColor);
        lblPorVerSeries.setForeground(Color.decode("#ffffff"));
        pnlUserFeedSeriePorVerDisplay.add(lblPorVerSeries, BorderLayout.NORTH);

        pnlUserFeedSeriePorVer.setBackground(bgColor);
        pnlUserFeedSeriePorVer.setForeground(Color.decode("#ffffff"));
        pnlUserFeedSeriePorVerDisplay.add(pnlUserFeedSeriePorVer, BorderLayout.CENTER);


        pnlUserFeedSerieCriticadaDisplay.setBackground(bgColor);
        lblCriticasSeries.setForeground(Color.decode("#ffffff"));
        pnlUserFeedSerieCriticadaDisplay.add(lblCriticasSeries, BorderLayout.NORTH);

        pnlUserFeedSerieCriticada.setBackground(bgColor);
        pnlUserFeedSerieCriticada.setForeground(Color.decode("#ffffff"));
        pnlUserFeedSerieCriticadaDisplay.add(pnlUserFeedSerieCriticada, BorderLayout.CENTER);

        pnlUserFeedPeliculaVistaDisplay.setBackground(bgColor);
        lblVistasPeliculas.setForeground(Color.decode("#ffffff"));
        pnlUserFeedPeliculaVistaDisplay.add(lblVistasPeliculas, BorderLayout.NORTH);
        
        pnlUserFeedPeliculaVista.setBackground(bgColor);
        pnlUserFeedPeliculaVista.setForeground(Color.decode("#ffffff"));
        pnlUserFeedPeliculaVistaDisplay.add(pnlUserFeedPeliculaVista, BorderLayout.CENTER);


        pnlUserFeedPeliculaPorVerDisplay.setBackground(bgColor);
        lblPorVerPeliculas.setForeground(Color.decode("#ffffff"));
        pnlUserFeedPeliculaPorVerDisplay.add(lblPorVerPeliculas, BorderLayout.NORTH);

        pnlUserFeedPeliculaPorVer.setBackground(bgColor);
        pnlUserFeedPeliculaPorVer.setForeground(Color.decode("#ffffff"));
        pnlUserFeedPeliculaPorVerDisplay.add(pnlUserFeedPeliculaPorVer, BorderLayout.CENTER);

        pnlUserFeedPeliculaCriticadaDisplay.setBackground(bgColor);
        lblCriticasPeliculas.setForeground(Color.decode("#ffffff"));
        pnlUserFeedPeliculaCriticadaDisplay.add(lblCriticasPeliculas, BorderLayout.NORTH);

        pnlUserFeedPeliculaCriticada.setBackground(bgColor);
        lblCriticasPeliculas.setForeground(Color.decode("#ffffff"));
        pnlUserFeedPeliculaCriticadaDisplay.add(pnlUserFeedPeliculaCriticada, BorderLayout.CENTER);
        

        JScrollPane scrollSerieVista = new JScrollPane(pnlUserFeedSerieVistaDisplay);
        scrollSerieVista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollSerieVista.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollSerieVista.setPreferredSize(new DimensionUIResource(pnlCentro.getWidth(), 390));
        pnlUserFeedSerie.add(scrollSerieVista, BorderLayout.NORTH);


        JScrollPane scrollSeriePorVer = new JScrollPane(pnlUserFeedSeriePorVerDisplay);
        scrollSeriePorVer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollSeriePorVer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollSeriePorVer.setPreferredSize(new DimensionUIResource(pnlCentro.getWidth(), 390));
        pnlUserFeedSerie.add(scrollSeriePorVer, BorderLayout.CENTER);
        
        JScrollPane scrollSerieCriticada = new JScrollPane(pnlUserFeedSerieCriticadaDisplay);
        scrollSerieCriticada.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollSerieCriticada.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollSerieCriticada.setPreferredSize(new DimensionUIResource(pnlCentro.getWidth(), 390));
        pnlUserFeedSerie.add(scrollSerieCriticada, BorderLayout.SOUTH);
        
        JScrollPane scrollPeliculaVista = new JScrollPane(pnlUserFeedPeliculaVistaDisplay);
        scrollPeliculaVista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPeliculaVista.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPeliculaVista.setPreferredSize(new DimensionUIResource(pnlCentro.getWidth(), 390));
        pnlUserFeedPelicula.add(scrollPeliculaVista, BorderLayout.NORTH);

        JScrollPane scrollPeliculaPorVer = new JScrollPane(pnlUserFeedPeliculaPorVerDisplay);
        scrollPeliculaPorVer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPeliculaPorVer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPeliculaPorVer.setPreferredSize(new DimensionUIResource(pnlCentro.getWidth(), 390));
        pnlUserFeedPelicula.add(scrollPeliculaPorVer, BorderLayout.CENTER);

        JScrollPane scrollPeliculaCriticada = new JScrollPane(pnlUserFeedPeliculaCriticadaDisplay);
        scrollPeliculaCriticada.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPeliculaCriticada.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPeliculaCriticada.setPreferredSize(new DimensionUIResource(pnlCentro.getWidth(), 390));
        pnlUserFeedPelicula.add(scrollPeliculaCriticada, BorderLayout.SOUTH);

        JScrollPane scrollSerie = new JScrollPane(pnlUserFeedSerie);
        scrollSerie.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollSerie.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JScrollPane scrollPelicula = new JScrollPane(pnlUserFeedPelicula);
        scrollPelicula.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPelicula.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        tp.add("Series", scrollSerie);
        tp.add("Peliculas", scrollPelicula);
        tp.setBorder(null);
        tp.setBackground(Color.LIGHT_GRAY);
        tp.setForeground(new Color(255,255,255));
        tp.setFont(fontType.deriveFont(40f));
        pnlCentro.add(tp, BorderLayout.CENTER);

        // Contenido del panel del sur
        author.setFont(new Font("Roboto", Font.PLAIN, 20));
        author.setForeground(Color.decode("#ffffff"));
        pnlSur.add(author);
        pnlSur.add(Box.createHorizontalStrut(1000));
        pnlSur.add(btnAjustes);
        pnlSur.setBackground(bgColor);

        // Incorporamos toda la ventana
        this.add(pnlNorte, BorderLayout.NORTH);
        this.add(pnlCentro, BorderLayout.CENTER);
        this.add(pnlSur, BorderLayout.SOUTH);

        // Caracteristicas de la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setPreferredSize(new DimensionUIResource(1800, 1013));
        this.setSize(1800, 1013);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}