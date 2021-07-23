package ui.AppManager;
//Search results window

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import domain.PeliculasDomain.PeliculaPreview;
import domain.SeriesDomain.SeriePreview;
import io.PeliculasIO.PeliculaDataFile;
import io.SeriesIO.SerieDataFile;
import io.UserIO.Register;

public class SearchResults extends JFrame {

    // Declaracion de todos los componentes involucrados en la ventana
    private JPanel pnlNorte = new JPanel();
    private JPanel pnlCentro = new JPanel();
    private JPanel pnlSur = new JPanel();

    private JFrame warningSearch = new JFrame();

    private JPanel pnlSearchDisplay = new JPanel();

    private JButton btnLogotype = new JButton();
    private JButton btnCrearCuenta = new JButton("Crear Cuenta/Iniciar Sesion");
    private JButton btnAjustes = new JButton("Ajustes");

    private JLabel author = new JLabel("Hecho por Yago Tobio Souto - Universidad Pontificia de Comillas 2021");
    private String catalogo_disp[] = { "Catalogo", "Peliculas", "Series", "Artistas (Musica)", "Libros" };
    private JComboBox comboBoxCatalogo = new JComboBox<>(catalogo_disp);

    public Color bgColor = Color.decode("#171710");

    public SearchResults(String searchType, Register usuarioLogIn) {

        super("Search Results");
        // Propiedades de la ventana
        pnlNorte.setLayout(new FlowLayout());
        pnlCentro.setLayout(new FlowLayout());
        pnlSur.setLayout(new FlowLayout());
        pnlSearchDisplay.setLayout(new FlowLayout());

        Font font_type = new Font("Arial", Font.PLAIN, 20);
        try {
            font_type = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Roboto-Light.ttf"))
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
        btnLogotype.setFont(font_type);
        btnLogotype.setIcon(btnLogotype_icon);
        btnLogotype.setBackground(bgColor);
        btnLogotype.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeScreen(usuarioLogIn);
            }
        });
        // btnLogotype.setRolloverIcon(btnLogotype_clicked);

        // Boton crear cuenta/Inicio Sesion
        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setBackground(bgColor);
        btnCrearCuenta.setForeground(Color.decode("#ffffff"));
        btnCrearCuenta.setFont(font_type);
        btnCrearCuenta.setPreferredSize(new DimensionUIResource(270, 50));
        btnCrearCuenta.setFocusPainted(false);
        // Behaviour boton cuenta
        if(usuarioLogIn.getLoggedInUser() != null)
        {
            System.out.println("User is not null");
            System.out.println(usuarioLogIn.getLoggedInUser().toString());
            btnCrearCuenta.setText("Mi Perfil");
            btnCrearCuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If we compile individual files, since this is a JFrame of it's own,
                    // compilation no work no good
                    try {
                        new ProfileTemplate(usuarioLogIn);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    dispose();
                }
            });
        }else
        {
            System.out.println(usuarioLogIn.getLoggedInUser());
            btnCrearCuenta.setText("Crear Cuenta/Iniciar Sesion");
            // Behaviour boton cuenta
            btnCrearCuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If we compile individual files, since this is a JFrame of it's own,
                    // compilation no work no good
                    usuarioLogIn.setVisible(true);
                    dispose();
                }
            });
        }
        // btnCrearCuenta.setBackground(bgColor);

        // Dropdown Box Catalogo
        comboBoxCatalogo.setBackground(bgColor);
        comboBoxCatalogo.setForeground(Color.decode("#ffffff"));
        comboBoxCatalogo.setFont(font_type);
        comboBoxCatalogo.setPreferredSize(new DimensionUIResource(150, 30));

        pnlNorte.add(btnLogotype);
        pnlNorte.add(Box.createHorizontalStrut(500));
        pnlNorte.add(btnCrearCuenta);
        pnlNorte.add(Box.createHorizontalStrut(30));
        pnlNorte.add(comboBoxCatalogo);
        pnlNorte.setBackground(bgColor);

        // Contenido del panel centro

        // Load in the searchResultFile and pass it as a JSONObject
        // The following block serves to fill in the arrayList
        ArrayList<PeliculaPreview> peliculasEncontradas = new ArrayList<PeliculaPreview>();
        ArrayList<SeriePreview> seriesEncontradas = new ArrayList<SeriePreview>();
        boolean emptyFile = false;
        // Debemos modificar esto basado en la lista que tenemos seleccionada en las
        // colecciones al buscar
        // Debemos inicializar searchResults
        if (searchType.equals("Peliculas")) {
            File searchResults = new File("resources/files/movieData/movieSearch.json");
            // Bloque que nos rellena el ArrayList basado en el documento generado por la
            // API
            if (searchResults.exists()) {
                try {
                    // Leemos el archivo
                    InputStream is = new FileInputStream("resources/files/movieData/movieSearch.json");
                    // Pasamos el archivo a texto
                    String searchJsonText = IOUtils.toString(is, "UTF-8");
                    // Este texto lo convertimos a un JSONOBject
                    JSONObject outerObject = new JSONObject(searchJsonText);
                    // Ahora queremos iterar por la clave del archivo que se llama Results
                    JSONArray resultArray = outerObject.getJSONArray("results");

                    if (resultArray.length() > 0) {

                        for (int i = 0; i < resultArray.length(); i++) {
                            // Vamos a cargar una peliculaPreview por cada iteracion
                            PeliculaPreview peliculaPreview = new PeliculaPreview();

                            JSONObject objectinArray = resultArray.getJSONObject(i);
                            // Cargamos el array en texto de los elementos que compone results[]
                            String[] elementNames = JSONObject.getNames(objectinArray);

                            for (String elementName : elementNames) {
                                try {
                                    if (elementName.equals("overview")) {
                                        peliculaPreview.setPeliculaOverview(objectinArray.getString(elementName));
                                    } else if (elementName.equals("title")) {
                                        peliculaPreview.setPeliculaTitle(objectinArray.getString(elementName));
                                    } else if (elementName.equals("poster_path")) {
                                        peliculaPreview.setPeliculaPosterLink(objectinArray.getString(elementName));
                                    } else if (elementName.equals("release_date")) {
                                        peliculaPreview.setPeliculaReleaseDate(objectinArray.getString(elementName));
                                    } else if (elementName.equals("id")) {
                                        peliculaPreview.setPeliculaId(objectinArray.getInt(elementName));
                                    }

                                } catch (JSONException ex) {
                                    System.out.print("");
                                }
                                ;
                            }
                            
                            peliculasEncontradas.add(peliculaPreview);
                        }

                    } else {
                        JOptionPane.showMessageDialog(warningSearch, "Pelicula no encontrada, vuelva a buscar", "Alert",
                                JOptionPane.WARNING_MESSAGE);
                        emptyFile = true;
                        this.dispose();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            // 1400 * 800
            if (emptyFile == false) {
                pnlSearchDisplay.setPreferredSize(new Dimension(1400, 300 * (peliculasEncontradas.size() - 2)));
                // Funcion donde vamos a ir añadiendo dinamicamente a la Scroll panel unos
                // JPanels con un tamaño fijo donde tendremos el formato fijo para cada peli
                for (int j = 0; j < peliculasEncontradas.size(); j++) {
                    JPanel previewPanel = new JPanel();
                    JPanel infoPanel = new JPanel();
                    previewPanel.setLayout(new BorderLayout());
                    infoPanel.setLayout(new BorderLayout());

                    JButton peliculasTitle = new JButton();
                    JTextArea peliculaOverview = new JTextArea();

                    peliculasTitle.setFont(font_type.deriveFont(25f));
                    peliculasTitle.setForeground(Color.decode("#ffffff"));
                    peliculasTitle.setBackground(bgColor);
                    peliculasTitle.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent me) {
                            peliculasTitle.setForeground(Color.decode("#f5c20c"));
                        }

                        public void mouseExited(MouseEvent me) {
                            peliculasTitle.setForeground(Color.decode("#ffffff"));
                        }
                    });

                    int idFilm = peliculasEncontradas.get(j).getPeliculaId();

                    StringBuilder titleInfo = new StringBuilder(peliculasEncontradas.get(j).getPeliculaTitle());
                    titleInfo.append(" ( ");
                    titleInfo.append(peliculasEncontradas.get(j).getPeliculaReleaseDate());
                    titleInfo.append(" )");

                    StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/movie/");
                    idLink.append(idFilm);
                    idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");

                    peliculasTitle.setText(titleInfo.toString());
                    peliculasTitle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                new PeliculaDataFile(idLink.toString());
                                new PeliculaTemplate(usuarioLogIn);
                                dispose();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                    peliculaOverview.setLineWrap(true);
                    peliculaOverview.setBackground(bgColor);
                    peliculaOverview.setFont(font_type.deriveFont(20f));
                    peliculaOverview.setSize(985, 210);
                    peliculaOverview.setForeground(Color.decode("#ffffff"));

                    StringBuilder posterLink = new StringBuilder("https://image.tmdb.org/t/p/w185");
                    posterLink.append(peliculasEncontradas.get(j).getPeliculaPosterLink());
                    String peliculaPosterLink = posterLink.toString();

                    URL posterUrl;
                    try {
                        posterUrl = new URL(peliculaPosterLink);
                        BufferedImage c = ImageIO.read(posterUrl);
                        ImageIcon imgPreview = new ImageIcon(c);

                        // Build the image icons
                        JLabel imagenPoster = new JLabel("");
                        imagenPoster.setIcon(imgPreview);
                        imagenPoster.setPreferredSize(new DimensionUIResource(250, 300));

                        peliculaOverview.setText(peliculasEncontradas.get(j).getPeliculaOverview());

                        infoPanel.add(peliculasTitle, BorderLayout.NORTH);
                        infoPanel.add(peliculaOverview, BorderLayout.CENTER);
                        infoPanel.setBackground(bgColor);
                        // infoPanel.add(seriesReleaseDate, BorderLayout.NORTH);

                        previewPanel.add(imagenPoster, BorderLayout.WEST);
                        previewPanel.add(infoPanel, BorderLayout.CENTER);
                        previewPanel.setBackground(bgColor);
                        previewPanel.setPreferredSize(new DimensionUIResource(1400, 300));
                        // previewPanel.setBackground(bgColor);
                        pnlSearchDisplay.add(previewPanel);

                    } catch (Exception e1) {
                        System.out.println("Unable to fetch Images from defined URL at BufferedImage");
                    }
                }
            } else {
                this.dispose();
                new HomeScreen(usuarioLogIn);
            }
        } else if (searchType.equals("Series")) {
            File searchResults = new File("resources/files/seriesData/serieSearch.json");

            if (searchResults.exists()) {
                try {
                    // Leemos el archivo
                    InputStream is = new FileInputStream("resources/files/seriesData/serieSearch.json");
                    // Pasamos el archivo a texto
                    String searchJsonText = IOUtils.toString(is, "UTF-8");
                    // Este texto lo convertimos a un JSONOBject
                    JSONObject outerObject = new JSONObject(searchJsonText);
                    // Ahora queremos iterar por la clave del archivo que se llama Results
                    JSONArray resultArray = outerObject.getJSONArray("results");

                    if (resultArray.length() > 0) {
                        for (int i = 0; i < resultArray.length(); i++) {
                            // Vamos a cargar una seriePreview por cada iteracion
                            SeriePreview seriePreview = new SeriePreview();

                            JSONObject objectinArray = resultArray.getJSONObject(i);
                            // Cargamos el array en texto de los elementos que compone results[]
                            String[] elementNames = JSONObject.getNames(objectinArray);

                            for (String elementName : elementNames) {
                                try {
                                    if (elementName.equals("overview")) {
                                        seriePreview.setserieOverview(objectinArray.getString(elementName));
                                    } else if (elementName.equals("name")) {
                                        seriePreview.setserieTitle(objectinArray.getString(elementName));
                                    } else if (elementName.equals("poster_path")) {
                                        seriePreview.setseriePosterLink(objectinArray.getString(elementName));
                                    } else if (elementName.equals("first_air_date")) {
                                        seriePreview.setserieReleaseDate(objectinArray.getString(elementName));
                                    } else if (elementName.equals("id")) {
                                        seriePreview.setserieId(objectinArray.getInt(elementName));
                                    }

                                } catch (JSONException ex) {
                                    System.out.print("");
                                }
                            }
                            seriesEncontradas.add(seriePreview);
                        }

                    } else {
                        JOptionPane.showMessageDialog(warningSearch, "Serie no encontrada, vuelva a buscar", "Alert",
                                JOptionPane.WARNING_MESSAGE);
                        emptyFile = true;
                        this.dispose();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (emptyFile == false) {
                // 1400 * 800
                pnlSearchDisplay.setPreferredSize(new Dimension(1400, 300 * (seriesEncontradas.size() - 2)));
                // Funcion donde vamos a ir añadiendo dinamicamente a la Scroll panel unos
                // JPanels con un tamaño fijo donde tendremos el formato fijo para cada peli
                for (int j = 0; j < seriesEncontradas.size(); j++) {
                    JPanel previewPanel = new JPanel();
                    JPanel infoPanel = new JPanel();
                    previewPanel.setLayout(new BorderLayout());
                    infoPanel.setLayout(new BorderLayout());

                    JButton seriesTitle = new JButton();
                    JTextArea serieOverview = new JTextArea();

                    seriesTitle.setFont(font_type.deriveFont(25f));
                    seriesTitle.setForeground(Color.decode("#ffffff"));
                    seriesTitle.setBackground(bgColor);
                    seriesTitle.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent me) {
                            seriesTitle.setForeground(Color.decode("#f5c20c"));
                        }

                        public void mouseExited(MouseEvent me) {
                            seriesTitle.setForeground(Color.decode("#ffffff"));
                        }
                    });

                    int serieId = seriesEncontradas.get(j).getserieId();

                    StringBuilder titleInfo = new StringBuilder(seriesEncontradas.get(j).getserieTitle());
                    titleInfo.append(" ( ");
                    titleInfo.append(seriesEncontradas.get(j).getserieReleaseDate());
                    titleInfo.append(" )");

                    StringBuilder idLink = new StringBuilder("https://api.themoviedb.org/3/tv/");
                    idLink.append(serieId);
                    idLink.append("?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US");

                    seriesTitle.setText(titleInfo.toString());
                    seriesTitle.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                new SerieDataFile(idLink.toString());
                                new SerieTemplate(usuarioLogIn);
                                dispose();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                    serieOverview.setLineWrap(true);
                    serieOverview.setBackground(bgColor);
                    serieOverview.setFont(font_type.deriveFont(20f));
                    serieOverview.setSize(985, 210);
                    serieOverview.setForeground(Color.decode("#ffffff"));

                    StringBuilder posterLink = new StringBuilder("https://image.tmdb.org/t/p/w185");
                    posterLink.append(seriesEncontradas.get(j).getseriePosterLink());
                    String peliculaPosterLink = posterLink.toString();

                    URL posterUrl;
                    try {
                        posterUrl = new URL(peliculaPosterLink);
                        BufferedImage c = ImageIO.read(posterUrl);
                        ImageIcon imgPreview = new ImageIcon(c);

                        // Build the image icons
                        JLabel imagenPoster = new JLabel("");
                        imagenPoster.setIcon(imgPreview);
                        imagenPoster.setPreferredSize(new DimensionUIResource(250, 300));

                        serieOverview.setText(seriesEncontradas.get(j).getserieOverview());

                        infoPanel.add(seriesTitle, BorderLayout.NORTH);
                        infoPanel.add(serieOverview, BorderLayout.CENTER);
                        infoPanel.setBackground(bgColor);
                        // infoPanel.add(seriesReleaseDate, BorderLayout.NORTH);

                        previewPanel.add(imagenPoster, BorderLayout.WEST);
                        previewPanel.add(infoPanel, BorderLayout.CENTER);
                        previewPanel.setBackground(bgColor);
                        previewPanel.setPreferredSize(new DimensionUIResource(1400, 300));
                        // previewPanel.setBackground(bgColor);
                        pnlSearchDisplay.add(previewPanel);

                    } catch (Exception e1) {
                        System.out.println("Unable to fetch Images from defined URL at BufferedImage");
                    }

                }

            } else {
                this.dispose();
                new HomeScreen(usuarioLogIn);
            }
        }

        pnlSearchDisplay.setBackground(bgColor);

        JScrollPane scrollableResults = new JScrollPane(pnlSearchDisplay);
        scrollableResults.setPreferredSize(new DimensionUIResource(1400, 800));
        scrollableResults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableResults.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableResults.getVerticalScrollBar().setUnitIncrement(16);
        scrollableResults.getVerticalScrollBar().setValue(0);

        pnlCentro.add(scrollableResults);
        pnlCentro.setBackground(new Color(23, 23, 16));
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

        this.setPreferredSize(new DimensionUIResource(1800,1013));
        this.setSize(1800, 1013);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        if (emptyFile == true) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }

    }
}
