package top.imlk.confesstalk.appUtil.mobileqq;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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
        //        public static Class chatAdapter1Class;
        public static Class messageForConfessNewsClass;


    }

    public static class FIELD {

        //        public static Field field_a_of_type_ComTencentMobileqqActivityAioSessionInfo;
        public static Field baseChatPieClass_field_a_of_type_AndroidSupportV4AppFragmentActivity;
        public static Field baseChatPieClass_field_a_of_type_ComTencentMobileqqAppQQAppInterface;
        public static Field friendsClass_remark;
        public static Field messageForConfessNewsClass_strConfessorUin;
        public static Field messageForConfessNewsClass_strConfessorNick;
//        public static Field chatAdapter1Class_field_a_of_type_ComTencentMobileqqActivityBaseChatPie;
//        public static Field chatAdapter1Class_field_a_of_type_JavaUtilList;
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
//        CLASS.chatAdapter1Class = HookHelper.findClass("com.tencent.mobileqq.activity.aio.ChatAdapter1", classLoader);
        CLASS.messageForConfessNewsClass = HookHelper.findClass("com.tencent.mobileqq.data.MessageForConfessNews", classLoader);


//        FIELD.field_a_of_type_ComTencentMobileqqActivityAioSessionInfo = HookHelper.findFirstFieldByExactType(CLASS.baseChatPieClass, CLASS.sessionInfoClass);
        FIELD.baseChatPieClass_field_a_of_type_AndroidSupportV4AppFragmentActivity = HookHelper.findFirstFieldByExactType(CLASS.baseChatPieClass, CLASS.fragmentActivityClass);
        FIELD.baseChatPieClass_field_a_of_type_ComTencentMobileqqAppQQAppInterface = HookHelper.findFirstFieldByExactType(CLASS.baseChatPieClass, CLASS.qqAppInterfaceClass);
        FIELD.friendsClass_remark = CLASS.friendsClass.getDeclaredField("remark");
        FIELD.friendsClass_remark.setAccessible(true);
        FIELD.messageForConfessNewsClass_strConfessorNick = CLASS.messageForConfessNewsClass.getDeclaredField("strConfessorNick");
        FIELD.messageForConfessNewsClass_strConfessorNick.setAccessible(true);
        FIELD.messageForConfessNewsClass_strConfessorUin = CLASS.messageForConfessNewsClass.getDeclaredField("strConfessorUin");
        FIELD.messageForConfessNewsClass_strConfessorUin.setAccessible(true);

//        FIELD.chatAdapter1Class_field_a_of_type_ComTencentMobileqqActivityBaseChatPie = HookHelper.findFirstFieldByExactType(CLASS.chatAdapter1Class, CLASS.baseChatPieClass);
//        FIELD.chatAdapter1Class_field_a_of_type_JavaUtilList = HookHelper.findFirstFieldByExactType(CLASS.chatAdapter1Class, List.class);
//        FIELD.qqNumber = HookHelper.findBestMacthField(CLASS.sessionInfoClass, "a", String.class);

        METHOD.qqAppInterfaceClass_getManager = CLASS.qqAppInterfaceClass.getDeclaredMethod("getManager", int.class);
        METHOD.qqAppInterfaceClass_getManager.setAccessible(true);


    }
}
