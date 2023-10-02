package stepDefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//Through junit
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/java/features",
        glue = "stepDefinitions",
       tags = ("not @ignore, @smoke"),
        plugin = {"pretty", "html:target/report.html"}
)
public class Runner {

}
