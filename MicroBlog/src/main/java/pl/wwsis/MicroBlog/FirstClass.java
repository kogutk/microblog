package pl.wwsis.MicroBlog;
import static spark.Spark.*;

public class FirstClass {

	public static void main(String[] args) {
		get("/hello", (req, res)->"Hello, gay boys");
	}

}
