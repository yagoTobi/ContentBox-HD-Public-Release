package ui.AppManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import org.json.JSONArray;
import org.json.JSONObject;

import domain.MusicDomain.AlbumPreview;
import domain.MusicDomain.Artista;
import domain.MusicDomain.Cancion;
import io.MusicIO.AlbumDataFile;
import io.UserIO.Register;

public class ArtistaTemplate extends JFrame {

    // Declaracion de todos los componentes involucrados en la ventana
    private JPanel pnlNorte = new JPanel();
    private JPanel pnlCentro = new JPanel();
    private JPanel pnlSur = new JPanel();

    private JPanel pnlVisualInfo = new JPanel();
    private JPanel pnlBibliografia = new JPanel(new BorderLayout());
    private JPanel pnlDiscografia = new JPanel();

    private JButton btnLogotype = new JButton("btnLogotype");
    private JButton btnCrearCuenta = new JButton("Crear Cuenta/Iniciar Sesion");
    private JButton btnAjustes = new JButton("Ajustes");

    private JLabel author = new JLabel("Hecho por Yago Tobio Souto - Universidad Pontificia de Comillas 2021");

    private String catalogo_disp[] = { "Catalogo", "Peliculas", "Series", "Artistas (Musica)"};
    private JComboBox comboBoxCatalogo = new JComboBox<>(catalogo_disp);
    private Register profile = new Register();
    public Color bgColor = Color.decode("#171710");
    Font fontType = new Font("Arial", Font.PLAIN, 20);

