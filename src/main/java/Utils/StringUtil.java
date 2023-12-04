/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author gille
 */
public class StringUtil {
    
    public static String mult(String s, int nbr) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < nbr; i++) {
            res.append(s);
        }
        return res.toString();
    }
    public static String specialIndent(String s, String beforeFirstLine, String beforeOtherLines) {
        StringBuilder res = new StringBuilder();
        res.append(beforeFirstLine);
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            res.append(cur);
            if (cur == '\n') {
                res.append(beforeOtherLines);
            }
        }
        return res.toString();
    }
}
