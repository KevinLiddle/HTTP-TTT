package Handlers;

import java.io.BufferedReader;

public class HomeHandler extends Handler {

  public static synchronized BufferedReader home(String request) throws Exception {
    return readFile("home.html");
  }
}