package com.mybatis.dao;

import com.mybatis.pojo.Dog;
import com.mybatis.pojo.DogTag;

public interface DogDao 
{
  //使用级联属性封装联合查询
  public Dog getDogInfoById(String dogId);
  //分布查询
  //查询dog的信息根据dogid
  public Dog getDogInfoByIdSimple(String dogId);
  //查询dogTag的信息根据id
  public DogTag getDogTagInfoByIdSimple(String Id);
}
