DELETE FROM users;
INSERT INTO users (id, name, age, email, wallet) VALUES
(1, 'Jone', 18, 'test1@baomidou.com', '{
    "name": "支付宝钱包",
    "level": 3,
    "currencies": [{
        "type": "USD",
        "amount": 999.19
    },{
        "type": "RMB",
        "amount": 1000.19
    }]
}'),
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
}');