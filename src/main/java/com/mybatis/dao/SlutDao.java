package com.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mybatis.pojo.Slut;

public interface SlutDao 
{
    public List<Slut> getAllSlutInfo();
    public List<Slut> getSlutInfo(Slut slut);
    public List<Slut> getSlutInfoIn(@Param("names") List<String> names);
    public List<Slut> getSlutInfoChoose(Slut slut);
    public void updateSlutInfo(Slut slut);
}
