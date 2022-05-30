package database;

import java.sql.Connection;
import java.util.List;

import util.ConnectionFactory;

public interface Idao<T> {
  public final Connection con = new ConnectionFactory().get();

  public List<T> all();

  public List<T> list(String param);

  public T get(int id);

  public void create(T t);

  public void update(T t);

  public void remove(T t);
}