    public ArtistaTemplate(Register usuarioLogIn) throws IOException {
        super("Content-Box");

        // Propiedades de la ventana
        pnlNorte.setLayout(new FlowLayout());
        pnlCentro.setLayout(new BorderLayout());
        pnlSur.setLayout(new FlowLayout());
        pnlVisualInfo.setLayout(new BorderLayout());
        profile.setVisible(false);

        //Font import         
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
                new HomeScreen(usuarioLogIn);
                dispose();
            }
        });


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

        // Boton crear cuenta/Inicio Sesion
        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setBackground(bgColor);
        btnCrearCuenta.setForeground(Color.decode("#ffffff"));
        btnCrearCuenta.setFont(fontType);
        btnCrearCuenta.setPreferredSize(new DimensionUIResource(270, 50));
        btnCrearCuenta.setFocusPainted(false);
        // Behaviour boton cuenta


        // Dropdown Box Catalogo
        comboBoxCatalogo.setBackground(bgColor);
        comboBoxCatalogo.setForeground(Color.decode("#ffffff"));
        comboBoxCatalogo.setFont(fontType);
        comboBoxCatalogo.setPreferredSize(new DimensionUIResource(150, 30));

        pnlNorte.add(btnLogotype);
        pnlNorte.add(Box.createHorizontalStrut(500));
        pnlNorte.add(btnCrearCuenta);
        pnlNorte.add(Box.createHorizontalStrut(30));
        pnlNorte.add(comboBoxCatalogo);
        pnlNorte.setBackground(bgColor);

    // Contenido del panel centro
        Artista artistaSelected = new Artista();
        JLabel nombreArtista = new JLabel(" " + artistaSelected.getNombreArtista());
        JLabel lblBiografia = new JLabel("Biografia:");
        JTextArea biografiaArtista = new JTextArea(artistaSelected.getBiografiaArtista());
        String fotoLinkArtista = artistaSelected.getFotoArtistaLink();
        URL fotoUrl;

        // Organizamos la parte izquierda del panel
        try {
            fotoUrl = new URL(fotoLinkArtista);
            BufferedImage c = ImageIO.read(fotoUrl);
            ImageIcon profileImg = new ImageIcon(c.getScaledInstance(400, 400, Image.SCALE_DEFAULT));

            JLabel lblFotoArtista = new JLabel("", profileImg, JLabel.CENTER);
            lblFotoArtista.setPreferredSize(new DimensionUIResource(400, 400));
            pnlVisualInfo.add(lblFotoArtista, BorderLayout.NORTH);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        biografiaArtista.setLineWrap(true);
        biografiaArtista.setEditable(false);
        biografiaArtista.setBackground(bgColor);
        biografiaArtista.setForeground(Color.decode("#ffffff"));
        biografiaArtista.setFont(fontType.deriveFont(17f));
        biografiaArtista.setHighlighter(null);

        JScrollPane bioScrollPane = new JScrollPane(biografiaArtista);
        bioScrollPane.getVerticalScrollBar().setValue(0);
        bioScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bioScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bioScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        lblBiografia.setBackground(bgColor);
        lblBiografia.setForeground(Color.decode("#ffffff"));
        lblBiografia.setFont(fontType.deriveFont(20f));

        pnlBibliografia.setBackground(bgColor);
        pnlBibliografia.add(lblBiografia, BorderLayout.NORTH);
        pnlBibliografia.add(bioScrollPane, BorderLayout.CENTER);

        pnlVisualInfo.add(pnlBibliografia, BorderLayout.CENTER);

        // Organizamos la parte derecha con el titulo y la discografia
        
        // Ahora tenemos que iterar a través de su discografia e ir añadiendo paneles
        // verticalmente en un scrollpane
        JPanel pnlVerticalDiscografia = new JPanel(new FlowLayout());
        JLabel lblDiscografia = new JLabel("Discografía:                                                                                                  ");
        lblDiscografia.setForeground(Color.decode("#ffffff"));
        lblDiscografia.setFont(fontType.deriveFont(45f));

        InputStream is = new FileInputStream("resources/files/musicData/discografiaArtista.json");
        String discografiaText = org.apache.commons.io.IOUtils.toString(is, "UTF-8");
        JSONObject outerObject = new JSONObject(discografiaText);
        JSONArray resultArray = outerObject.getJSONArray("album");

        JPanel spacerPanel = new JPanel(new BorderLayout());
        spacerPanel.add(lblDiscografia, BorderLayout.NORTH);
        spacerPanel.setBackground(bgColor);

        // Iteracion del documento que contiene la discografía:
        for (int i = 0; i < resultArray.length(); i++) {
            AlbumPreview albumPreview = new AlbumPreview();
            JSONObject objectinArray = resultArray.getJSONObject(i);
            String[] elementNames = JSONObject.getNames(objectinArray);

            for (String elementName : elementNames) {
                try {

                    if (elementName.equals("strAlbumThumb")) {
                        if (objectinArray.get(elementName).equals(null)) {
                            continue;
                        } else {
                            albumPreview.setAlbumCoverLink(objectinArray.getString(elementName));
                        }

                    } else if (elementName.equals("strAlbumStripped")) {
                        albumPreview.setAlbumTitle(objectinArray.getString(elementName));
                    } else if (elementName.equals("intYearReleased")) {
                        albumPreview.setReleaseData(objectinArray.getString(elementName));
                    } else if (elementName.equals("idAlbum")) {
                        albumPreview.setAlbumId(objectinArray.getString(elementName));
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            // Vale, ahora tras esto ya deberiamos tener cargado el album
            JPanel pnlAlbumPeek = new JPanel(new BorderLayout());
            JButton albumBoton = new JButton();

            // Get Album Thumbnail, y ponemos el boton debajo
            try {
                URL albumCover = new URL(albumPreview.getAlbumCoverLink());
                BufferedImage c = ImageIO.read(albumCover);
                ImageIcon albumCoverIcon = new ImageIcon(c.getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                JLabel lblAlbumCover = new JLabel("", albumCoverIcon, JLabel.CENTER);

                StringBuilder albumTitlesb = new StringBuilder(albumPreview.getAlbumTitle());
                albumTitlesb.append(" (")
                            .append(albumPreview.getReleaseData())
                            .append(")");

                albumBoton.setText(albumTitlesb.toString());
                albumBoton.setBackground(bgColor);
                albumBoton.setForeground(Color.decode("#ffffff"));
                albumBoton.setFont(fontType.deriveFont(15f));

                lblAlbumCover.setPreferredSize(new DimensionUIResource(400, 400));
                pnlAlbumPeek.add(lblAlbumCover, BorderLayout.CENTER);
                pnlAlbumPeek.add(albumBoton, BorderLayout.SOUTH);
                pnlVerticalDiscografia.add(pnlAlbumPeek);
            } catch (Exception ex) {
                //ex.printStackTrace();
            }

            albumBoton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                       AlbumDataFile selectedAlbum = new AlbumDataFile(albumPreview.getAlbumId());
                       ArrayList<Cancion> selectedSongs = selectedAlbum.cancionesAlbum();
                       JPanel pnlCancionesAlbum = new JPanel();
                       
                       BoxLayout boxLayout = new BoxLayout(pnlCancionesAlbum, BoxLayout.Y_AXIS);
                       pnlCancionesAlbum.setLayout(boxLayout);

                       //Ahora que ya tenemos nuestro arrayList de canciones, tenemos que construir un JPanel 
                       //Tal que nos lo ponga una debajo de otra 
                       for(Cancion c:selectedSongs)
                       {
                           JPanel pnlPreviewCancion = new JPanel();
                           JLabel numeroCancion = new JLabel(c.getSongNumber());
                           JLabel nombreCancion = new JLabel(c.getNombreCancion());
                           JLabel nombreArtista = new JLabel(c.getNombreArtista());
                           JButton musicVideo = new JButton("Video Musical");

                           //Customisation for the labels 
                           numeroCancion.setBackground(bgColor);
                           numeroCancion.setForeground(Color.decode("#ffffff"));
                           numeroCancion.setFont(fontType.deriveFont(15f));

                           nombreCancion.setBackground(bgColor);
                           nombreCancion.setForeground(Color.decode("#ffffff"));
                           nombreCancion.setFont(fontType.deriveFont(15f));

                           nombreArtista.setBackground(bgColor);
                           nombreArtista.setForeground(Color.decode("#ffffff"));
                           nombreArtista.setFont(fontType.deriveFont(15f));

                           pnlPreviewCancion.add(numeroCancion);
                           pnlPreviewCancion.add(Box.createHorizontalStrut(20));
                           pnlPreviewCancion.add(nombreCancion);
                           pnlPreviewCancion.add(Box.createHorizontalStrut(20));
                           pnlPreviewCancion.add(nombreArtista);
                           pnlPreviewCancion.add(Box.createHorizontalStrut(20));
                           pnlPreviewCancion.setBackground(bgColor);

                           if(c.getMvLink().equals("null"))
                           {
                               //No añadimos el boton al JPanel 
                           }else
                           {

                            musicVideo.addActionListener(new ActionListener()
                            {
                                @Override
                                public void actionPerformed(ActionEvent e)
                                {
                                    Runtime rt = Runtime.getRuntime();
                                    try {
                                        rt.exec("rundll32 url.dll, FileProtocolHandler "+ c.getMvLink());
                                     } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });


                             pnlPreviewCancion.add(musicVideo);
                           }
                           pnlPreviewCancion.setAlignmentX(Component.LEFT_ALIGNMENT);
                           
                           pnlCancionesAlbum.add(pnlPreviewCancion);
                       }
                       //Creamos la ventana en donde se puede ver la informacion de las canciones 
                       JFrame infoAlbum = new JFrame();

                       JScrollPane scrollPaneCanciones = new JScrollPane(pnlCancionesAlbum);
                       scrollPaneCanciones.setPreferredSize(new DimensionUIResource(550,450));
                       scrollPaneCanciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                       scrollPaneCanciones.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                       infoAlbum.add(scrollPaneCanciones);
                       infoAlbum.setVisible(true);
                       infoAlbum.setResizable(false);
                       infoAlbum.setLocationRelativeTo(null);
                       infoAlbum.setSize(new DimensionUIResource(550,450));
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }

        nombreArtista.setForeground(Color.decode("#ffffff"));
        nombreArtista.setFont(fontType.deriveFont(50f));

        JScrollPane scrollPaneDiscografia = new JScrollPane(pnlVerticalDiscografia);
        scrollPaneDiscografia.setBackground(bgColor);
        scrollPaneDiscografia.setPreferredSize(new DimensionUIResource(1300,450));
        scrollPaneDiscografia.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneDiscografia.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        pnlDiscografia.add(nombreArtista, BorderLayout.NORTH);
        pnlDiscografia.add(spacerPanel, BorderLayout.CENTER);
        pnlDiscografia.add(scrollPaneDiscografia, BorderLayout.SOUTH);
        
        pnlDiscografia.setBackground(bgColor);
        pnlCentro.setBackground(bgColor);

        pnlCentro.add(pnlVisualInfo, BorderLayout.WEST);
        pnlCentro.add(pnlDiscografia, BorderLayout.CENTER);

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
     * @throws IOException
     * 
     * Plantilla para el artista buscado en el HomeScreen, deberia mostrar su biografia en español, pero en el caso de que no lo encuentre, lo mostrará en Ingles. 
     * A su derecha, podremos observar su discografía. Toda dependiente de la API TheAudioDB. 
     * 
     */
    public static void main(String[] args) throws IOException {
        //new ArtistaTemplate();
    }

}
