package util;

import java.io.*;
import java.util.*;

public class PropertyUtil {

	public static String getConnectionString(String fname) {
        Properties pr = new Properties();
        try (FileInputStream f = new FileInputStream(fname)) 
        {
            pr.load(f);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        String url = pr.getProperty("db.url");
        String username = pr.getProperty("db.username");
        String password = pr.getProperty("db.password");

        return url + "?user=" + username + "&password=" + password;
    }
}