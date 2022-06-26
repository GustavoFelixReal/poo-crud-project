package src.controller;

public interface IController<T> {
  public void add();

  public void search();

  public boolean validate();

  public void clear();

  public boolean update();

  public boolean delete();

  public boolean get();
}
