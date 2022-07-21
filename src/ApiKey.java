import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKey {
    public String getApiKey () {

        // Pega a API_KEY do arquivo env.properties
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("env.properties")) {

            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop.getProperty("API_KEY");
    }
}
