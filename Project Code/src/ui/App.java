package ui;

import io.UserIO.Register;
import ui.AppManager.HomeScreen;

public class App {
    
    public static Register registerPublic = new Register();

    
    /** 
     * @param args
     * 
     * Programa que inicia todo el programa
     */
    public static void main(String[] args)
    {
        new HomeScreen(registerPublic);
    }
}
