package TTTApplication;

public abstract class Application {

  final String OK = "200 OK";
  final String NOT_FOUND = "404 Not Found";

  public abstract String serverResponse(String[] request) throws Exception;

  public abstract String status(String URI);
}
