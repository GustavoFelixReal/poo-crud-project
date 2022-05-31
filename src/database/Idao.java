package database;

import java.util.List;

public interface Idao<T> {

  public List<T> all();

  public List<T> list(String param);

  public T get(int id);

  public void create(T t);

  public void update(T t);

  public void remove(T t);
}
