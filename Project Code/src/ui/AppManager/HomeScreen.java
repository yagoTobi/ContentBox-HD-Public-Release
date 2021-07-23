package ui.AppManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import io.MusicIO.ArtistaDataFile;
import io.MusicIO.DiscographySearchDataFile;
import io.PeliculasIO.PeliculaSearchDataFile;
import io.SeriesIO.SerieSearchDataFile;
import io.UserIO.Register;

public class HomeScreen extends JFrame {

    // Declaracion de todos los componentes involucrados en la ventana
    private JPanel pnlNorte = new JPanel();
    private JPanel pnlCentro = new JPanel();
    private JPanel pnlSur = new JPanel();

    private JButton btnLogotype = new JButton();
    private JButton btnCrearCuenta = new JButton();
    private JButton btnSearchClick = new JButton();
    private JButton btnAjustes = new JButton("Ajustes");

    private JLabel buscar_searchtype = new JLabel("Busca tu siguiente pelicula/serie/album...");
    private JLabel author = new JLabel("Realizado por Yago Tobio Souto - Universidad Pontificia de Comillas 2021");
    private String catalogo_disp[] = { "Catalogo", "Peliculas", "Series", "Artistas (Musica)" };
    private JComboBox comboBoxCatalogo = new JComboBox<>(catalogo_disp);

    private JTextField txt_searchbar = new JTextField(25);

    private String urlSearch;
    private String contentSearch;
    private JFrame warningSearch = new JFrame();

    public Color bgColor = Color.decode("#171710");

