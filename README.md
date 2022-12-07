用于对接现有的登录插件以适配PixelWeb，不会影响现有的玩家登录数据  
当前仅支持AuthMe登录插件，内置HttpServer，请在配置文件中修改相关配置

指令：  
重启插件/plp reload
  
配置文件
```yaml
# 您的PixelWeb的token，清不要暴露您的token，否则后果很严重！！
token: yourtoken
# 使用的前置登录插件，支持AuthMe，默认自动检测
# type: AuthMe
type: auto
# 登录服务的端口，需要保证端口未被占用！
http:
  port: 8081
```