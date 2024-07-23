package model;

import java.util.List;

public interface DAO<T> {

  // Different CRUD functions for the tables
  // Having a general DAO allows program to work with more than one table
  public List<T> findAll();

  public T findById(Integer id);

  public T insert(T t);

  public T update(Integer id, T t);

  public boolean delete(Integer id);
  
}
