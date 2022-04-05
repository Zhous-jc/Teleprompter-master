# Teleprompter

基于springboot + sphinx4 的演讲练习平台

###功能汇总：

#####用户
    登录  √
    退出  √
    注册  ×
    前端验证码  ×
    修改密码  √
    邮箱验证码  √
    绑定邮箱  √
#####演讲稿功能
    word/txt导入  ×
    OCR识别  ×
    写稿资料    ×
    编辑  ×
    导出  ×
#####演讲功能
    演讲学习    ×
    演讲练习    ×
    直播间系统  ×
    演讲回放    ×
#####未来功能
    演讲点评  ×

###接口汇总：
~~~
/login
登录
req： loginName；password
ret： SysUser
~~~
~~~
/logout
登出
~~~
~~~
/changePwd
修改密码
req： newPwd；email
ret： true/false
~~~
~~~
/sendVfCode
发送邮箱验证码
req： email; type
~~~
~~~
/bindEmail
绑定邮箱
req： email; type; vfCode
~~~