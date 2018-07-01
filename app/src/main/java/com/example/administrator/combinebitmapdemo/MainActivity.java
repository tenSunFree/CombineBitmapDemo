package com.example.administrator.combinebitmapdemo;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.othershe.combinebitmap.CombineBitmap;
import com.othershe.combinebitmap.layout.WechatLayoutManager;
import com.othershe.library.NiceImageView;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * EasyPermissions.PermissionCallbacks, 权限结果处理回调
 */
public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private String[] IMG_URL_ARR = {
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/31363.png?"
    };

    private String[] IMG_URL_ARR2 = {
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/1060.png?",
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/1058.png?",
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/1238.png?",
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/1217.png?"
    };

    private String[] IMG_URL_ARR3 = {
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/4387.png?",
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/4361.png?",
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/4386.png?",
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/4337.png?"
    };

    private String[] IMG_URL_ARR4 = {
            "https://drjaosdejw578.cloudfront.net/tw/static/1530241609129/assets/886/products/46662.png?"
    };

    NiceImageView imageView1;
    NiceImageView imageView2;
    NiceImageView imageView3;
    NiceImageView imageView4;

    /**
     * System.arraycopy为JVM内部固有方法, 它通过手工编写汇编或其他优化方法来进行 Java数组拷贝
     * 这种方式比起直接在Java上进行for循环或clone是更加高效的, 数组越大体现地越明显
     */
    private String[] getUrls(int number, int count) {
        String[] urls = new String[count];
        switch (number) {
            case 1:
                System.arraycopy(IMG_URL_ARR, 0, urls, 0, count);
                break;
            case 2:
                System.arraycopy(IMG_URL_ARR2, 0, urls, 0, count);
                break;
            case 3:
                System.arraycopy(IMG_URL_ARR3, 0, urls, 0, count);
                break;
            case 4:
                System.arraycopy(IMG_URL_ARR4, 0, urls, 0, count);
                break;
        }
        return urls;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        requestStoragePermission();
    }

    /**
     * @AfterPermissionGranted(), 当权限获取成功之后会回调到该注解方法中
     * 大致流程: EasyPermissions.requestPermissions() 先請求權限, 如果同意了權限的請求之後, 會調用@AfterPermissionGranted()
     * 但是調用@AfterPermissionGranted()的同時, 會檢查參數是不是一致, 這裡的參數是1000, 如果一致的話, 就會再執行requestStoragePermission()
     */
    @AfterPermissionGranted(1000)
    private void requestStoragePermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {                                     // 判斷是否已授權APP權限, 第二個參數可加入多個權限
            loadWechatBitmap(imageView1, 1, 1);
            loadWechatBitmap(imageView2, 2, 4);
            loadWechatBitmap(imageView3, 3, 4);
            loadWechatBitmap(imageView4, 4, 1);
        } else {
            EasyPermissions.requestPermissions(                                                         // 第一次要求授權遭拒, 第二次請求授權時會執行此段程式, 提醒使用者此項授權的必要性
                    this, "need storage permission", 1000, perms);
        }
    }

    /**
     * Android 6.0以後執行階段取得使用者授權, 或被使用者拒絕後, 會執行onRequestPermissionsResult(), 在此將授權回應交由EasyPermissions類別處理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 返回获取成功的权限
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    /**
     * 返回获取失败的权限
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    private void loadWechatBitmap(ImageView imageView, int number, int count) {
        CombineBitmap.init(MainActivity.this)
                .setLayoutManager(new WechatLayoutManager())
                .setSize(180)
                .setGap(3)
                .setGapColor(Color.parseColor("#8f4f06"))
                .setUrls(getUrls(number, count))
                .setImageView(imageView)
                .build();
    }
}
