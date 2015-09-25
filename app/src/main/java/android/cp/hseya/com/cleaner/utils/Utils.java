package android.cp.hseya.com.cleaner.utils;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ayesh on 5/19/2015.
 */
public class Utils {


    public static String stringToMD5(String message) {

        String digest = null;

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {

                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            digest = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return digest;

    }


    public static String  getCurrentDateOnly()throws  Exception{
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            return dateFormat.format(date);

        }catch (Exception e){
            throw  e;
        }
    }
}
