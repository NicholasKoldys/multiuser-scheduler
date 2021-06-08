package dev.nicholaskoldys.multiuserscheduler.service;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author nicho
 */
public class LocalizationService {
    
    public static ResourceBundle activeResourceBundle;
    
    static {
        
        if (Locale.getDefault() == Locale.US) {
            try {
            activeResourceBundle = 
                ResourceBundle.getBundle(
                "MessagesBundle_en_US", Locale.getDefault());
            } catch (Exception ex) {
                System.out.println("Local file not found");
            }
        } else if (Locale.getDefault() == Locale.FRANCE) {
            try {
            activeResourceBundle = 
                ResourceBundle.getBundle(
                "MessagesBundle_fr_FR", Locale.getDefault());
            } catch (Exception ex) {
                System.out.println("Local file not found");
            }
        }
    }
    
    public static void changeLocale(String selectedLocale) {

        if (selectedLocale.equals("englishMenuItem")) {
            try {
            activeResourceBundle = 
                ResourceBundle.getBundle(
                "MessagesBundle_en_US", Locale.ENGLISH);
            } catch (Exception ex) {
                System.out.println("Local file not found");
            }
        } else if (selectedLocale.equals("frenchMenuItem")) {
            try {   
            activeResourceBundle = 
                ResourceBundle.getBundle(
                "MessagesBundle_fr_FR", Locale.FRENCH);
            } catch (Exception ex) {
                System.out.println("Local file not found");
            }
        }     
    }
}
