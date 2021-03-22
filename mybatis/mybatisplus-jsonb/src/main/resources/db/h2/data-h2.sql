DELETE FROM users;

INSERT INTO users (id, name, age, email, wallet, addresses) VALUES
(1, 'Jone', 18, 'test1@baomidou.com', '{
    "name": "支付宝钱包",
    "level": 2,
    "currencies": [{
        "type": "USD",
        "amount": 999.19
    },{
        "type": "RMB",
        "amount": 1000.19
    }]
}', '[{
    "state":"中国",
    "province":"北京",
    "city":"北京",
    "street":"海淀区成府路126号",
    "zipCode":"100080"
    },{
    "state":"中国",
    "province":"北京",
    "city":"北京",
    "street":"朝阳区三里屯19号",
    "zipCode":"100090"
}]'),
(2, 'Jack', 20, 'test2@baomidou.com', '{
    "name": "微信钱包",
    "level": 6,
    "currencies": [{
        "type": "USD",
        "amount": 888.18
    },{
        "type": "RMB",
        "amount": 1000.18
    }]
}', '[{
    "state":"中国",
    "province":"上海",
    "city":"上海",
    "street":"浦东新区川沙镇黄赵路310号",
    "zipCode":"123443",
    "address1":""
}]'
);