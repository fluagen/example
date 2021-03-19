package com.cnebula.example.mybatis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnebula.example.mybatis.dataobject.Users;
import com.cnebula.example.mybatis.mapper.UsersMapper;

@SpringBootTest
public class JsonbTests {

	@Autowired
	UsersMapper usersMapper;
	
	@Test
    public void test() {
		Users jone = usersMapper.selectById(1);
        System.out.println(jone);

        Users jack = usersMapper.selectById(2);
        System.out.println(jack);
        
        QueryWrapper<Users> wrapper = new QueryWrapper<Users>().apply("wallet ->> 'name' = '支付宝钱包' ");
        Users obj = usersMapper.selectOne(wrapper);
        System.out.println(obj);
    }
	
}
