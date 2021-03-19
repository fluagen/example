package com.cnebula.example.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnebula.example.mybatis.dataobject.Users;

@Mapper
public interface UsersMapper extends BaseMapper<Users>{

}
