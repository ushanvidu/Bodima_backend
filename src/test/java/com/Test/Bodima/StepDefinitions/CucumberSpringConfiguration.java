
package com.Test.Bodima.StepDefinitions;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import com.Test.Bodima.BodimaApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = BodimaApplication.class)
public class CucumberSpringConfiguration {
}