package Handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

public abstract class Handler {

  final static String viewsRoot = "src/views/";

  public static synchronized BufferedReader execute(String request) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(new File("src/config/routes.txt")));
    String line = br.readLine();
    while(line != null){
      String[] route = line.split("\\s*->\\s*");
      if(request.matches(route[0])){
        return callMethod(route[1], request);
      }
      line = br.readLine();
    }
    return notFound(request);
  }

  public static synchronized BufferedReader notFound(String request) throws Exception {
    return readFile("404NotFound.html");
  }

  protected static synchronized BufferedReader readFile(String location) throws Exception {
    return readFile(location, "");
  }

  protected static synchronized BufferedReader readFile(String location, String request) throws Exception {
    String[] route = request.split("/");
    request = route[route.length - 1];
    return new BufferedReader(new FileReader(new File(viewsRoot + location + "/" + request)));
  }

  protected static synchronized int getID(String request) {
    return Integer.parseInt(request.split("/")[1]);
  }

  private static synchronized BufferedReader callMethod(String methodName, String request) throws Exception {
    String[] route = methodName.split("#");
    Class<?> handlerClass = Class.forName("Handlers." + route[0]);
    Method method = handlerClass.getMethod(route[1], String.class);
    return (BufferedReader) method.invoke(null, request);
  }
}
