package Handlers;

import java.io.*;

public abstract class Handler {

  final String viewsRoot = "src/views/";

  public abstract BufferedReader execute(String request) throws Exception;

  public BufferedReader notFound(String request) throws Exception {
    return new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "404NotFound.html")));
  }

  public BufferedReader stylesheets(String request) throws Exception {
    return new BufferedReader(new FileReader(new File(viewsRoot + "stylesheets" + request)));
  }

  public BufferedReader javascripts(String request) throws Exception {
    return new BufferedReader(new FileReader(new File(viewsRoot + "javascripts" + request)));
  }
}
