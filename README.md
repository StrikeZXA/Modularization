# 组件化开发和自定义的ARouter的应用

## 组件化开发
### 资源统一管理
- 根目录新建config.gradle配置文件
- 把需要的资源配置版本信息等都填写进去
- 根目录的build.gradle添加apply from "config.gradle",
这样app和module的build.gradle可以以rootProject..的方式来管理依赖版本
- app的build.gradle需要根据自定义布尔值变量is_application来决定是否依赖module,
因为app是不能依赖application,而module是根据is_application来决定自身是module还是application
```
if(!rootProject.ext.android.is_application.toBoolean()){
             implementation project(rootProject.ext.dependencies.login)
             implementation project(rootProject.ext.dependencies.member)
}
```





