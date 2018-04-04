package top.imlk.confesstalk.appUtil.mobileqq;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import top.imlk.confesstalk.hooker.HookHelper;

/**
 * Created by imlk on 2018/4/3.
 */

public class IMember {
    public static class CLASS {
        //Class对象
        public static Class baseChatPieClass;
        //        public static Class sessionInfoClass;
        public static Class xEditTextExClass;
        public static Class fragmentActivityClass;
        public static Class qqAppInterfaceClass;
        public static Class friendsManagerClass;
        public static Class friendsClass;


    }

    public static class FIELD {

        //        public static Field field_a_of_type_ComTencentMobileqqActivityAioSessionInfo;
        public static Field field_a_of_type_AndroidSupportV4AppFragmentActivity;
        public static Field field_a_of_type_ComTencentMobileqqAppQQAppInterface;
        public static Field friendsClass_remark;
//        public static Field qqNumber;

    }

    public static class METHOD {
        public static Method qqAppInterfaceClass_getManager;
    }


    public static void initUtil(ClassLoader classLoader) throws NoSuchMethodException, NoSuchFieldException {
        CLASS.baseChatPieClass = HookHelper.findClass("com.tencent.mobileqq.activity.BaseChatPie", classLoader);//l oadPackageParam.classLoader
//        CLASS.sessionInfoClass = HookHelper.findClass("com.tencent.mobileqq.activity.aio.SessionInfo", classLoader);
        CLASS.xEditTextExClass = HookHelper.findClass("com.tencent.widget.XEditTextEx", classLoader);
        CLASS.fragmentActivityClass = HookHelper.findClass("android.support.v4.app.FragmentActivity", classLoader);
        CLASS.qqAppInterfaceClass = HookHelper.findClass("com.tencent.mobileqq.app.QQAppInterface", classLoader);
        CLASS.friendsManagerClass = HookHelper.findClass("com.tencent.mobileqq.app.FriendsManager", classLoader);
        CLASS.friendsClass = HookHelper.findClass("com.tencent.mobileqq.data.Friends", classLoader);


//        FIELD.field_a_of_type_ComTencentMobileqqActivityAioSessionInfo = HookHelper.findFirstFieldByExactType(CLASS.baseChatPieClass, CLASS.sessionInfoClass);
        FIELD.field_a_of_type_AndroidSupportV4AppFragmentActivity = HookHelper.findFirstFieldByExactType(CLASS.baseChatPieClass, CLASS.fragmentActivityClass);
        FIELD.field_a_of_type_ComTencentMobileqqAppQQAppInterface = HookHelper.findFirstFieldByExactType(CLASS.baseChatPieClass, CLASS.qqAppInterfaceClass);
        FIELD.friendsClass_remark = CLASS.friendsClass.getDeclaredField("remark");
//        FIELD.qqNumber = HookHelper.findBestMacthField(CLASS.sessionInfoClass, "a", String.class);

        METHOD.qqAppInterfaceClass_getManager = CLASS.qqAppInterfaceClass.getDeclaredMethod("getManager", int.class);
        METHOD.qqAppInterfaceClass_getManager.setAccessible(true);


    }
}
