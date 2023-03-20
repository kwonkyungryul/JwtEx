package shop.mtcoding.jwtstudy.example;

import org.junit.jupiter.api.Test;

public class envTest {
    @Test
    public void env_test() {
        String myvar = System.getenv("JAVA_HOME");
        System.out.println(myvar);
    }
}
