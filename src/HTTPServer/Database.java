package HTTPServer;

import java.util.ArrayList;

public class Database {

  private static final Database instance = new Database();
  public ArrayList<Object> table = new ArrayList<Object>();

  private Database(){}

  public static ArrayList<Object> table(){
    return instance.table;
  }
}
