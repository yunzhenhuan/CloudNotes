package com.nucyzh.login_third;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nucyzh.R;
import com.nucyzh.flipperactivity.activity_test.Main;
import com.nucyzh.login_third.sinasdk.widget.LoginButton;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.BmobUser.BmobThirdUserAuth;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Author:XiYang on 2016/2/21.
 * Email:765849854@qq.com
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private static String TAG= "LoginActivity";
    EditText et_account,et_pwd;
    Button btn_qq,btn_weixin;

    Button btn_login,btn_register;
    LoginButton btn_weibo;

    private AuthListener mAuthListener = new AuthListener();
    private AuthInfo mAuthInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG,"true");
        //初始化BmobSDK
        Bmob.initialize(this, Constants.BMOB_APPID);
        initView();
        BmobUser user = BmobUser.getCurrentUser(this);
        if(user!=null){
            Intent intent = new Intent(LoginActivity.this, MainTestActivity.class);
            startActivity(intent);
        }
    }


    public void initView(){
        et_account = (EditText)findViewById(R.id.et_account);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        //设置微博按钮的样式
        btn_weibo = (LoginButton)findViewById(R.id.btn_weibo);
        // 创建微博授权认证信息
        mAuthInfo = new AuthInfo(this, Constants.WEIBO_APP_KEY, Constants.WEIBO_REDIRECT_URL, Constants.WEIBO_SCOPE);
        btn_weibo.setWeiboAuthInfo(mAuthInfo, mAuthListener);
        btn_weibo.setStyle(LoginButton.LOGIN_INCON_STYLE_2);

        btn_qq = (Button)findViewById(R.id.btn_qq);
        btn_weixin = (Button)findViewById(R.id.btn_weixin);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_qq.setOnClickListener(this);
        btn_weixin.setOnClickListener(this);
    }

    String account,pwd;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_login://登陆
                account = et_account.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                if(account.equals("")){
                    toast("Please fill in your username");
                    return;
                }
                if(pwd.equals("")){
                    toast("Please fill in your password");
                    return;
                }
                BmobUser user = new BmobUser();
                user.setUsername(account);
                user.setPassword(pwd);
                user.login(this, new SaveListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(LoginActivity.this, MainTestActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        toast("Longin failed："+arg1);
                    }
                });
                break;

            case R.id.btn_register://注册
                account= et_account.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                if(account.equals("")){
                    toast("Please fill in your username");
                    return;
                }
                if(pwd.equals("")){
                    toast("Please fill in your password");
                    return;
                }
                BmobUser u = new BmobUser();
                u.setUsername(account);
                u.setPassword(pwd);
                //u.setEmail("123456@qq.com");
                u.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(LoginActivity.this, MainTestActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        toast("注册失败："+arg1);
                    }
                });
                break;
            case R.id.btn_qq://QQ授权登录
                qqAuthorize();
                break;

            case R.id.btn_weixin:
                //发起微信登陆授权的请求
                SendAuth.Req req = new SendAuth.Req();
                //req.scope = "snsapi_userinfo";
                //req.state = "bmob_third_login_demo";//可随便写，微信会原样返回
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                boolean isOk = Applicationtest.api.sendReq(req);
                Log.i("smile", "是否发送成功："+isOk);
                break;
            default:
                break;
        }
    }


    /**
     * @method loginWithAuth
     * @param context
     * @param authInfo
     * @return void
     * @exception
     */
    public void loginWithAuth(final BmobThirdUserAuth authInfo){
        BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {
            @Override
            public void onSuccess(JSONObject userAuth) {
                // TODO Auto-generated method stub
                Log.i("smile",authInfo.getSnsType()+"登陆成功返回:"+userAuth);
                Intent intent = new Intent(LoginActivity.this, Main.class);
                intent.putExtra("json", userAuth.toString());
                intent.putExtra("from", authInfo.getSnsType());
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                toast("第三方登陆失败："+msg);
            }

        });
    }

    public static Tencent mTencent;

    private void qqAuthorize(){
        if(mTencent==null){
            mTencent = Tencent.createInstance(Constants.QQ_APP_ID, getApplicationContext());
        }
        mTencent.logout(this);
        mTencent.login(this, "all", new IUiListener() {

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if(arg0!=null){
                    JSONObject jsonObject = (JSONObject) arg0;
                    try {
                        String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                        String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                        String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                        BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ,token, expires,openId);
                        loginWithAuth(authInfo);
                    } catch (JSONException e) {
                    }
                }
            }

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                toast("QQ授权出错："+arg0.errorCode+"--"+arg0.errorDetail);
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                toast("取消qq授权");
            }
        });
    }

    /**
     * 登入按钮的监听器，接收授权结果。
     */
    private class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
            if (accessToken != null && accessToken.isSessionValid()) {
                //调用Bmob提供的授权登录方法进行微博登陆，登录成功后，你就可以在后台管理界面的User表中看到微博登陆后的用户啦
                String token = accessToken.getToken();
                String expires = String.valueOf(accessToken.getExpiresTime());
                String uid = accessToken.getUid();
                Log.i("smile", "微博授权成功后返回的信息:token = "+token+",expires ="+expires+",uid = "+uid);
                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO,token, expires,uid);
                loginWithAuth(authInfo);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            toast(e.getMessage());
        }

        @Override
        public void onCancel() {
            toast("取消weibo授权");
        }
    }

    /**
     * 当微博授权成功退出时，该函数被调用。
     * 微博授权时需要用到，非常重要：使用LoginButton控件的 Activity 必须重写该方法，且调用LoginButton的内部函数，否则会导致无法授权成功。
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //回调内部函数
        btn_weibo.onActivityResult(requestCode, resultCode, data);
    }

    private void toast(String msg){
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
