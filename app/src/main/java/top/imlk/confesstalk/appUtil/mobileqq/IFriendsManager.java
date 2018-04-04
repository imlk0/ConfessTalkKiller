package top.imlk.confesstalk.appUtil.mobileqq;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;


public class IFriendsManager {

    public static Object getFriendsManager(Object qqAppInterface) throws InvocationTargetException, IllegalAccessException {
        return IMember.METHOD.qqAppInterfaceClass_getManager.invoke(qqAppInterface, 50);
    }

    public static ConcurrentHashMap getFriendsConcurrentHashMap(Object friendsManager) throws IllegalAccessException, NoSuchFieldException {
        for (Field field : IMember.CLASS.friendsManagerClass.getDeclaredFields()) {
            if (ConcurrentHashMap.class == field.getType()) {
                field.setAccessible(true);
                ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) field.get(friendsManager);
                if (concurrentHashMap.size() > 0) {
                    if (concurrentHashMap.get(concurrentHashMap.keySet().toArray()[0]).getClass() == IMember.CLASS.friendsClass) {
                        return concurrentHashMap;
                    }
                }
            }
        }
        throw new NoSuchFieldException();
    }

}
