package com.example.webtest;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.webtest.base.BidName;
import com.example.webtest.base.Constant;
import com.example.webtest.base.MyWebView;
import com.example.webtest.base.Shops;
import com.example.webtest.base.WA_YundaFragment;
import com.example.webtest.io.LogUtil;
import com.example.webtest.io.SharedPreferencesUtils;
import com.example.webtest.io.WA_Parameters;

import java.util.ArrayList;

/**
 * @desc 自动化Fragment主调页面
 * @author z.h
 */
public class WA_MainFragment extends WA_YundaFragment implements View.OnClickListener {
	private static final String ARG_CODE = "WebAutoFragment";
	private static final String BASIC_JS_PATH = "basic_inject.js";
	private static final String LOGIC_JS_PATH = "logic_inject.js";

	private LocalMethod mLocalMethod;
	private WA_Parameters parameter;
	private String injectJS;
	private String before10secUrl;
	private int oldindex;
	private boolean IS_INIT_LOAD = true;
	private boolean IS_3WAT = false;
	private Button btn_way3Result;



	/**  通过静态方法实例化自动化Fragment*/
	public static void start(Activity mContext, int containerRsID, WA_Parameters parameter)
	{
		WA_MainFragment mCurrentFragment = WA_MainFragment.getInstence(parameter);
		mContext.getFragmentManager().beginTransaction().replace(containerRsID, mCurrentFragment).commit();
	}

	private static WA_MainFragment getInstence(WA_Parameters parameter)
	{
		WA_MainFragment webAutoFragment = new WA_MainFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(ARG_CODE, parameter);
		webAutoFragment.setArguments(bundle);
		return webAutoFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		instrumentation = new Instrumentation();
		if (null != bundle)
		{
			parameter = (WA_Parameters) bundle.getSerializable(ARG_CODE);
		}
		Resources res = getResources();
//		shops = res.getStringArray(R.array.classify);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.wa_fragment_main, container, false);
		findViews(view);
		initData();
		setListener(view);
		return view;
	}

	private void findViews(View view)
	{
		listWeb = (MyWebView) view.findViewById(R.id.wa_webview_list);
		btnRefresh = (Button) view.findViewById(R.id.btn_refresh);
		btnBack = (Button) view.findViewById(R.id.btn_back);
		btn_way3Result = (Button) view.findViewById(R.id.btn_way3Result);
		btnSearch = (Button) view.findViewById(R.id.btn_search);
		btnGosearch = (Button) view.findViewById(R.id.btn_gosearch);
		btnGosearchworld = (Button) view.findViewById(R.id.btn_gosearchworld);
		btnGetchecked = (Button) view.findViewById(R.id.btn_getchecked);
		btn_check = (Button) view.findViewById(R.id.btn_check);
		btn_biao1 = (Button) view.findViewById(R.id.btn_biao1);
		btn_str_result = (Button) view.findViewById(R.id.btn_str_result);
	}

	/** 初始化两个不同功用的WebView */
	private void initData()
	{
		initListWeb();
		//deleteLog();
		injectJS = getJsFromFile(getActivity(), BASIC_JS_PATH) + getJsFromFile(getActivity(), LOGIC_JS_PATH);
	}