    public HomeScreen(Register logInSignUp) {

        super("Content-Box");
        // Propiedades de la ventana
        pnlNorte.setLayout(new FlowLayout());
        pnlCentro.setLayout(new FlowLayout());
        pnlSur.setLayout(new FlowLayout());

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
        // btnLogotype.setRolloverIcon(btnLogotype_clicked);

        // Boton crear cuenta/Inicio Sesion
        
        if(logInSignUp.getLoggedInUser() != null)
        {
            System.out.println("User is not null");
            System.out.println(logInSignUp.getLoggedInUser().toString());
            btnCrearCuenta.setText("Mi Perfil");
            btnCrearCuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If we compile individual files, since this is a JFrame of it's own,
                    // compilation no work no good
                    try {
                        new ProfileTemplate(logInSignUp);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    dispose();
                }
            });
        }else
        {
            System.out.println(logInSignUp.getLoggedInUser());
            btnCrearCuenta.setText("Crear Cuenta/Iniciar Sesion");
            // Behaviour boton cuenta
            btnCrearCuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If we compile individual files, since this is a JFrame of it's own,
                    // compilation no work no good
                    logInSignUp.setVisible(true);
                    dispose();
                }
            });
        }

        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setBackground(bgColor);
        btnCrearCuenta.setForeground(Color.decode("#ffffff"));
        btnCrearCuenta.setFont(font_type);
        btnCrearCuenta.setPreferredSize(new DimensionUIResource(270, 50));
        btnCrearCuenta.setFocusPainted(false);
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
        // pnlNorte.size();
        pnlNorte.setBackground(bgColor);

        // Contenido del panel centro

        buscar_searchtype.setFont(font_type.deriveFont(60f));
        buscar_searchtype.setForeground(Color.decode("#c4c1c0"));
        txt_searchbar.setPreferredSize(new DimensionUIResource(700, 50));
        txt_searchbar.setFont(font_type.deriveFont(40f));
        txt_searchbar.setAlignmentX(LEFT_ALIGNMENT);
        txt_searchbar.setMaximumSize(txt_searchbar.getPreferredSize());
        // behaviour text field
        txt_searchbar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if ((comboBoxCatalogo.getSelectedItem()).equals("Peliculas")) {
                        contentSearch = txt_searchbar.getText().replaceAll("\\s+", "%20");
                        urlSearch = "https://api.themoviedb.org/3/search/movie?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US&query="
                                + contentSearch + "&page=1&include_adult=false";
                        txt_searchbar.setText("");
                        try {
                            new PeliculaSearchDataFile(urlSearch);
                            new SearchResults(comboBoxCatalogo.getSelectedItem().toString(),logInSignUp);

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        dispose();
                    } else if ((comboBoxCatalogo.getSelectedItem()).equals("Series")) {
                        contentSearch = txt_searchbar.getText().replaceAll("\\s+", "%20");
                        urlSearch = "https://api.themoviedb.org/3/search/tv?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US&page=1&query="
                                + contentSearch + "&include_adult=false";
                        txt_searchbar.setText("");
                        try {
                            new SerieSearchDataFile(urlSearch);
                            new SearchResults(comboBoxCatalogo.getSelectedItem().toString(),logInSignUp);

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        dispose();
                    } else if ((comboBoxCatalogo.getSelectedItem()).equals("Artistas (Musica)")) {
                        // Prepara el link para la busqueda de albumes
                        contentSearch = txt_searchbar.getText().replaceAll("\\s+", "_");
                        urlSearch = "https://www.theaudiodb.com/api/v1/json/523532/search.php?s=" + contentSearch;
                        String discografiaArtistaLink = "https://theaudiodb.com/api/v1/json/523532/searchalbum.php?s="
                                + contentSearch;

                        txt_searchbar.setText("");

                        try {
                            new ArtistaDataFile(urlSearch);
                            new DiscographySearchDataFile(discografiaArtistaLink);
                            new ArtistaTemplate(logInSignUp);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        dispose();

                    } else {
                        JOptionPane.showMessageDialog(warningSearch,
                                "Antes de buscar, asegurese que ha seleccionado el tipo de contenido en el Catalogo que desea buscar! (Arriba a mano derecha)",
                                "Alert", JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });

        Icon lupa = new ImageIcon("resources/images/LogotypesButtons/lupa_search.png");
        btnSearchClick.setPreferredSize(new DimensionUIResource(70, 50));
        btnSearchClick.setBackground(bgColor);
        btnSearchClick.setBorderPainted(false);
        btnSearchClick.setHorizontalAlignment(SwingConstants.CENTER);
        btnSearchClick.setIcon(lupa);
        btnSearchClick.setFocusPainted(false);
        btnSearchClick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if ((comboBoxCatalogo.getSelectedItem()).equals("Peliculas")) {
                    contentSearch = txt_searchbar.getText().replaceAll("\\s+", "%20");
                    urlSearch = "https://api.themoviedb.org/3/search/movie?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US&query="
                            + contentSearch + "&page=1&include_adult=false";
                    txt_searchbar.setText("");
                    try {
                        new PeliculaSearchDataFile(urlSearch);
                        new SearchResults("Peliculas", logInSignUp);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else if ((comboBoxCatalogo.getSelectedItem()).equals("Series")) {
                    contentSearch = txt_searchbar.getText().replaceAll("\\s+", "%20");
                    urlSearch = "https://api.themoviedb.org/3/search/tv?api_key=979d65dab975515083c638cdfaeb92e2&language=en-US&page=1&query="
                            + contentSearch + "&include_adult=false";
                    txt_searchbar.setText("");
                    try {
                        new PeliculaSearchDataFile(urlSearch);
                        new SearchResults("Series",logInSignUp);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    dispose();
                } else if ((comboBoxCatalogo.getSelectedItem()).equals("Artistas (Musica)")) {
                    // Prepara el link para la busqueda de albumes
                    contentSearch = txt_searchbar.getText().replaceAll("\\s+", "_");
                    urlSearch = "https://www.theaudiodb.com/api/v1/json/523532/search.php?s=" + contentSearch;
                    txt_searchbar.setText("");

                    try {
                        new ArtistaDataFile(urlSearch);
                        new ArtistaTemplate(logInSignUp);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(warningSearch,
                                "Artista no encontrado, intente meter el nombre exacto", "Alert",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(warningSearch,
                            "Antes de buscar, asegurese que ha seleccionado el tipo de contenido en el Catalogo que desea buscar! (Arriba a mano derecha)",
                            "Alert", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        pnlCentro.add(Box.createVerticalStrut(350));
        pnlCentro.add(buscar_searchtype);
        pnlCentro.add(txt_searchbar);
        pnlCentro.add(btnSearchClick);
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
        this.setVisible(true);
    }

    
    /** 
     * @param args
     * La central de todo el programa. En la parte de la derecha, se selecciona en el 'CATALOGO' el tipo de busqueda que queremos. 
     * El resto se suele explicar solo. Observamos las propiedades de la barra de texto. Se puede presionar Enter o el boton con la imagen de la lupa para iniciar la busqueda
     */
    public static void main(String[] args) {

        Register registerPublic = new Register();
        new HomeScreen(registerPublic);
    }
}