package controller;

import java.sql.Connection;

import util.ConnectionFactory;

public interface IController<T> {
  public final Connection con = new ConnectionFactory().get();

  public void add();

  public void search();

  public boolean validate();
}
