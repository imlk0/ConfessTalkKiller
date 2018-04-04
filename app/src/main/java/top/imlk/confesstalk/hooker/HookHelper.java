package top.imlk.confesstalk.hooker;

import android.app.ActivityManager;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import de.robv.android.xposed.XposedHelpers;


public class HookHelper {


    public static Class<?> findClass(String className, ClassLoader classLoader) {

        return XposedHelpers.findClass(className, classLoader);
    }

    public static Field findFirstFieldByAncestorType(Class srcClass, Class ancestorClass) {

        Class temp;
        for (Field field : srcClass.getDeclaredFields()) {
            temp = field.getType();
            if (temp == ancestorClass) {
                field.setAccessible(true);
                return field;
            }

            while ((temp = temp.getSuperclass()) != null) {
                if (temp == ancestorClass) {
                    field.setAccessible(true);
                    return field;
                }
            }
        }

        return null;
    }

    public static Field findFirstFieldByExactType(Class clazz, Class type) {
        return XposedHelpers.findFirstFieldByExactType(clazz, type);
    }

    public static Object callStaticMethod(Class clazz, String methodName, Class<?>[] parameterTypes, Object... args) {
        return XposedHelpers.callStaticMethod((Class<?>) clazz, methodName, parameterTypes, args);
    }

    public static Object callMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object... args) {
        return XposedHelpers.callMethod(obj, methodName, parameterTypes, args);
    }

    public static Object callMethod(Object obj, String methodName, Object... args) {
        return XposedHelpers.callMethod(obj, methodName, args);
    }

    public static Method findBestMatchMethod(Class srcClass, String name, Class returnType, Class... paramTypes) {
//        Log.e("HookHelper", "ClassName:" + ((Class)srcClass).getName() + " Package:" + ((Class)srcClass).getPackage().getName());

        //getDeclaredMethods获取本类中的方法
        for (Method method : ((Class) srcClass).getDeclaredMethods()) {
//            Log.e("HookHelper", "name:" + method.getName());
//            Log.e("HookHelper", "returnType:" + method.getReturnType().getName());
//
//            Class[] classes = method.getParameterTypes();
//
//            for (int index = 0; index < classes.length; index++) {
//                Log.e("HookHelper", "parameterType " + index + ":" + classes[index].getName());
//            }

            if ((method.getName().equals(name)) && (method.getReturnType() == returnType) && (paramTypes.length == method.getParameterTypes().length)) {

                Class[] methodParamterTypes = method.getParameterTypes();
                boolean ok = true;
                for (int index = paramTypes.length - 1; index >= 0; index--) {

                    if (paramTypes[index] != methodParamterTypes[index]) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    method.setAccessible(true);
                    return method;
                }
            }
        }
        throw new NoSuchMethodError();
    }


    public static Field findBestMacthField(Class srcClass, String name, Class type) {
        for (Field field : srcClass.getDeclaredFields()) {

            if ((field.getName().equals(name)) && (field.getType() == type)) {
                field.setAccessible(true);
                return field;
            }
        }
        throw new NoSuchFieldError();
    }


    public static boolean isMainProcess(Context context) {
        return context.getPackageName().equals(getProcessName(context));
    }

    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

}
