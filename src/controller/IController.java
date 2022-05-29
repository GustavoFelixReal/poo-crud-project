package controller;

import java.util.List;

public interface IController<T> {
  public List<T> all();

  public List<T> list(String param);

  public T get(int id);

  public void create(T t);

  public void update(T t);

  public void remove(T t);
}
