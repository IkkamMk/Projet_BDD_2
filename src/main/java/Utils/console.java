/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author gille
 */
public class console {
    public static BufferedReader myinput = new BufferedReader(new InputStreamReader(System.in));
    
    
    public static double entreeDouble(String s) {
        boolean encore = true;
        double res = 0;

        while (encore) {
            try {
                System.out.println(s);
                res = Double.parseDouble(myinput.readLine());
                encore = false;
            } catch (IOException e) {
                throw new Error(e);
            } catch (NumberFormatException e) {
                System.out.println("flottant non valide, essayez encore");
                encore = true;
            }
        }
        return res;
    }
    public static int entreeEntier(String s) {
        boolean encore = true;
        int res = 0;

        while (encore) {
            try {
                System.out.println(s);
                res = Integer.parseInt(myinput.readLine());
                encore = false;
            } catch (IOException e) {
                throw new Error(e);
            } catch (NumberFormatException e) {
                System.out.println("entier non valide, essayez encore");
                encore = true;
            }
        }
        return res;
    }
    public static String entreeString(String s) {
        String res = null;

        try {
            System.out.println(s);
            res = myinput.readLine();
        } catch (IOException e) {
            throw new Error(e);
        }
        return res;
    }
}

