package io.UserIO;

//Log in/Sign Up Feature window 
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.DimensionUIResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import domain.UserDomain.User;
import domain.UserDomain.UserFeatures;
import ui.AppManager.HomeScreen;

public class Register extends JFrame {

  private JPanel pnlSignUp = new JPanel();
  private JPanel pnlLogIn = new JPanel();
  private JTextField txtSuUser = new JTextField(25);
  private JPasswordField txtSuPswrd = new JPasswordField(25);
  private JTextField txtLiUser = new JTextField(25);
  private JPasswordField txtLiPswrd = new JPasswordField(25);
  private JFrame warningDuplicate = new JFrame();
  private JFrame loggedInAlert = new JFrame();

  private JButton btnCreacion = new JButton("Crear tu cuenta");
  private JButton btnSignIn = new JButton("Iniciar Sesion");
  private ArrayList<User> userData = new ArrayList<User>();

  private File checkRegister = new File("resources/files/userData/register.json");
  private boolean boolExistsRegister = checkRegister.exists();
  private boolean boolDuplicateUser = false;

  private JCheckBox cbSuShowHide = new JCheckBox("Mostrar clave de acceso");
  private JCheckBox cbLiShowHide = new JCheckBox("Mostrar clave de acceso"); // Porque no queremos escribir contraseña
  private StringBuilder userDataLink = new StringBuilder("resources/files/userData/users/");
  private UserFeatures loggedInUser = new UserFeatures(null, null);
  public Color bgColor = Color.decode("#171710");

