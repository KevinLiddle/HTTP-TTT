package Application;

import Handlers.Handler;

public abstract class Application {

  public final String OK = "200 OK";
  public final String NOT_FOUND = "404 Not Found";
  public Handler handler;

  public abstract String serverResponse(String[] request) throws Exception;

  public abstract String status(String URI) throws Exception;

}