	private void initListWeb()
	{
		WebSettings webSetting = listWeb.getSettings();


		// 支持获取手势焦点
		listWeb.requestFocusFromTouch();
		listWeb.setHorizontalFadingEdgeEnabled(true);
		listWeb.setVerticalFadingEdgeEnabled(false);
		listWeb.setVerticalScrollBarEnabled(false);
		// 支持JS
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setDisplayZoomControls(true);
		webSetting.setLoadWithOverviewMode(true);
		// 支持插件
		webSetting.setPluginState(WebSettings.PluginState.ON);
		webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		// 自适应屏幕
		webSetting.setUseWideViewPort(true);
		// 支持缩放
		webSetting.setSupportZoom(true
		);//就是这个属性把我搞惨了，
		// 隐藏原声缩放控件
		// 支持内容重新布局
//		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.supportMultipleWindows();
		webSetting.setSupportMultipleWindows(true);
		// 设置缓存模式
		webSetting.setDomStorageEnabled(true);
		webSetting.setDatabaseEnabled(true);
		webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSetting.setAppCacheEnabled(true);
		webSetting.setAppCachePath(listWeb.getContext().getCacheDir().getAbsolutePath());
		// 设置可访问文件
		webSetting.setAllowFileAccess(true);
		webSetting.setNeedInitialFocus(true);
		// 支持自定加载图片
		if (Build.VERSION.SDK_INT >= 19) {
			webSetting.setLoadsImagesAutomatically(true);
		} else {
			webSetting.setLoadsImagesAutomatically(false);
		}
		webSetting.setNeedInitialFocus(true);
		// 设定编码格式
		webSetting.setDefaultTextEncodingName("UTF-8");



		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//支持js


		webSetting.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
//自适应屏幕
//自动缩放
		webSetting.setBuiltInZoomControls(true);
		webSetting.setSupportZoom(true);

//支持获取手势焦点


//
//		webSetting.setJavaScriptEnabled(true);
//		webSetting.setDefaultTextEncodingName("utf-8");
//		webSetting.setAllowFileAccess(true);
//		webSetting.setUseWideViewPort(true);
//		webSetting.setLoadWithOverviewMode(true);
		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


		listWeb.loadUrl(Constant.URL);
		listWeb.setWebViewClient(new MyListWebViewClient());
		mLocalMethod = new WA_YundaFragment.LocalMethod(getActivity(), parameter);
		listWeb.addJavascriptInterface(mLocalMethod, "localMethod");
//		webSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
	}


	private void setListener(View view)
	{

		bidNameMd5 = md5Password(BidName.BrandName);


		btnRefresh.setOnClickListener(this);


		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnGosearch.setOnClickListener(this);
		btnGosearchworld.setOnClickListener(this);
		btnGetchecked.setOnClickListener(this);
		btn_check.setOnClickListener(this);
		btn_biao1.setOnClickListener(this);
		btn_str_result.setOnClickListener(this);
		btn_way3Result.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
            case R.id.btn_back://3way
				IS_3WAT = true;
				shopIndex = 0;
				allSameList = new ArrayList<String>();
				shops = Shops.shops.split("\n");
				SwitchMethod = Constant.DEFAULT_WAY;
				loadUrl(Constant.default_url.replace(Constant.SEIZE_STR, shops[shopIndex]));
                break;
            case R.id.btn_way3Result:
				String result = SharedPreferencesUtils.getValue(getActivity(), Constant.default_url.replace(Constant.SEIZE_STR, md5Password(Shops.shops)));
				String[] min3wayUrls = result.split("###");
				foreachMinurlResult(min3wayUrls);
				break;
            case R.id.btn_getchecked:
				SwitchMethod = Constant.CANGKU_NEXT_PAGE_LOAD;
            	handlerJs("jsCangkuGoNextPage();");
                break;
            case R.id.btn_gosearchworld:
				IS_CANGKU = true;
				listWeb.loadUrl(Constant.CANGKU_URL);
                break;
			case R.id.btn_refresh:
				IS_3WAT = false;
				FOREACH_MODE = false;
				String url = initBeginUrl();
                spShopRecordKey = url + Constant.SHOP_LIST;
                if (btn_biao1.getText().toString().equals("缓存")) {
                    minUrlRecord = SharedPreferencesUtils.getValue(getActivity(), spRecordMinUrlKey);
                    String shops = SharedPreferencesUtils.getValue(getActivity(), spShopRecordKey);
                    String[] split = shops.split("###");
                    taoSearchList = new ArrayList<String>();
                    for (int i = 0; i < split.length; i++) {
						taoSearchList.add(split[i]);
                    }
                    foreachSearchTBLM();

                } else {
                    if (FIRST_TIME) {
                        FIRST_TIME = false;
                        spRecordMinUrlKey = url+Constant.MIN_URL_RECORD;
						minUrlShopNameRecordKey = url+Constant.MIN_NAME_RECORD;
                    }
                    loadUrl(url);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handlerJs("tblmShopList();");
                        }
                    }, Constant.TBLM_WAIT_TIME);

                }
				break;
			case R.id.btn_gosearch:
				if (IS_3WAT) {
					nextShop3Way();
				} else {
					listWeb.reload();
				}

				break;
			case R.id.btn_check://遍历模式
				IS_3WAT = false;
				btn_check.setBackgroundResource(android.R.color.holo_orange_light);
				foreachShop();
				break;
			case R.id.btn_biao1:
                if (btn_biao1.getText().toString().equals("缓存")) {
                    btn_biao1.setText("重新");
                } else {
                    btn_biao1.setText("缓存");
                }
				break;
			case R.id.btn_str_result:
				foreachSearchTBLM();
