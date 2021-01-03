package pl.wwsis.MicroBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = "pl.wwsis.MicroBlog")
public class App 
{
    public static void main( String[] args )
    {
    	
    	SpringApplication.run(App.class, args);
          
    }
}
