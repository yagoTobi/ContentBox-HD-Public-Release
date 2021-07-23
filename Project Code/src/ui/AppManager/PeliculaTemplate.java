package ui.AppManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import domain.PeliculasDomain.Pelicula;
import io.UserIO.Register;

public class PeliculaTemplate extends JFrame {

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
    private JButton btnCrearCuenta = new JButton("Crear Cuenta/Iniciar Sesion");
    private JButton btnAjustes = new JButton("Ajustes");

    private JLabel author = new JLabel("Hecho por Yago Tobio Souto - Universidad Pontificia de Comillas 2021");

    private String catalogo_disp[] = { "Catalogo", "Peliculas", "Series", "Artistas (Musica)", "Libros" };
    private JComboBox comboBoxCatalogo = new JComboBox<>(catalogo_disp);
    public Color bgColor = Color.decode("#171710");
    public Font fontType = new Font("Arial", Font.PLAIN, 20);
    private JFrame warningSearch = new JFrame();

    public PeliculaTemplate(Register userLogin) throws IOException {

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

        // Boton crear cuenta/Inicio Sesion
        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setBackground(bgColor);
        btnCrearCuenta.setForeground(Color.decode("#ffffff"));
        btnCrearCuenta.setFont(fontType);
        btnCrearCuenta.setPreferredSize(new DimensionUIResource(270, 50));
        btnCrearCuenta.setFocusPainted(false);
        // Behaviour boton cuenta
        if (userLogin.getLoggedInUser() != null) {
            System.out.println("User is not null");
            System.out.println(userLogin.getLoggedInUser().toString());
            btnCrearCuenta.setText("Mi Perfil");
            btnCrearCuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If we compile individual files, since this is a JFrame of it's own,
                    // compilation no work no good
                    try {
                        new ProfileTemplate(userLogin);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    dispose();
                }
            });
        } else {
            System.out.println(userLogin.getLoggedInUser());
            btnCrearCuenta.setText("Crear Cuenta/Iniciar Sesion");
            // Behaviour boton cuenta
            btnCrearCuenta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If we compile individual files, since this is a JFrame of it's own,
                    // compilation no work no good
                    userLogin.setVisible(true);
                    dispose();
                }
            });
        }

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

        // Contenido del Panel del centro

        Pelicula peliculaSelected = new Pelicula();

        // Top Part info

        JButton btnHyperLink = new JButton("Visitar la pagina web");
        btnHyperLink.setFont(fontType.deriveFont(18f));
        btnHyperLink.setBackground(bgColor);
        btnHyperLink.setForeground(Color.decode("#ffffff"));
        btnHyperLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runtime rt = Runtime.getRuntime();
                try {

                    if (peliculaSelected.getPeliculaHomepageLink().equals("")) {
                        JOptionPane.showMessageDialog(warningSearch, "La siguiente pelicula no tiene una pagina web",
                                "Alert", JOptionPane.WARNING_MESSAGE);
                    } else {
                        rt.exec("rundll32 url.dll, FileProtocolHandler " + peliculaSelected.getPeliculaHomepageLink());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Bottom Part info
        JLabel lblGenre = new JLabel();
        JLabel lblRating = new JLabel();
        JLabel lblReleaseDate = new JLabel();
        JLabel lblRuntime = new JLabel();
        JLabel lblProductionCompanies = new JLabel();

        JButton btnWatchLater = new JButton();
        JButton btnSeenIt = new JButton();
        JButton btnCreateReview = new JButton();
        JLabel lblContentTitle = new JLabel("  " + peliculaSelected.getPeliculaTitle());
        JLabel lblOverview = new JLabel("Overview:");
        JTextArea areaContentDescription = new JTextArea(peliculaSelected.getPeliculaOverview());
        JLabel lblimgMedia = new JLabel();

        lblimgMedia.setSize(new DimensionUIResource(1300, 700));
        // ImageIcon tstImagesmol = new
        // ImageIcon("resources/images/MainScreenWallpapers/tqg_2.jpg");

        StringBuilder posterLink = new StringBuilder("https://image.tmdb.org/t/p/w300");
        posterLink.append(peliculaSelected.getPeliculaPosterLink());
        String peliculaPosterLink = posterLink.toString();

        URL posterUrl;

        try {
            posterUrl = new URL(peliculaPosterLink);
            BufferedImage c = ImageIO.read(posterUrl);
            ImageIcon tstImagesmol = new ImageIcon(c);

            JLabel lblTestIcon = new JLabel("", tstImagesmol, JLabel.CENTER);
            btnHyperLink.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), 50));

            pnlMediaSummary.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), 300));

            pnlVisualInfo.add(lblTestIcon);

            pnlVisualInfo.setPreferredSize(new DimensionUIResource(tstImagesmol.getIconWidth(), pnlCentro.getHeight()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Icon seenIcon = new ImageIcon("resources/images/LogotypesButtons/seen_1.png");
        Icon seeIconClicked = new ImageIcon("resources/images/LogotypesButtons/seen_2.png");

        Icon watchLater = new ImageIcon("resources/images/LogotypesButtons/watch_later_1.png");
        Icon watchLaterClicked = new ImageIcon("resources/images/LogotypesButtons/watch_later_2.png");

        Icon writeReview = new ImageIcon("resources/images/LogotypesButtons/write_review_1.png");
        Icon writtenReview = new ImageIcon("resources/images/LogotypesButtons/write_review_2.png");

        lblGenre.setFont(fontType.deriveFont(20f));
        lblRating.setFont(fontType.deriveFont(20f));
        lblReleaseDate.setFont(fontType.deriveFont(20f));
        lblRuntime.setFont(fontType.deriveFont(20f));
        lblProductionCompanies.setFont(fontType.deriveFont(20f));

        lblGenre.setForeground(Color.decode("#ffffff"));
        lblRating.setForeground(Color.decode("#ffffff"));
        lblReleaseDate.setForeground(Color.decode("#ffffff"));
        lblRuntime.setForeground(Color.decode("#ffffff"));
        lblProductionCompanies.setForeground(Color.decode("#ffffff"));

        lblGenre.setText("  Genero: Drama");

        StringBuilder ratingBuilder = new StringBuilder("Clasificación: ");
        ratingBuilder.append(peliculaSelected.getPeliculaRating());
        lblRating.setText(ratingBuilder.toString());

        StringBuilder releaseDateBuilder = new StringBuilder("Fecha de Estreno: ");
        releaseDateBuilder.append(peliculaSelected.getPeliculaReleaseDate());
        lblReleaseDate.setText(releaseDateBuilder.toString());

        StringBuilder runtimeBuilder = new StringBuilder("Duración: ");
        runtimeBuilder.append(peliculaSelected.getPeliculaRunTime());
        runtimeBuilder.append(" min.");
        lblRuntime.setText(runtimeBuilder.toString());

        // TO DO -> PENDIENTE POR ARREGLAR LAS COMPAÑIAS DE PRODUCCION Y EL GENERO sin tiempo
        //lblProductionCompanies.setText("Producida por: Netflix");

        pnlMediaSummary.add(lblGenre);
        pnlMediaSummary.add(Box.createHorizontalStrut(25));
        pnlMediaSummary.add(lblRating);
        pnlMediaSummary.add(lblReleaseDate);
        pnlMediaSummary.add(lblRuntime);
        pnlMediaSummary.add(lblProductionCompanies);
        pnlMediaSummary.setBackground(bgColor);

        pnlVisualInfo.add(Box.createVerticalStrut(50));
        pnlVisualInfo.add(btnHyperLink);
        pnlVisualInfo.add(pnlMediaSummary);
        pnlVisualInfo.setBackground(bgColor);

        lblContentTitle.setFont(fontType.deriveFont(60f));
        lblContentTitle.setForeground(Color.decode("#ffffff"));
        lblOverview.setFont(fontType.deriveFont(30f));
        lblOverview.setForeground(Color.decode("#ffffff"));

        areaContentDescription.setBackground(bgColor);
        areaContentDescription.setForeground(Color.decode("#ffffff"));
        areaContentDescription.setLineWrap(true);
        areaContentDescription.setEditable(false);
        areaContentDescription.setPreferredSize(new DimensionUIResource(1000, 200));

        btnWatchLater.setPreferredSize(new DimensionUIResource(50, 50));

        // Aqui debemos incluir un check para dos cosas
        // 1.- Primero de todo ver si estamos metidos en una cuenta
        // 2.- Si si que esta metido en la cuenta, comprobar si esta en nuestro registro
        // de usuarios
        if (userLogin.getLoggedInUser() != null) {
            // Check si esta en nuestro registro para peliculas vistas
            // Esto no funciona, ya que no va a ser el mismo objeto :(
            // user.getLoggedInUser() -> uf

            if ((userLogin.getLoggedInUser().getIdPeliculas(userLogin.getLoggedInUser().getPeliculasPorVer()))
                    .contains(peliculaSelected.getPeliculaId())) {
                btnWatchLater.setIcon(watchLaterClicked);
            } else {
                btnWatchLater.setIcon(watchLater);
            }
        } else {
            btnWatchLater.setIcon(watchLater);
        }

        btnWatchLater.setBackground(Color.decode("#4a4a46"));
        btnWatchLater.setBorderPainted(false);
        btnWatchLater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userLogin.getLoggedInUser() != null) {
                    // Tenemos que re-escribir el archivo
                    Gson gsonRegister = new GsonBuilder().setPrettyPrinting().create();

                    // StringBuilder para el acceso del archivo
                    StringBuilder sb = new StringBuilder("resources/files/userData/users/");
                    sb.append(userLogin.getLoggedInUser().getUsuario()).append("/")
                            .append(userLogin.getLoggedInUser().getUsuario()).append("DataFile.json");

                    if (btnWatchLater.getIcon() == watchLater) {
                        btnWatchLater.setIcon(watchLaterClicked);
                        userLogin.getLoggedInUser().addPeliculasPorVer(peliculaSelected);

                        try (Writer userDataWriter = new FileWriter(sb.toString())) {
                            gsonRegister.toJson(userLogin.getLoggedInUser(), userDataWriter);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        btnWatchLater.setIcon(watchLater);
                        userLogin.getLoggedInUser().removePeliculasPorVer(peliculaSelected.getPeliculaId());
                        // Tenemos que re-escribir el archivo
                        try (Writer userDataWriter = new FileWriter(sb.toString())) {
                            gsonRegister.toJson(userLogin.getLoggedInUser(), userDataWriter);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(warningSearch,
                            "Si quiere usar esta funcionalidad, porfavor creese una cuenta.", "Alert",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        btnSeenIt.setPreferredSize(new DimensionUIResource(50, 50));

        // Aqui debemos incluir un check para dos cosas
        // 1.- Primero de todo ver si estamos metidos en una cuenta
        // 2.- Si si que esta metido en la cuenta, comprobar si esta en nuestro registro
        // de usuarios
        if (userLogin.getLoggedInUser() != null) {
            // Check si esta en nuestro registro para peliculas vistas
            // Esto no funciona, ya que no va a ser el mismo objeto :(
            // user.getLoggedInUser() -> uf

            if ((userLogin.getLoggedInUser().getIdPeliculas(userLogin.getLoggedInUser().getPeliculasVistas()))
                    .contains(peliculaSelected.getPeliculaId())) {
                btnSeenIt.setIcon(seeIconClicked);
            } else {
                btnSeenIt.setIcon(seenIcon);
            }
        } else {
            btnSeenIt.setIcon(seenIcon);
        }

        btnSeenIt.setBackground(Color.decode("#4a4a46"));
        btnSeenIt.setBorderPainted(false);
        btnSeenIt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userLogin.getLoggedInUser() != null) {

                    // Tenemos que re-escribir el archivo
                    Gson gsonRegister = new GsonBuilder().setPrettyPrinting().create();

                    // StringBuilder para el acceso del archivo
                    StringBuilder sb = new StringBuilder("resources/files/userData/users/");
                    sb.append(userLogin.getLoggedInUser().getUsuario()).append("/")
                            .append(userLogin.getLoggedInUser().getUsuario()).append("DataFile.json");

                    if (btnSeenIt.getIcon() == seenIcon) {
                        btnSeenIt.setIcon(seeIconClicked);
                        userLogin.getLoggedInUser().addPeliculaVista(peliculaSelected);

                        try (Writer userDataWriter = new FileWriter(sb.toString())) {
                            gsonRegister.toJson(userLogin.getLoggedInUser(), userDataWriter);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    } else {

                        btnSeenIt.setIcon(seenIcon);

                        userLogin.getLoggedInUser().removePeliculaVista(peliculaSelected.getPeliculaId());

                        try (Writer userDataWriter = new FileWriter(sb.toString())) {
                            gsonRegister.toJson(userLogin.getLoggedInUser(), userDataWriter);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(warningSearch,
                            "Si quiere usar esta funcionalidad, porfavor creese una cuenta.", "Alert",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        btnCreateReview.setPreferredSize(new DimensionUIResource(50, 50));

        if (userLogin.getLoggedInUser() != null) {
            // Check si esta en nuestro registro para peliculas vistas
            // Esto no funciona, ya que no va a ser el mismo objeto :(
            // user.getLoggedInUser() -> uf

            if (userLogin.getLoggedInUser().getPeliculasCriticadas().containsKey(peliculaSelected.getPeliculaId())) {
                btnCreateReview.setIcon(writtenReview);
            } else {
                btnCreateReview.setIcon(writeReview);
            }
        } else {
            btnCreateReview.setIcon(writeReview);
        }

        btnCreateReview.setBackground(Color.decode("#4a4a46"));
        btnCreateReview.setBorderPainted(false);
        btnCreateReview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userLogin.getLoggedInUser() != null) {

                    // Tenemos que re-escribir el archivo
                    Gson gsonRegister = new GsonBuilder().setPrettyPrinting().create();

                    // StringBuilder para el acceso del archivo
                    StringBuilder sb = new StringBuilder("resources/files/userData/users/");
                    sb.append(userLogin.getLoggedInUser().getUsuario()).append("/")
                            .append(userLogin.getLoggedInUser().getUsuario()).append("DataFile.json");

                    // Creamos la pestaña con todas sus propiedad
                    JFrame opinionFrame = new JFrame();
                    JPanel opinionPanel = new JPanel(new BorderLayout());
                    JButton saveOpinionBtn = new JButton("Guardar Critica");
                    JTextArea opinionText = new JTextArea();

                    // En el caso en donde no se haya escrito una critica
                    if (btnCreateReview.getIcon() == writeReview) {
                        btnCreateReview.setIcon(writtenReview);

                        opinionText.setSize(350, 350);
                        opinionText.setFont(fontType.deriveFont(15f));
                        opinionText.setBackground(bgColor);
                        opinionText.setForeground(Color.decode("#ffffff"));
                        opinionText.setLineWrap(true);

                        saveOpinionBtn.setSize(100, 50);
                        saveOpinionBtn.setBackground(bgColor);
                        saveOpinionBtn.setFont(fontType.deriveFont(20f));
                        saveOpinionBtn.setForeground(Color.decode("#ffffff"));

                        saveOpinionBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {
                                // Si no se ha guardado ningun texto en la cuadricula, dejamos todo como estaba
                                if (opinionText.getText().equals("")) {
                                    btnCreateReview.setIcon(writeReview);
                                    userLogin.getLoggedInUser().removePeliculasCriticadas(peliculaSelected.getPeliculaId());
                                } else {
                                    // Ahora tenemos que guardar en su sitio
                                    userLogin.getLoggedInUser().addPeliculasCriticadas(peliculaSelected.getPeliculaId(),
                                            opinionText.getText());
                                }

                                // Tras esto, guardalo en el archivo
                                try (Writer userDataWriter = new FileWriter(sb.toString())) {
                                    gsonRegister.toJson(userLogin.getLoggedInUser(), userDataWriter);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                            }
                        });

                        opinionPanel.add(opinionText, BorderLayout.CENTER);
                        opinionPanel.add(saveOpinionBtn, BorderLayout.SOUTH);
                        opinionPanel.setPreferredSize(new DimensionUIResource(400, 400));

                        opinionFrame.add(opinionPanel);
                        opinionFrame.setVisible(true);
                        opinionFrame.setResizable(false);
                        opinionFrame.setSize(new DimensionUIResource(400, 400));
                        opinionFrame.setLocationRelativeTo(null);
                        opinionFrame.setTitle("Critica Personal");

                    } else {

                        opinionText.setSize(350, 350);
                        opinionText.setFont(fontType.deriveFont(15f));
                        opinionText.setBackground(bgColor);
                        opinionText.setText(userLogin.getLoggedInUser().getPeliculasCriticadas()
                                .get(peliculaSelected.getPeliculaId()));
                        opinionText.setForeground(Color.decode("#ffffff"));
                        opinionText.setLineWrap(true);

                        saveOpinionBtn.setSize(100, 50);
                        saveOpinionBtn.setBackground(bgColor);
                        saveOpinionBtn.setFont(fontType.deriveFont(20f));
                        saveOpinionBtn.setForeground(Color.decode("#ffffff"));

                        saveOpinionBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {
                                // Para guardar al archivo del usuario
                                if (opinionText.getText().equals("")) {
                                    btnCreateReview.setIcon(writeReview);
                                    userLogin.getLoggedInUser()
                                            .removePeliculasCriticadas(peliculaSelected.getPeliculaId());

                                    try (Writer userDataWriter = new FileWriter(sb.toString())) {
                                        gsonRegister.toJson(userLogin.getLoggedInUser(), userDataWriter);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    userLogin.getLoggedInUser().addPeliculasCriticadas(peliculaSelected.getPeliculaId(),
                                            opinionText.getText());

                                    try (Writer userDataWriter = new FileWriter(sb.toString())) {
                                        gsonRegister.toJson(userLogin.getLoggedInUser(), userDataWriter);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        });

                        opinionPanel.add(opinionText, BorderLayout.CENTER);
                        opinionPanel.add(saveOpinionBtn, BorderLayout.SOUTH);
                        opinionPanel.setPreferredSize(new DimensionUIResource(400, 400));

                        opinionFrame.add(opinionPanel);
                        opinionFrame.setVisible(true);
                        opinionFrame.setResizable(false);
                        opinionFrame.setSize(new DimensionUIResource(400, 400));
                        opinionFrame.setLocationRelativeTo(null);
                        opinionFrame.setTitle("Critica Personal");
                    }

                } else {
                    JOptionPane.showMessageDialog(warningSearch,
                            "Si quiere usar esta funcionalidad, porfavor creese una cuenta.", "Alert",
                            JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        areaContentDescription.setFont(fontType);
        areaContentDescription.setForeground(Color.decode("#ffffff"));

        StringBuilder wallpaperLink = new StringBuilder("https://image.tmdb.org/t/p/original");
        wallpaperLink.append(peliculaSelected.getPeliculaWallpaperLink());
        String peliculaWallpaperLink = wallpaperLink.toString();

        URL wallpaperUrl;

        try {
            wallpaperUrl = new URL(peliculaWallpaperLink);
            BufferedImage c1 = ImageIO.read(wallpaperUrl);
            Image dimg = c1.getScaledInstance(lblimgMedia.getWidth(), lblimgMedia.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imgMedia = new ImageIcon(dimg);

            lblimgMedia.setIcon(imgMedia);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // BorderLayout South is for the images that we obtain from the API Link
        // We're going to have to pass this image as a link image within the parameter
        // of the class

        pnlButtons.add(btnWatchLater);
        pnlButtons.add(btnSeenIt);
        pnlButtons.add(btnCreateReview);
        pnlButtons.add(Box.createHorizontalStrut(1200));
        pnlButtons.add(lblOverview);
        pnlButtons.add(Box.createHorizontalStrut(1200));
        pnlButtons.add(areaContentDescription);
        pnlButtons.setBackground(bgColor);

        pnlTextInfo.add(lblContentTitle, BorderLayout.NORTH);
        pnlTextInfo.add(pnlButtons, BorderLayout.CENTER);
        pnlTextInfo.setForeground(Color.decode("#ffffff"));
        pnlTextInfo.setBackground(bgColor);

        pnlInfo.add(pnlTextInfo, BorderLayout.NORTH);
        pnlInfo.add(Box.createHorizontalStrut(50), BorderLayout.WEST);
        pnlInfo.add(lblimgMedia, BorderLayout.CENTER);
        pnlInfo.add(Box.createHorizontalStrut(50), BorderLayout.EAST);
        pnlInfo.setBackground(bgColor);

        pnlCentro.add(pnlVisualInfo, BorderLayout.WEST);
        pnlCentro.add(pnlInfo, BorderLayout.CENTER);
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

        this.setPreferredSize(new DimensionUIResource(1800, 1013));
        this.setSize(1800, 1013);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * @param args
     * @throws IOException
     * 
     * 
     *                     Esto coge la pelicula seleccionada en la ventana de la
     *                     busqueda, y buscar en la carpeta de archivos por la
     *                     pelicula seleccionada. La cual obtiene un archivo JSON de
     *                     la API El programa se encarga de coger dichas propiedades
     *                     y desplegarlas de una manera agradable.
     * 
     *                     Tambien tenemos temporalmente la funcionalidad de decidir
     *                     si esta pelicula ya la hemos visto, la queremos ver, o
     *                     poner una opinion y guardarla a nuestro perfil.
     */
    public static void main(String[] args) throws IOException {
        // new PeliculaTemplate();
    }
}