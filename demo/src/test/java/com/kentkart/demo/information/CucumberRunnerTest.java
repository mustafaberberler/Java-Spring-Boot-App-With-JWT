package com.kentkart.demo.information;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "json:target/cucumber.json"},
		features = "src/test/resources/features",
		glue = "StepDefinitions"
)
@CucumberContextConfiguration
//@SpringBootTest(classes = CucumberRunnerTest.class)
public class CucumberRunnerTest {

}
