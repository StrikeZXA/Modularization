# 组件化开发和自定义的ARouter的应用

## 组件化开发
### 资源统一管理
- 根目录新建config.gradle配置文件
- 把需要的资源配置版本信息等都填写进去
- 根目录的build.gradle添加apply from "config.gradle",
这样app和module的build.gradle可以以rootProject..的方式来管理依赖版本
- app的dependencies需要根据自定义布尔值变量is_application来决定是否依赖module,
因为app是不能依赖application,而module是根据is_application来决定自身是module还是application
```
if(!rootProject.ext.android.is_application.toBoolean()){
             implementation project(rootProject.ext.dependencies.login)
             implementation project(rootProject.ext.dependencies.member)
}
```
- module需要做的事情,如login：
   - 根据is_application切换是module还是application
```
  if(rootProject.ext.android.is_application.toBoolean()){
      apply plugin: 'com.android.application'
  }else {
      apply plugin: 'com.android.library'
  }
```

   - 根据is_application切换是否具有applicationId属性,module是没有applicationId的
```
  if(rootProject.ext.android.is_application.toBoolean()){
     applicationId "com.maniu.login"
  }
```

   - 创建'src/main/manifest/AndroidManifest.xml'和'src/main/module',这样可以根据is_application切换不同的AndroidManifest.xml和Application,
```
sourceSets{
        main{
            if(rootProject.ext.android.is_application.toBoolean()){
                manifest.srcFile 'src/main/AndroidManifest.xml'
                java.srcDirs 'src/main/module'
            }else {
                manifest.srcFile 'src/main/manifest/AndroidManifest.xml'
            }
        }
    }
```