//				String[] split = BidName.BrandName.split("\n");
//				String result123 = "";
//				for (int i = 0; i < split.length; i++) {
//					if (strLength(split[i]) < 11) {
//						result123 = result123 + "\n" + split[i];
//					}
//				}
//				LogUtil.e(result123);
				break;
			case R.id.btn_search:
				if (FOREACH_MODE) {
					spRecordMinUrlKey = md5Password(Shops.shops) + Constant.MIN_URL_RECORD;
					minUrlShopNameRecordKey = md5Password(Shops.shops) + Constant.MIN_NAME_RECORD;
				} else {
					String mUrl = initBeginUrl();
					if (TextUtils.isEmpty(spRecordMinUrlKey)) {
						spRecordMinUrlKey = mUrl+Constant.MIN_URL_RECORD;
					}
					if (TextUtils.isEmpty(minUrlShopNameRecordKey)) {
						minUrlShopNameRecordKey = mUrl+Constant.MIN_NAME_RECORD;
					}
				}
				String[] minUrls = SharedPreferencesUtils.getValue(getActivity(), spRecordMinUrlKey).split("###");
				foreachMinurlResult(minUrls);


				break;

		}


	}

	private void foreachMinurlResult(String[] minUrls) {
		ArrayList<String> minUrlsResultList = new ArrayList<String>();
		ArrayList<Integer> numsList = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			if (i == 0) {
				numsList.add(50);
			} else {
				numsList.add(numsList.get(i - 1) + 50);
			}
		}

		String temple = "================================================" + "\n";
		for (int i = 0; i < minUrls.length; i++) {
			int one = i / 50;

			if (one > 0) {
				int two = one + 1;
				if (i == one * 50) {
					temple = "================================================" + "\n";
				}
				if (i >= one * 50 && i < two * 50) {
					temple = temple + minUrls[i] + "\n";

					if (minUrls.length < two * 50 - 1) {
						if (i == minUrls.length - 1) {
							minUrlsResultList.add(temple);
						}
					} else if (i == two * 50 - 1) {
						minUrlsResultList.add(temple);
					}
				}
			} else if (one==0) {
				temple = temple + minUrls[i] + "\n";
				if (minUrls.length < numsList.get(one) - 1) {
					if (i == minUrls.length - 1) {
						minUrlsResultList.add(temple);
					}

				} else if (i == numsList.get(one) - 1) {
					minUrlsResultList.add(temple);
				}
			}
        }

		for (int i = 0; i < minUrlsResultList.size(); i++) {
			LogUtil.e(minUrlsResultList.get(i));
		}

	}


	private String initBeginUrl() {
		if (TextUtils.isEmpty(beginUrl)) {
			beginUrl = listWeb.getUrl().replace("&toPage=1", "");
		}
		return beginUrl + "&userType=0&jpmj=1&" + Constant.FILTER + "&level=1" + "&toPage=" + toPage + "&perPageSize=100";
	}


	/** ListWebView加载完注入基本JS函数 */
	private class MyListWebViewClient extends WebViewClient
	{
		@Override
		public void onPageFinished(WebView view, final String url)
		{
			view.loadUrl("javascript:" + injectJS);
			LogUtil.e("SwitchMethod:" + SwitchMethod);
			CHECK_UPLOAD_SUCCESS = false;
			try {
				LogUtil.e("shopName:" + shops[shopIndex]);
			} catch (Exception e) {
				LogUtil.e(e.toString());
			}

			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					switchMethod(url);
				}
			});

