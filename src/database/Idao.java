package src.database;

import java.util.List;

public interface Idao<T> {

  public List<T> all();

  public List<T> list(String param);

  public T get(int id);

  public boolean create(T t);

  public void update(T t);

  public void remove(T t);
}
