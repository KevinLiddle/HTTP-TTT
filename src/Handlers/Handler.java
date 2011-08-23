package Handlers;

import java.io.*;
import java.lang.reflect.Method;

public abstract class Handler {

  final String viewsRoot = "src/views/";

  public synchronized BufferedReader execute(String request) throws Exception {
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

  public synchronized BufferedReader notFound(String request) throws Exception {
    return new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "404NotFound.html")));
  }

  public synchronized BufferedReader stylesheets(String request) throws Exception {
    return new BufferedReader(new FileReader(new File(viewsRoot + "stylesheets" + request)));
  }

  public synchronized BufferedReader javascripts(String request) throws Exception {
    return new BufferedReader(new FileReader(new File(viewsRoot + "javascripts" + request)));
  }

  public synchronized BufferedReader images(String request) throws Exception {
    return new BufferedReader(new FileReader(new File(viewsRoot + "images" + request)));
  }

  private synchronized BufferedReader callMethod(String methodName, String request) throws Exception {
    Method method = this.getClass().getMethod(methodName, String.class);
    return (BufferedReader) method.invoke(this, request);
  }
}
