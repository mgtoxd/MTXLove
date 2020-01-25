package pers.mtx.mylove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class MainActivity extends Activity {

    WebView webView;
    double lat;
    double lng;
    LocationManager locationManager;
    LocationListener mLocationListener01;
    public String strlocation="";


    @Override
    protected void onStart() {
        //权限
        checkPermission();
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.wv_webview);
        loadweb();
        locationService=new LocationService(getApplicationContext());
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());







    }

    @SuppressLint("SetJavaScriptEnabled")
    public void loadweb() {
        final String url = "file:////android_asset/love.html";
//        String url = "http://49.235.196.164:8080/map?jd=118.93076119&wd=42.29711232";
        webView.setWebViewClient(new WebViewClient());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//
                webView.loadUrl(url);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "justTest");
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webView.getSettings().setLoadWithOverviewMode(true);
    }

    //重载onKeyDown的函数，使其在页面内回退,而不是直接退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //课表函数,返回1-15
    @JavascriptInterface
    public int getDate() throws ParseException {
//        Toast.makeText(this, "开始mdate", Toast.LENGTH_SHORT).show();
        Date date = new Date(System.currentTimeMillis());
//        String rq1 = "2020-03-03";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        date = formatter.parse(rq1);
        String beginTime = "2020-02-23";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date bt=sdf.parse(beginTime);
        if (date.before(bt)){
            return 0;
        }
        String newdays = datetool.newdays(date);
        Log.i(TAG, "getDate: "+newdays);
//        Toast.makeText(this, "newday"+newdays, Toast.LENGTH_SHORT).show();
        Map map =datetool.mtx2(newdays);
        int week =(int) map.get("week");
        int day = (int) map.get("day");
        int img = 0;
        if (week%2==0){
            img=7;
        }else {
            img=0;
        }
        img=img+day;
//        Toast.makeText(this, "day"+day+"week"+week, Toast.LENGTH_SHORT).show();
        return img;
    }


    /*
    打电话
     */
    @JavascriptInterface
    public void callPhone(String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this, "打电话权限未授予,请到设置中开启", Toast.LENGTH_SHORT).show();
        }

    }

    /*
    权限部分
     */
    public void  checkPermission(){
        int targetSdkVersion = 0;
        String[] PermissionString= new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            PermissionString = new String[]{Manifest.permission.CALL_PHONE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
        }
        try {
            final PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;//获取应用的Target版本
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
//            Log.e("err", "检查权限_err0");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Build.VERSION.SDK_INT是获取当前手机版本 Build.VERSION_CODES.M为6.0系统
            //如果系统>=6.0
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                //第 1 步: 检查是否有相应的权限
                boolean isAllGranted = checkPermissionAllGranted(PermissionString);
                if (isAllGranted) {
                    //Log.e("err","所有权限已经授权！");
                    return;
                }
                // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
                ActivityCompat.requestPermissions(this,
                        PermissionString, 1);
            }
        }
    }
    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                //Log.e("err","权限"+permission+"没有授权");
                return false;
            }
        }
        return true;
    }
    //申请权限结果返回处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                // 所有的权限都授予了
                Log.e("err", "权限都授权了");
            } else {
                Toast.makeText(this, "部分权限未授予", Toast.LENGTH_SHORT).show();
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                //容易判断错
                //MyDialog("提示", "某些权限未开启,请手动开启", 1) ;
            }
        }
    }

    /*
    位置函数,返回url
     */
    @JavascriptInterface
    public void getLocation() {
        if (!isopen()){
            Toast.makeText(MainActivity.this, "GPS没开", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(MainActivity.this, "开了", Toast.LENGTH_SHORT).show();
        }
        locationService.start();


    }
//
    /*




    位置

     */

    private LocationService locationService;
    String mLatitudeStr,mLongitudeStr,province;

    private static final String TAG = "MainActivity";
    private BDLocationListener mListener = new BDLocationListener() {
        int count = 0;
        @Override
        public void onReceiveLocation(BDLocation location) {

            mLatitudeStr = Double.toString(location.getLatitude());
            mLongitudeStr = Double.toString(location.getLongitude());
//            Toast.makeText(MainActivity.this, "onReceiveLocation: 纬度"+mLatitudeStr+"    经度 "+mLongitudeStr, Toast.LENGTH_SHORT).show();
            strlocation="wd="+mLatitudeStr+"&jd="+mLongitudeStr;
            Log.e(TAG, "onReceiveLocation: " + strlocation);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: "+strlocation);
                    webView.loadUrl("http://49.235.196.164:8080/map?wd=42.266149&jd=118.882664");
//                webView.loadUrl("file:////android_asset/index.html");
                }
            });
            count++;
            province = location.getProvince();
            if (!"".equals(province) && locationService != null) {
                locationService.stop();
            }
        }

    };

    private boolean isopen(){
        LocationManager locationManager = (LocationManager)getApplicationContext().
                getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }







}
