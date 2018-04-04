package top.imlk.confesstalk.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.didikee.donate.AlipayDonate;
import android.didikee.donate.WeiXinDonate;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import top.imlk.confesstalk.R;


public class SettingActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        checkPermissions();

        try {
            ((TextView) findViewById(R.id.tv_version)).setText(getApplication().getPackageManager().getPackageInfo(getPackageName(), 0).versionName + "(" + getApplication().getPackageManager().getPackageInfo(getPackageName(), 0).versionCode + ")");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.tv_title_donate)).setText(randomString());
        ((TextView) findViewById(R.id.tv_setting_show_ico)).setText(icoStatus());

        findViewById(R.id.tv_version).setOnClickListener(this);
        findViewById(R.id.tv_author_imlk).setOnClickListener(this);
        findViewById(R.id.tv_donate_wechat).setOnClickListener(this);
        findViewById(R.id.tv_donate_alipay).setOnClickListener(this);
        findViewById(R.id.tv_setting_show_ico).setOnClickListener(this);
        findViewById(R.id.tv_link_github).setOnClickListener(this);
        findViewById(R.id.tv_link_qqGroup).setOnClickListener(this);
        findViewById(R.id.tv_link_github_license).setOnClickListener(this);


    }

    public String randomString() {
        String[] strings = {"我女票很漂亮，能捐赠我吗(*/ω＼*)",
                "我手机很旧了，能支持我换新吗( •̀ ω •́ )✧",
                "施主，能给点钱吗(。_。)",
                "苦逼开发者求喂养(ง •_•)ง",
                "我没有钱钱( $ _ $ )",
                "我想吃零食(。・∀・)ノ",
                "给咸鱼一点动力吧( •̀ ω •́ )y",
                "拯救秃头程序员〒▽〒"};

        return strings[((int) (strings.length * Math.random())) % strings.length];
    }

    public String icoStatus() {
        if (getPackageManager().getComponentEnabledSetting(getAliseComponentName()) == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
            return "点击隐藏应用图标";
        } else {
            return "点击显示应用图标";
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_version:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coolapk.com/apk/top.imlk.confesstalk")));
                break;
            case R.id.tv_author_imlk:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.coolapk.com/u/477017")));
                break;
            case R.id.tv_donate_wechat:

                InputStream weixinQrIs = getResources().openRawResource(R.raw.donate_wechat);
                String qrPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "ConfessTalk" + File.separator +
                        "imlk_weixin.png";

                File qrFile = new File(qrPath);
                File parentFile = qrFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                try {
                    if ((!qrFile.createNewFile()) && (!qrFile.exists())) {
                        throw new IOException("unable to createThis File : " + qrPath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);

                    builder.setMessage("很高兴能收到您的捐赠，为了完成这一过程,我们需要在您的储存中存放一张我的收款码图片（捐赠完我们会把它删掉的，请放心）\n但由于Android系统的bug\nε(┬┬﹏┬┬)3\n新申请的权限必须要在 应用完全重启后 才被允许使用\n\n\n是否完全关闭此应用，然后再进入？\n\n关闭后你需要重新打开此应用继续捐赠");
                    builder.setNegativeButton("残忍拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("点我完全关闭此应用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(SettingActivity.this, "请尝试重新打开应用继续您的捐赠，谢谢", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                    builder.show();
                    break;
                }

                WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
                WeiXinDonate.donateViaWeiXin(this, qrPath);

                Toast.makeText(SettingActivity.this, "请从相册选择收款码，蟹蟹，你的鼓励是我的最大动力", Toast.LENGTH_SHORT).show();

                break;
            case R.id.tv_donate_alipay:
                boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(this);
                if (hasInstalledAlipayClient) {
                    AlipayDonate.startAlipayClient(this, "FKX093049UCVM4EEN8WV84");
                }
                Toast.makeText(SettingActivity.this, "蟹蟹，你的鼓励是我的最大动力", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_setting_show_ico:
                if (getPackageManager().getComponentEnabledSetting(getAliseComponentName()) == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
                    getPackageManager().setComponentEnabledSetting(getAliseComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                } else {
                    getPackageManager().setComponentEnabledSetting(getAliseComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
                }
                ((TextView) view).setText(icoStatus());
                break;
            case R.id.tv_link_qqGroup:
                Intent intent = new Intent();
                intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3Dt3KJwqRiDDvuu8hCIY7Ku0cEZIXkRCKE"));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "拉起QQ失败emmmm", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_link_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/KB5201314/ConfessTalkKiller")));
                break;
            case R.id.tv_link_github_license:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/KB5201314/ConfessTalkKiller/blob/master/LICENSE")));
                break;
        }
    }


    private ComponentName getAliseComponentName() {
        return new ComponentName(SettingActivity.this, "top.imlk.confesstalk.hooker.Injecter");
    }

    private void checkPermissions() {


        int permission_0 = ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission_1 = ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_2 = ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);

        if (permission_0 != PackageManager.PERMISSION_GRANTED || permission_1 != PackageManager.PERMISSION_GRANTED || permission_2 != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(SettingActivity.this, "需要读写文件权限", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);
            }
//                getPermissionByUser();
        }
    }
//
//    private void getPermissionByUser() {
//
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivity(intent);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }


    @Override
    protected void onDestroy() {

        //删除付款码

        String qrPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "ConfessTalk" + File.separator +
                "imlk_weixin.png";

        File qrFile = new File(qrPath);
        File parentFile = qrFile.getParentFile();

        if (qrFile.exists()) {
            qrFile.delete();
            parentFile.delete();
        }

        System.exit(0);
        super.onDestroy();
    }

}
