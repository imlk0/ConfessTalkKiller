package top.imlk.confesstalk.hooker;

import android.content.Context;
import android.text.TextWatcher;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import top.imlk.confesstalk.appUtil.mobileqq.IFriendsManager;
import top.imlk.confesstalk.appUtil.mobileqq.IMember;
import top.imlk.confesstalk.appUtil.mobileqq.QQUtil;
import top.imlk.confesstalk.holder.ITag;

/**
 * Created by imlk on 2018/4/2.
 */

public class Injecter implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("com.tencent.mobileqq")) {

            XposedBridge.log("confesstalk Found QQ!!!!!!!!!!!!!!!!!!!!!!");


            XposedHelpers.findAndHookMethod("com.tencent.mobileqq.qfix.QFixApplication", loadPackageParam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {


                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {


                    XposedBridge.log("confesstalk QQChatPic clasloader: " + ((Context) param.args[0]).getClassLoader().getClass().getName());


                    if (HookHelper.isMainProcess(((Context) param.args[0]))) {

                        XposedBridge.log("confesstalk isMainProcess!!");

                        loadPackageParam.classLoader = ((Context) param.args[0]).getClassLoader();

                        performHook_QQ(loadPackageParam);

                    }
                }
            });


        }
    }

    public static void performHook_QQ(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws NoSuchMethodException, NoSuchFieldException {

        IMember.initUtil(loadPackageParam.classLoader);

        XposedHelpers.findAndHookMethod(TextView.class, "addTextChangedListener", TextWatcher.class, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                if (param.args[0].getClass().getName().equals("com.tencent.mobileqq.activity.aio.rebuild.ConfessChatPie") && IMember.CLASS.xEditTextExClass.isInstance(param.thisObject)) {

                    if (((TextView) param.thisObject).getTag(ITag.TOP_IMLK_CONFESSTALK_INJECTED_TAG) == null) {
                        ((TextView) param.thisObject).setTag(ITag.TOP_IMLK_CONFESSTALK_INJECTED_TAG, true);

                        XposedBridge.log("confesstalk Found suit View");

                        String qQNumber = QQUtil.getQQNum(param.args[0]);

                        ((TextView) param.thisObject).setHint("QQ:" + qQNumber + "  昵称:" + QQUtil.getRemarkName(param.args[0], qQNumber));

                        XposedBridge.log("confesstalk finish set");

                    }

                }

            }
        });


    }

}
