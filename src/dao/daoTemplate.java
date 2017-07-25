package dao;

import java.util.List;

public interface daoTemplate<T> {
 public int save(T t)throws Exception;
 public int delete(T t)throws Exception;
 public int delete(Integer id)throws Exception;
 public int update(T t)throws Exception;
 public List<T> select()throws Exception;
 public T get(Integer id)throws Exception;
}
