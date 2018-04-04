package top.imlk.confesstalk.appUtil.mobileqq;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import top.imlk.confesstalk.hooker.HookHelper;

/**
 * Created by imlk on 2018/4/2.
 */

public class QQUtil {


    public static String getQQNum(Object baseChatPie) throws IllegalAccessException {

//        Object sessionInfo = FIELD.field_a_of_type_ComTencentMobileqqActivityAioSessionInfo.get(baseChatPie);
        Activity fragmentActivity = (Activity) IMember.FIELD.field_a_of_type_AndroidSupportV4AppFragmentActivity.get(baseChatPie);


        return fragmentActivity.getIntent().getStringExtra("uin");

    }


    public static String getRemarkName(Object baseChatPie, String qqNumber) {

        try {
            return (String) IMember.FIELD.friendsClass_remark.get(
                    IFriendsManager.getFriendsConcurrentHashMap(
                            IFriendsManager.getFriendsManager(IMember.FIELD.field_a_of_type_ComTencentMobileqqAppQQAppInterface.get(baseChatPie)
                            )
                    ).get(qqNumber));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return "unknown";
    }

}
