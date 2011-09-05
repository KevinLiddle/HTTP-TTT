package Handlers;

import java.io.BufferedReader;

public class GUIHandler extends Handler{

  public static synchronized BufferedReader stylesheets(String request) throws Exception {
    return readFile("stylesheets", request);
  }

  public static synchronized BufferedReader javascripts(String request) throws Exception {
    return readFile("javascripts", request);
  }

  public static synchronized BufferedReader images(String request) throws Exception {
    return readFile("images", request);
  }
}