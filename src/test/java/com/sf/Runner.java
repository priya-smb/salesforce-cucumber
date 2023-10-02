package com.sf;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//Through junit
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "com/sf/stepdefs",
//        tags = "not @ignore",
        plugin = {"pretty", "html:target/report.html"}
)
public class Runner {

}
