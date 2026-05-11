import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import DB.DbConnection;

public class Main {
    public static void main(String[] args) {
    	Properties props = new Properties();
        
        try(InputStream fs = DbConnection.class.getClassLoader().getResourceAsStream("properties-config.properties")) {
        	props.load(fs);
        } catch (IOException e) {
			System.out.println("Errors here: ");
			e.printStackTrace();
		}
    }
}
