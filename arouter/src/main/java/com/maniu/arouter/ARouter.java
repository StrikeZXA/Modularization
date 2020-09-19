package com.maniu.arouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * @author WEN
 * @Description:
 * @date 2020/9/14 16:39
 */
public class ARouter {

    private static ARouter aRouter = new ARouter();

    //装载了所有Activity类对象
    private Map<String,Class<? extends Activity>> maps;

    //上下文
    private Context context;

    private ARouter(){
        maps = new HashMap<>();
    }

    public static ARouter getInstance(){
        return aRouter;
    }

    public void init(Context context){
        this.context = context;
        List<String> classNames = getClassName("com.maniu.util");
        for(String className:classNames){
            try {
                Class<?> aClass = Class.forName(className);
                if(IRouter.class.isAssignableFrom(aClass)){
                    IRouter iRouter = (IRouter)aClass.newInstance();
                    iRouter.putActivity();
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getClassName(String packgeName) {
        List<String> classList = new ArrayList<>();
        String path = null;
        try {
            //通过包管理器获取到应用信息类然后获取apk的完整路径
            path = context.getPackageManager().getApplicationInfo(context.getPackageName(),0).sourceDir;
            //根据APK的完整路径获取到编译后的dex文件目录
            DexFile dexfile = new DexFile(path);;
            //获得编译后的dex文件中所有的classs
            Enumeration<String> entries = dexfile.entries();
            //然后进行遍历
            while (entries.hasMoreElements()){
                //通过遍历素有的class的包名
                String name = entries.nextElement();
                if(name.contains(packgeName)){
                    //如果符合就添加到集合中
                    classList.add(name);
                }
            }
        } catch (PackageManager.NameNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return classList;
    }

    /**
     * 跳转窗体的方法
     * @param key
     * @param bundle
     */
    public void jumpActivity(String key, Bundle bundle,Context context){
        Class<? extends Activity> activityClass =  maps.get(key);
        if(activityClass!=null){
            Intent intent = new Intent().setClass(context,activityClass);
            if(bundle!=null){
                intent.putExtra(key,bundle);
            }
            context.startActivity(intent);
        }
    }

    /**
     * 跳转窗体的方法
     * @param key
     * @param context
     */
    public void jumpActivity(String key,Context context){
        jumpActivity(key, null , context);
    }
    /**
     * 将Activity class添加进map
     */
    public void addActivity(String key,Class<? extends Activity> clazz){
        if(key!=null && clazz!=null && !maps.containsKey(key)){
            maps.put(key,clazz);
        }
    }

}
