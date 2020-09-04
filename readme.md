# 基于JWT的SSO单点登陆

#### 单点登陆原理
访问登录服务,登录成功后,将jwt token写入到cookie中,  
cookie的domain范围设置为sso.com,  
这样sso.com下所有的子域名,都能拿到该token,从而实现单点登录功能  

#### 项目结构
JssCommon 通用工具包  
JssLogin 登录服务 通过`www.login.sso.com`访问  
JssWebAbc 服务一 通过`www.abc.sso.com`访问  
JssWebMno 服务二 通过`www.abc.ssn.com`访问  

#### 数据准备
修改host文件
```
127.0.0.1 www.login.sso.com
127.0.0.1 www.abc.sso.com
127.0.0.1 www.mno.sso.com
127.0.0.1 www.zxu.com
```
#### 启动程序（无先后顺序）
MnoApplication  
AbcApplication  
JssLoginApplication  


#### 测试SSO
1: 访问下面受保护资源，发现无法访问
www.abc.sso.com:8082/Abc/showAbc  
www.mno.sso.com:8083/Mno/accessMno  

2: 访问登录页面,输入默认用户名zxu 密码4862登录
www.login.sso.com:8081/Login/loginPage  

3: 继续访问受保护资源,发现可以访问
www.abc.sso.com:8082/Abc/showAbc  
www.mno.sso.com:8083/Mno/accessMno  

4: 访问以下资源,发现无法访问,因为cookie的domain范围设置为sso.com,但是zxu.com并不在该范围内,所以访问失败
www.zxu.com:8082/Abc/showAbc  
127.0.0.1:8082/Abc/showAbc  

#### 技术总结
+ JWT有多种实现方式,这里使用最流行的jjwt  
+ 使用thymeleaf模版引擎,避免不必要的前端开发  

#### 关于jwt密钥签名
JwtUtil.keyPair动态生成secrect,用户token加密,keypair保存在redis中或使用其他方案,  
只要保证多个微服务用到的是同一个keypair即可  
这里固定一个keypair保存在配置文件里,为了的方便演示,但是一旦keypair泄漏,用户便可自行签发jwt,造成安全隐患  

#### 补充说明
jwt生效时间为1h,为了防止在用户访问程序起见jwt失效导致无法正常访问,  
则需要在jwt超时的时候,判断是否已超过jwt最长生命周期(2h),  
如果在jwt最长生命周期内则刷新jwt  
如果超过jwt最长生命周期,则用户需要重新登录  
注意：jwt存在cookie中,如果cookie销毁,则用户也需要重新登录

#### 代办工作
1: 需要为jwt claim设置生命周期上限，一旦超过上限就不允许在刷新(重新获取)token
