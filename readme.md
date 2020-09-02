# 基于JWT的SSO单点登陆

#### 数据准备
修改host文件
```
127.0.0.1 www.login.sso.com
127.0.0.1 www.abc.sso.com
127.0.0.1 www.mno.sso.com
127.0.0.1 www.zxu.com
```

#### 测试SSO
1: 访问下面受保护资源，发现无法访问
`www.abc.sso.com:8082/Abc/showAbc`
`www.mno.sso.com:8083/Mno/accessMno`

2: 访问登录页面,输入默认用户名zxu 密码4862登录
`www.login.sso.com:8081/Login/loginPage`

3: 继续访问受保护资源,发现可以访问
`www.abc.sso.com:8082/Abc/showAbc`
`www.mno.sso.com:8083/Mno/accessMno`

4: 访问以下资源,发现无法访问,因为cookie的domain范围设置为sso.com,zxu.com不在该范围内,所以访问失败
`www.zxu.com:8082/Abc/showAbc`

#### 备注
filter和interceptor如何选择,看实际情况

#### 关于jwt密钥签名
JwtUtil.keyPair动态生成secrect,用户token加密,keypair保存在redis中或使用其他方案,只要保证多个微服务用到的是同一个keypair即可
这里固定一个keypair,为了的方便演示,但是一旦keypair泄漏,用户便可自行签发jwt,造成安全隐患
