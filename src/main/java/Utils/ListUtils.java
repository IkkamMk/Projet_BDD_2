/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.List;

/**
 *
 * @author gille
 */
public class ListUtils {
    @FunctionalInterface
    public interface ElemFormatter<E> {

        public String format(E elem);
    }
    public static <E> String enumerateList(List<? extends E> list,
            String beforeNum, int debutNumerotation, String betweenNumAndVal, String afterVal,
            ElemFormatter<E> formatter) {
        StringBuilder res = new StringBuilder();
        if (list.size() != 0) {
            int nbrDigit = (int) Math.floor(Math.log10(list.size())) + 1;
            String beforeOtherLines = StringUtil.mult(" ", beforeNum.length() + nbrDigit) + betweenNumAndVal;
            for (int i = 0; i < list.size(); i++) {
                String beforeFirstLine = beforeNum
                        + String.format("%" + nbrDigit + "d", (i + debutNumerotation))
                        + betweenNumAndVal;
                res.append(StringUtil.specialIndent(formatter.format(list.get(i)), beforeFirstLine, beforeOtherLines));
                res.append(afterVal);
            }
        }
        return res.toString();
    }
    public static String enumerateList(List<? extends Object> list) {
        return enumerateList(list, "", 1, " : ", "\n", Object::toString);
    }
    
    public static <E> String enumerateList(List<? extends E> list,ElemFormatter<E> formatter) {
        return enumerateList(list, "", 1, " : ", "\n", formatter);
    }
}
