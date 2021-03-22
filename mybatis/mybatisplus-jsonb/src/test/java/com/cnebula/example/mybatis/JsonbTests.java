package com.cnebula.example.mybatis;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cnebula.example.mybatis.dataobject.Address;
import com.cnebula.example.mybatis.dataobject.Users;
import com.cnebula.example.mybatis.mapper.UsersMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class JsonbTests {

	@Autowired
	UsersMapper usersMapper;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
    public void test() {
		Users jone = usersMapper.selectById(1);
        System.out.println(jone);

        Users jack = usersMapper.selectById(2);
        
        // 1. Address声明了@JsonIgnoreProperties，所以可以忽略未知的属性“address1”
        // 2. 泛型的反序列需要使用 TypeReference 指定实际的类型，否则jackson默认反序列为 List<LikedHashMap>
        List<Address> addresses = mapper.convertValue(jack.getAddresses(), new TypeReference<List<Address>>() {
		});
        
        System.out.println(jack);
        System.out.println(addresses);
        
//        QueryWrapper<Users> wrapper = new QueryWrapper<Users>().apply("wallet ->> 'name' = '支付宝钱包' ");
//        Users obj = usersMapper.selectOne(wrapper);
//        System.out.println(obj);
    }
	
}
