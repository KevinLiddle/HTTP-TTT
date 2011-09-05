package specs.Handlers;

import java.io.BufferedReader;

public class HandlerTestHelpers {

  public static boolean gamePage(BufferedReader br) throws Exception {
    return page(br, "table");
  }

  public static boolean notFoundPage(BufferedReader br) throws Exception {
    return page(br, "Not Found");
  }

  public static boolean gameInProgress(BufferedReader br) throws Exception {
    String document = readDocument(br);
    return (document.contains("table") && document.contains("X"));
  }

  public static boolean page(BufferedReader br, String pageElement) throws Exception {
    return readDocument(br).contains(pageElement);
  }

  public static String readDocument(BufferedReader br) throws Exception {
    String line = br.readLine();
    String document = "";
    while(line != null){
      document += line + "\n";
      line = br.readLine();
    }
    return document;
  }
}