//			try {
//				switchMethod(url);
//			} catch (Exception e) {
//				LogUtil.e(e.toString());
//			}

			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{
			super.onPageStarted(view, url, favicon);
		}
	}

	private void switchMethod(final String url) {
		before10secUrl = url;
		oldindex = searIndex;
		if (!IS_CANGKU&&!IS_INIT_LOAD&&!IS_3WAT) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (oldindex == searIndex && before10secUrl.equals(url)) {
                        foreachSearchTBLM();
                    }
                }
            }, 20000);
		} else if (IS_3WAT) {
			oldindex = link3WayIndex;
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (oldindex == link3WayIndex && before10secUrl.equals(url)) {
						nextShop3Way();
					}
				}
			}, 50000);
		}
		if (IS_INIT_LOAD) {
            IS_INIT_LOAD = false;
        }


		switch (SwitchMethod) {
            case Constant.WAY3_SAMESTYTLE:
				SameLoadFinish = true;
				LogUtil.e("way3SameUrl:main" + 413);
				way3SameUrl();
                break;
            case Constant.DEFAULT_WAY:
            case Constant.SALES_DESC:
            case Constant.RENQI_WAY:
                if (SwitchMethod == Constant.DEFAULT_WAY) {
                    handlerJs("showKeyboard3Way();", 1000);
                } else {

                    handlerJs("find3WaySameStyle();", 1000);
                }
                break;
            case Constant.CANGKU_NEXT_PAGE_LOAD:
				if (NEXT_PAGE_END) {
					SwitchMethod = -1;
				}
                handlerJs("jsCangkuGoNextPage();", 2000);
                break;
            case Constant.EDIT_DETAIL_COMPLETE:
                if (shangjiaIndex < xiajiaRecordList.size()) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goEditDetailsUrl();
                        }
                    }, 1000);
                } else {
                    xiajiaRecordList.clear();
                    shangjiaIndex = 0;
                }
                break;
            case Constant.EDIT_DETAIL:
                shangjiaIndex++;
                SwitchMethod = Constant.EDIT_DETAIL_COMPLETE;
                handlerJs("editTitleAndShangjiaNow();", 2000);
                break;
            case Constant.NEXT_PAGE_LOAD:
				SwitchMethod = -1;
//                    String name = taoNameList.get(searIndex);
                handlerJs("tblmShopList();", 8000);
                break;
            case Constant.FIND_SAMESTYLE_FROM_TBLM:
				SwitchMethod = -1;
//                    String name = taoNameList.get(searIndex);
                findSameStyle();
                break;
            case Constant.GO_SAMESTYLE_URL:
                SwitchMethod = -1;

                jsGoSameStyle();


                break;

        }
	}



	private void findSameStyle() {
//		String name  = taoNameList.get(searIndex);
		LogUtil.e("!!!!!!"  + currentPShopSize + "-" + searIndex);
		if (searIndex < currentPShopSize) {
//            LogUtil.e("!!!!!!" + name);
            handlerJs("findSameStyle();",Constant.WebListenerTime);
            searIndex++;
        }
	}

	private void jsGoSameStyle() {
		if (searIndex < currentPShopSize){
			handlerJs("jsGoSameUrl(\"" + titlesArray + "\");", Constant.WebListenerTime);

        }
	}

}
