package basePackage;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

import static org.apache.log4j.Logger.getLogger;

public class BaseSetup {
    public static RequestSpecification httpsRequest;
    public static Response response;
    public Logger logger;

    @BeforeClass
    public void setup() throws IOException {

        logger=getLogger("RestAssueredAutomation");
       /*//code to load property file
        Properties properties=new Properties();
        properties.load(new FileInputStream("Log4J.properties"));
        PropertyConfigurator.configure(properties);*/
        PropertyConfigurator.configure("Log4J.properties");
        logger.setLevel(Level.DEBUG);
    }
}
