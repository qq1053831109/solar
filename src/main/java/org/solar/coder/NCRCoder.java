package org.solar.coder;

public class NCRCoder {
    public static String decode(String str) {
        StringBuffer sb = new StringBuffer();
        int i1 = 0;
        int i2 = 0;


        while (i2 < str.length()) {
            i1 = str.indexOf("&#", i2);
            if (i1 == -1) {
                sb.append(str.substring(i2));
                break;
            }
            sb.append(str.substring(i2, i1));
            i2 = str.indexOf(";", i1);
            if (i2 == -1) {
                sb.append(str.substring(i1));
                break;
            }


            String tok = str.substring(i1 + 2, i2);
            try {
                int radix = 10;
                if (tok.charAt(0) == 'x' || tok.charAt(0) == 'X') {
                    radix = 16;
                    tok = tok.substring(1);
                }
                sb.append((char) Integer.parseInt(tok, radix));
            } catch (NumberFormatException exp) {
                //sb.append(unknownCh);
            }
            i2++;
        }
        return sb.toString();
    }

}