  public Register() {

    super("Ingresar en la pagina");
    // Font type import -> Check about code repetition
    Font font_type = new Font("Arial", Font.PLAIN, 20);
    try {
      font_type = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Roboto-Light.ttf")).deriveFont(18f);
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Roboto-Light.ttf")));
    } catch (FontFormatException | IOException e) {
      System.out.println("Error importing Font");
    }

    setLoggedInUser(null);

    UIManager.put("TabbedPane.selected", Color.BLACK);
    UIManager.put("TabbedPane.borderHighlightColor", Color.BLACK);
    UIManager.getDefaults().put("TabbedPane.lightHighlight", Color.LIGHT_GRAY);
    UIManager.getDefaults().put("TabbedPane.selectHighlight", Color.LIGHT_GRAY);
    JTabbedPane tp = new JTabbedPane();
    tp.setBorder(null);

    JLabel lbl_su_usrname = new JLabel("Usuario:");
    JLabel lbl_li_usrname = new JLabel("Usuario:");
    JLabel lbl_li_pswrd = new JLabel("Clave de Acceso: ");
    JLabel lbl_su_pswrd = new JLabel("Clave de Acceso: ");
    char password_bullet = '\u2022';

    // Cargar los usarios del json en el evento en el que exista el archivo
    try {
      // Vamos a leer los usuarios que tenemos en nuestro archivo con sus coordenadas
      // y pasarlos a un ArrayList
      if (boolExistsRegister == true) {

        String readRegisterFile = FileUtils.readFileToString(checkRegister, StandardCharsets.UTF_8);
        JSONArray jsonArr = new JSONArray(readRegisterFile);

        for (int i = 0; i < jsonArr.length(); i++) {
          JSONObject jsonObj = jsonArr.getJSONObject(i);
          User newUser = new User();
          newUser.setUsuario(jsonObj.getString("usuario"));
          newUser.setClave(jsonObj.getString("clave"));
          userData.add(newUser);
        }

      } else {
        System.out.println("File no exist, check back later");
      }
    } catch (Exception ex) {
      // ex.printStackTrace();
    }

    pnlSignUp.setLayout(new FlowLayout());
    pnlLogIn.setLayout(new FlowLayout());

    // Propiedades de los elementos
    txtLiPswrd.setFont(font_type.deriveFont(20f));
    txtLiUser.setFont(font_type.deriveFont(20f));

    txtSuPswrd.setFont(font_type.deriveFont(20f));
    txtSuUser.setFont(font_type.deriveFont(20f));

    // Sign up bars
    lbl_su_usrname.setFont(font_type.deriveFont(30f));
    lbl_su_usrname.setForeground(Color.decode("#ffffff"));

    lbl_su_pswrd.setFont(font_type.deriveFont(30f));
    lbl_su_pswrd.setForeground(Color.decode("#ffffff"));

    // Log In Bars
    lbl_li_usrname.setFont(font_type.deriveFont(30f));
    lbl_li_usrname.setForeground(Color.decode("#ffffff"));

    lbl_li_pswrd.setFont(font_type.deriveFont(30f));
    lbl_li_pswrd.setForeground(Color.decode("#ffffff"));

    cbLiShowHide.setFont(font_type);
    cbLiShowHide.setBackground(bgColor);
    cbLiShowHide.setForeground(Color.decode("#ffffff"));
    cbLiShowHide.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          txtLiPswrd.setEchoChar((char) 0);
        } else {
          txtLiPswrd.setEchoChar(password_bullet);
        }
      }
    });

    cbSuShowHide.setFont(font_type);
    cbSuShowHide.setForeground(Color.decode("#ffffff"));
    cbSuShowHide.setBackground(bgColor);
    cbSuShowHide.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          txtSuPswrd.setEchoChar((char) 0);
        } else {
          txtSuPswrd.setEchoChar(password_bullet);
        }
      }
    });

    // Button Customisation
    btnCreacion.setBorderPainted(true);
    btnCreacion.setPreferredSize(new DimensionUIResource(300, 45));
    btnCreacion.setBorder(BorderFactory.createLineBorder(Color.white));
    btnCreacion.setBackground(Color.decode("#e8c010"));
    btnCreacion.setForeground(Color.decode("#ffffff"));
    btnCreacion.setFont(font_type.deriveFont(font_type.getStyle() ^ Font.BOLD, 30f));
    btnCreacion.setFocusPainted(false);
    btnCreacion.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // Info. que se obtiene de la ventana
        String pswrd = String.valueOf(txtSuPswrd.getPassword());
        User u = new User(txtSuUser.getText(), pswrd);

        if (txtSuUser.getText().equals("") || pswrd.equals("")) {
          JOptionPane.showMessageDialog(warningDuplicate, "Error! No ha rellenado todas las areas requiridas", "Error!",
              JOptionPane.WARNING_MESSAGE);
          dispose();
          setLoggedInUser(null);
        } else {

          Gson gsonRegister = new GsonBuilder().setPrettyPrinting().create();

          try {
            //Caso en el que inicialmente exista el registro 
            if (boolExistsRegister == true) {

              // This is the sign up so we have to check for existing usernames
              for (User registeredUsers : userData) {

                if (txtSuUser.getText().equals(registeredUsers.getUsuario())) {
                  JOptionPane.showMessageDialog(warningDuplicate, "Este usuario ya existe, por favor inicie sesión",
                      "Alert", JOptionPane.WARNING_MESSAGE);
                  txtSuUser.setText("");
                  txtSuPswrd.setText("");
                  boolDuplicateUser = true;
                  setLoggedInUser(null);
                }
              }

              if (boolDuplicateUser == false) {

                try (Writer writer = new FileWriter("resources/files/userData/register.json")) {
                  userData.add(u);
                  gsonRegister.toJson(userData, writer);

                  JOptionPane.showMessageDialog(loggedInAlert, "Enhorabuena! Se ha creado tu perfil!", "Success!",
                      JOptionPane.INFORMATION_MESSAGE);

                  Register.this.setVisible(false);

                  loggedInUser = new UserFeatures(txtSuUser.getText(), new String(txtSuPswrd.getPassword()));
                  setLoggedInUser(loggedInUser);

                  System.out.println("Usuario Creado => " + loggedInUser.toString());

                  System.out.println("Creamos la nueva ventana");
                  new HomeScreen(Register.this);

                }

                userDataLink.append(txtSuUser.getText());

                Files.createDirectories(Paths.get(userDataLink.toString()));

                userDataLink.append("/").append(txtSuUser.getText()).append("DataFile.json");

                try (Writer userDataWriter = new FileWriter(userDataLink.toString())) {

                  // Le tenemos que pasar objetos no?
                  gsonRegister.toJson(loggedInUser, userDataWriter);
                }

              }

            } else {
              //Caso en el que no exista el registro 
              try (Writer writer = new FileWriter("resources/files/userData/register.json")) {

                //Creacion del registro
                userData.add(u);
                gsonRegister.toJson(userData, writer);

                JOptionPane.showMessageDialog(warningDuplicate, "Enhorabuena! Se ha creado tu perfil!", "Success!",
                    JOptionPane.INFORMATION_MESSAGE);

                // Esto es lo que causa que desaparezca la ventana
                Register.this.setVisible(false);

                loggedInUser = new UserFeatures(txtSuUser.getText(), new String(txtSuPswrd.getPassword()));
                setLoggedInUser(loggedInUser);
                // Generate the json profile for the user
                // Build a link based on the username
                new HomeScreen(Register.this);

                Files.createDirectories(Paths.get(userDataLink.toString()));

                userDataLink.append("/").append(txtSuUser.getText()).append("DataFile.json");

                try (Writer userDataWriter = new FileWriter(userDataLink.toString())) {
                  gsonRegister.toJson(loggedInUser, userDataWriter);
                }
              }
            }

            // Debemos de hacer esto para poder reiniciar el proceso sino, al cambiar el
            // usuario tras un duplicado no nos va a escribir el
            // Usuario Correcto
            boolDuplicateUser = false;
            userDataLink = new StringBuilder("resources/files/userData/users/");
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }

      }
    });

    // Button Behaviour
    btnSignIn.setBorderPainted(true);
    btnSignIn.setPreferredSize(new DimensionUIResource(300, 45));
    btnSignIn.setBorder(BorderFactory.createLineBorder(Color.white));
    btnSignIn.setBackground(Color.decode("#e8c010"));
    btnSignIn.setForeground(Color.decode("#ffffff"));
    btnSignIn.setFont(font_type.deriveFont(font_type.getStyle() ^ Font.BOLD, 30f));
    btnSignIn.setFocusPainted(false);
    btnSignIn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        boolean boolUserFound = false;
        // Check that the user in the textBox matches with the one we've inserted
        for (User registeredUsers : userData) {

          // UserFound, good stuff
          if (txtLiUser.getText().equals(registeredUsers.getUsuario())) {
            boolUserFound = true;

            String pswrdText = new String(txtLiPswrd.getPassword());

            if (pswrdText.equals(registeredUsers.getClave())) {
              // Comprobamos que la contraseña coincida
              JOptionPane.showMessageDialog(warningDuplicate, "Enhorabuena! Se ha iniciado sesión!", "Success!",
                  JOptionPane.INFORMATION_MESSAGE);
              // Tenemos que leer el archivo correspondiente y sacar el objeto y asignarlo al
              // registro

              // Archivo a leer
              // Tenemos que re-escribir el archivo
              Gson gsonRegister = new GsonBuilder().setPrettyPrinting().create();

              // StringBuilder para el acceso del archivo
              StringBuilder sb = new StringBuilder("resources/files/userData/users/");
              sb.append(txtLiUser.getText()).append("/").append(txtLiUser.getText()).append("DataFile.json");

              try {
                BufferedReader br = new BufferedReader(new FileReader(sb.toString()));
                //A ver, en el archivo, vamos a tener un userFeatures
                UserFeatures uf = gsonRegister.fromJson(br, UserFeatures.class);

                setLoggedInUser(uf);
                Register.this.setVisible(false);
                new HomeScreen(Register.this);

              } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }

              //Ahora que tenemos el path, debemos de leer el objeto
            

            } else {
              txtLiPswrd.setText("");
              JOptionPane.showMessageDialog(warningDuplicate, "Usuario encontrado, pero la contraseña es incorrecta",
                  "Error!", JOptionPane.WARNING_MESSAGE);

              setLoggedInUser(null);
            }
          }
        }

        if (boolUserFound == false) {
          JOptionPane.showMessageDialog(warningDuplicate, "Este usuario no existe, por favor registresé!", "Alert",
              JOptionPane.WARNING_MESSAGE);
          setLoggedInUser(null);
        }
      }
    });

    // Trabajamos en el Sign Up panel
    pnlSignUp.add(lbl_su_usrname);
    pnlSignUp.add(txtSuUser);
    pnlSignUp.add(lbl_su_pswrd);
    pnlSignUp.add(txtSuPswrd);
    pnlSignUp.add(cbSuShowHide);
    pnlSignUp.add(btnCreacion);
    pnlSignUp.setBackground(bgColor);

    pnlLogIn.add(lbl_li_usrname);
    pnlLogIn.add(txtLiUser);
    pnlLogIn.add(lbl_li_pswrd);
    pnlLogIn.add(txtLiPswrd);
    pnlLogIn.add(cbLiShowHide);
    pnlLogIn.add(btnSignIn);
    pnlLogIn.setBackground(bgColor);

    // Panel Structure within window
    tp.add("Registrate", pnlSignUp);
    tp.add("Iniciar Sesion", pnlLogIn);
    tp.setBorder(null);
    tp.setBackground(Color.LIGHT_GRAY);
    tp.setForeground(new Color(255, 255, 255));
    tp.setFont(font_type.deriveFont(40f));

    // Caracteristicas de la ventana
    this.add(tp);
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setSize(500, 700);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setVisible(false);

    /*
     * this.addWindowListener( new WindowAdapter() {
     * 
     * @Override public void windowClosing(WindowEvent we) { new HomeScreen(); } }
     * );
     */

  }

  /**
   * @param loggedInUser
   * 
   *                     Manera de indicarle al programa si un usuario se ha
   *                     metido o no
   */
  public void setLoggedInUser(UserFeatures loggedInUser) {
    this.loggedInUser = loggedInUser;

  }

  /**
   * @return UserFeatures
   * 
   * 
   * 
   */
  public UserFeatures getLoggedInUser() {
    return loggedInUser;
  }

}
