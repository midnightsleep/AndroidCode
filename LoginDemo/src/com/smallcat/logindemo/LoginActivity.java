package com.smallcat.logindemo;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/****
 * 
 * @author Administrator 注释：七岁小猫 时间：2015年5月12日 16:14:45 内容：登录中头像动画
 */
public class LoginActivity extends Activity {

	// Widgets
	private ImageView moImgPhoto;
	private ImageView moImgProgress;
	private LinearLayout moLayoutWelcome;
	private View moViewSlideLine;
	private EditText moEditUsername;
	private EditText moEditPassword;
	private ImageView moImgSlider;
	private Button moBtnClearUsername;
	private Button moBtnClearPassword;
	private Button moBtnRegister;
	private Button moBtnTraveller;

	// Members
	private Handler moHandler;
	private boolean mbIsSlidingBack;
	private int miSliderMinX, miSliderMaxX, miLastX;
	private String msRedirectPage;

	// Constant
	public static final int PASSWORD_MIN_LENGTH = 6;
	public static final int LOGIN_SUCCESS = 0; // 登录成功
	public static final int LOGIN_FAILED = 1; // 登录失败
	public static final int LOGIN_SLIDER_TIP = 2; // 登录页面滑块向左自动滑动
	public static final int LOGIN_PHOTO_ROTATE_TIP = 3; // 登录页面加载图片转动

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setHandler();
		initMembers();
		setEventListeners();

	}

	// 触摸登录界面收回键盘
	public boolean onTouchEvent(android.view.MotionEvent poEvent) {
		try {
			InputMethodManager loInputMgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			return loInputMgr.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), 0);
		} catch (Exception e) {
			return false;
		}
	}

	private void setHandler() {
		moHandler = new Handler() {
			@Override
			public void handleMessage(Message poMsg) {
				switch (poMsg.what) {
				case LOGIN_SUCCESS:
					// 登录成功
					Map<String, String> lmExtra = null;
					if (!Utils.isStrEmpty(msRedirectPage)) {
						lmExtra = new HashMap<String, String>();
						lmExtra.put("redirect", msRedirectPage);
					}
					// Utils.gotoActivity(LoginActivity.this,
					// LoginSuccessActivity.class, true, lmExtra);
					break;
				case LOGIN_FAILED:
					// 登录失败
					stopLogin();
					Toast.makeText(LoginActivity.this, (String) poMsg.obj,
							Toast.LENGTH_LONG).show();
					break;
				case LOGIN_SLIDER_TIP:
					moImgSlider.layout(miLastX, moImgSlider.getTop(), miLastX
							+ moImgSlider.getWidth(), moImgSlider.getTop()
							+ moImgSlider.getHeight());
					break;
				case LOGIN_PHOTO_ROTATE_TIP:
					moImgPhoto.setImageBitmap((Bitmap) poMsg.obj);
					break;
				}
			}
		};
	}

	// 实例化控件
	private void initMembers() {
		moImgPhoto = (ImageView) findViewById(R.id.login_img_photo);
		moImgProgress = (ImageView) findViewById(R.id.login_img_progress);
		moLayoutWelcome = (LinearLayout) findViewById(R.id.login_layout_welcome);
		moViewSlideLine = findViewById(R.id.login_view_line);
		moEditUsername = (EditText) findViewById(R.id.login_edit_username);
		moEditPassword = (EditText) findViewById(R.id.login_edit_password);
		moImgSlider = (ImageView) findViewById(R.id.login_img_slide);
		moBtnClearUsername = (Button) findViewById(R.id.login_btn_clear_username);
		moBtnClearPassword = (Button) findViewById(R.id.login_btn_clear_password);
		moBtnRegister = (Button) findViewById(R.id.login_btn_register);
		moBtnTraveller = (Button) findViewById(R.id.login_btn_traveller);
		mbIsSlidingBack = false;
		miLastX = 0;
		miSliderMinX = 0;
		miSliderMaxX = 0;
	}

	// 设置监听事件
	private void setEventListeners() {
		moEditUsername.addTextChangedListener(new OnEditUsername());
		moEditPassword.addTextChangedListener(new OnEditPassword());
		moBtnClearUsername.setOnClickListener(new OnClearEditText());
		moBtnClearPassword.setOnClickListener(new OnClearEditText());
		moImgSlider.setOnClickListener(new OnSliderClicked());
		moImgSlider.setOnTouchListener(new OnSliderDragged());
		moBtnRegister.setOnClickListener(new OnRegister());
		moBtnTraveller.setOnClickListener(new OnTravell());
	}

	/************** 事件处理类 *******************************/
	// 处理用户名编辑事件
	private class OnEditUsername implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// 1. 处理右侧清除按钮隐藏/显示
			if (s.length() >= 1)
				moBtnClearUsername.setVisibility(View.VISIBLE);
			else
				moBtnClearUsername.setVisibility(View.GONE);

			// 2. 处理滑块是否可滑动
			initWidgetForCanLogin();
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	}

	// 处理密码编辑事件
	private class OnEditPassword implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// 1. 处理右侧清空按钮显示/隐藏
			if (s.length() >= 1)
				moBtnClearPassword.setVisibility(View.VISIBLE);
			else if (s.length() == 0
					&& moBtnClearPassword.getVisibility() == View.VISIBLE)
				moBtnClearPassword.setVisibility(View.GONE);

			// 2. 处理滑块是否可滑动
			initWidgetForCanLogin();
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

	}

	// 清除输入控件中的文字的事件处理
	private class OnClearEditText implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_btn_clear_username:
				// 如果清除帐号则密码一并清除
				moEditUsername.setText("");
				moEditPassword.setText("");
				break;
			case R.id.login_btn_clear_password:
				// 清除已输密码
				moEditPassword.setText("");
				break;
			default:
				break;
			}
		}
	}

	// 滑动图标点击事件
	private class OnSliderClicked implements OnClickListener {
		@Override
		public void onClick(View v) {
			// 如果不符合登录条件 则跳转到忘记密码界面
			if (!canLogin())
				Utils.gotoActivity(LoginActivity.this, ForgetPwdActivity.class,
						false, null);
		}
	}

	// 滑动图标滑动事件
	private class OnSliderDragged implements OnTouchListener {
		@SuppressWarnings("unused")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Utils.closeKeybord(moEditPassword, LoginActivity.this);
			Utils.closeKeybord(moEditUsername, LoginActivity.this);
			if (canLogin() && !mbIsSlidingBack) {
				if (miSliderMaxX == 0) {
					miSliderMinX = moViewSlideLine.getLeft()
							- moImgSlider.getWidth() / 2;
					miSliderMaxX = moViewSlideLine.getRight()
							- moImgSlider.getWidth() / 2;
				}
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					miLastX = (int) event.getRawX();
				case MotionEvent.ACTION_MOVE:
					int liX = (int) event.getRawX();
					if (liX > miSliderMaxX)
						liX = miSliderMaxX;
					else if (liX < miSliderMinX)
						liX = miSliderMinX;
					if (liX != miLastX) {
						moImgSlider.layout(liX, moImgSlider.getTop(), liX
								+ moImgSlider.getWidth(), moImgSlider.getTop()
								+ moImgSlider.getHeight());
						miLastX = liX;
						if (miLastX == miSliderMaxX) {
							// startRotateImg();
							String lsUsername = moEditUsername.getText()
									.toString();
							String lsPassword = moEditPassword.getText()
									.toString();
							startLogin();
							// TODO 调用借口
						}
					}
					break;
				case MotionEvent.ACTION_UP:
					if ((int) event.getRawX() < miSliderMaxX)
						slideBack();
					break;
				}

			}
			return false;
		}
	}

	// 注册事件
	private class OnRegister implements OnClickListener {
		@Override
		public void onClick(View v) {
			showToast("注册");
			// Utils.gotoActivity(LoginActivity.this,
			// RegisterTypeActivity.class,
			// false, null);
		}
	}

	// 游客事件
	private class OnTravell implements OnClickListener {
		@Override
		public void onClick(View v) {
			showToast("游客");
		}
	}

	// 根据是否可以登录，初始化相关控件
	private void initWidgetForCanLogin() {
		if (canLogin())
			moImgSlider.setImageResource(R.drawable.ic_arrow_circle_right);
		else
			moImgSlider.setImageResource(R.drawable.ic_ask_circle);
	}

	// 判断当前用户输入是否合法，是否可以登录
	private boolean canLogin() {
		Editable loUsername = moEditUsername.getText();
		Editable loPassword = moEditPassword.getText();
		return !Utils.isStrEmpty(loUsername)
				&& loPassword.length() >= PASSWORD_MIN_LENGTH;
	}

	// 滑块向会自动滑动
	private void slideBack() {
		new Thread() {
			@Override
			public void run() {
				mbIsSlidingBack = true;
				while (miLastX > miSliderMinX) {
					miLastX -= 5;
					if (miLastX < miSliderMinX)
						miLastX = miSliderMinX;
					Message loMsg = new Message();
					loMsg.what = LOGIN_SLIDER_TIP;
					moHandler.sendMessage(loMsg);
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
					}
				}
				mbIsSlidingBack = false;
			}
		}.start();
	}

	// 动画开启
	private void startLogin() {
		Animation loAnimRotate = AnimationUtils.loadAnimation(this,
				R.anim.rotate);
		Animation loAnimScale = AnimationUtils.loadAnimation(this,
				R.anim.login_photo_scale_small);
		// 匀速动画
		LinearInterpolator linearInterpolator = new LinearInterpolator();
		// 加速动画
		// AccelerateInterpolator accelerateInterpolator = new
		// AccelerateInterpolator();
		// 弹跳动画
		// BounceInterpolator bounceInterpolator = new BounceInterpolator();

		loAnimRotate.setInterpolator(linearInterpolator);
		loAnimScale.setInterpolator(linearInterpolator);
		moImgProgress.setVisibility(View.VISIBLE);
		moImgProgress.startAnimation(loAnimRotate);
		moImgPhoto.startAnimation(loAnimScale);

		moImgSlider.setVisibility(View.GONE);
		moViewSlideLine.setVisibility(View.GONE);
		moEditUsername.setVisibility(View.GONE);
		moEditPassword.setVisibility(View.GONE);
		moBtnClearUsername.setVisibility(View.GONE);
		moBtnClearPassword.setVisibility(View.GONE);
		moBtnRegister.setVisibility(View.GONE);
		moBtnTraveller.setVisibility(View.GONE);

		moLayoutWelcome.setVisibility(View.VISIBLE);
	}

	// 动画结束
	private void stopLogin() {
		Animation loAnimScale = AnimationUtils.loadAnimation(this,
				R.anim.login_photo_scale_big);
		LinearInterpolator loLin = new LinearInterpolator();
		loAnimScale.setInterpolator(loLin);
		moImgProgress.clearAnimation();
		moImgProgress.setVisibility(View.GONE);
		moImgPhoto.clearAnimation();
		moImgPhoto.startAnimation(loAnimScale);

		moImgSlider.setVisibility(View.VISIBLE);
		moViewSlideLine.setVisibility(View.VISIBLE);
		moEditUsername.setVisibility(View.VISIBLE);
		moEditPassword.setVisibility(View.VISIBLE);
		moBtnClearUsername.setVisibility(View.VISIBLE);
		moBtnClearPassword.setVisibility(View.VISIBLE);
		moBtnRegister.setVisibility(View.VISIBLE);
		moBtnTraveller.setVisibility(View.VISIBLE);
		moLayoutWelcome.setVisibility(View.GONE);
	}

	private void showToast(String strToast) {
		Toast.makeText(this, strToast, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
