package top.imlk.confesstalk.hooker;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
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

            final XC_MethodHook.Unhook[] unhook = new XC_MethodHook.Unhook[1];

            unhook[0] = XposedHelpers.findAndHookMethod("com.tencent.mobileqq.qfix.QFixApplication", loadPackageParam.classLoader, "onCreate", new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Context context = ((Application) param.thisObject).getBaseContext();

                    XposedBridge.log("confesstalk clasloader: " + context.getClassLoader().getClass().getName());


                    if (HookHelper.isMainProcess(context)) {

//                        Debug.waitForDebugger();

                        XposedBridge.log("confesstalk isMainProcess!!");

                        loadPackageParam.classLoader = context.getClassLoader();

                        unhook[0].unhook();

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


        XposedHelpers.findAndHookMethod(IMember.CLASS.messageForConfessNewsClass, "doParse", new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                IMember.FIELD.messageForConfessNewsClass_strConfessorNick.set(
                        param.thisObject
                        , "QQ号:" + IMember.FIELD.messageForConfessNewsClass_strConfessorUin.get(param.thisObject)
//                        , IMember.FIELD.messageForConfessNewsClass_strConfessorNick.get(param.thisObject)
//                                + "\nQQ:"
//                                + IMember.FIELD.messageForConfessNewsClass_strConfessorUin.get(param.thisObject)
                );

            }
        });


    }

}
