package com.example.webtest;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.example.webtest.base.Arith;
import com.example.webtest.base.BidName;
import com.example.webtest.base.Constant;
import com.example.webtest.base.MyWebView;
import com.example.webtest.base.Shops;
import com.example.webtest.base.SplitStr;
import com.example.webtest.base.WA_YundaFragment;
import com.example.webtest.io.LogUtil;
import com.example.webtest.io.SharedPreferencesUtils;
import com.example.webtest.io.WA_Parameters;

import java.util.ArrayList;
import java.util.Random;

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
	private ArrayList<String> pingDuoDuoSearchList;


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
		btn_split = (Button) view.findViewById(R.id.btn_split);
		btn_et_reset = (Button) view.findViewById(R.id.btn_et_reset);
		et_shop = (EditText) view.findViewById(R.id.et_shop);
		et_split = (EditText) view.findViewById(R.id.et_split);
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
		btn_split.setOnClickListener(this);
		btn_et_reset.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
            case R.id.btn_back://3way
				IS_3WAT = true;
				shopIndex = 0;
				allSameList = new ArrayList<String>();
				getShopsStr();
				shops = shopsStr.split("\n");

				SwitchMethod = Constant.DEFAULT_WAY;
				loadUrl(Constant.default_url.replace(Constant.SEIZE_STR, shops[shopIndex]));
                break;
            case R.id.btn_way3Result:
				getShopsStr();
				String result = SharedPreferencesUtils.getValue(getActivity(), Constant.default_url.replace(Constant.SEIZE_STR, md5Password(shopsStr)));
				String pingDuoDuoResult = SharedPreferencesUtils.getValue(getActivity(), Constant.default_url.replace(Constant.SEIZE_STR, md5Password(shopsStr))+Constant.PINGDUODUO);
				String[] min3wayUrls = result.split("###");
				String[] pingDuoDuoResultTitleMinurl = pingDuoDuoResult.split("###");
				LogUtil.e("\n" + "---------------------------pingDuoDUO------------------------------------------");
//				ArrayList<String> pingDuoDuoList = new ArrayList<String>();
//				for (int i = 0; i < pingDuoDuoResultTitleMinurl.length; i++) {
//					pingDuoDuoList.add(pingDuoDuoResultTitleMinurl[i]);
//				}
//				getMyMinList(pingDuoDuoList);
//				LogUtil.e("------------------------------------标题、低价链接------------------------------");
//				pingDuoDuoList = new ArrayList<String>();
//
//				for (int i = 0; i < pingDuoDuoResultTitleMinurl.length; i++) {
//					String pingDuoDuoMinurl = SharedPreferencesUtils.getValue(getActivity(), pingDuoDuoResultTitleMinurl[i] + Constant.PINGDUODUO_MINURL);
//					String pingDuoDuoTitle = SharedPreferencesUtils.getValue(getActivity(), pingDuoDuoResultTitleMinurl[i] + Constant.PINGDUODUO_TITLE);
//					pingDuoDuoList.add(pingDuoDuoTitle + "\n" + pingDuoDuoMinurl);
//				}
//				getMyMinList(pingDuoDuoList);




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
					minPingDuoDuoUrlRecord = SharedPreferencesUtils.getValue(getActivity(), spRecordMinUrlKey+Constant.PINGDUODUO);
					String shops = SharedPreferencesUtils.getValue(getActivity(), spShopRecordKey);
					String[] split = shops.split("###");
					taoSearchList = new ArrayList<String>();
					for (int i = 0; i < split.length; i++) {
						taoSearchList.add(split[i]);
					}

//					String shopsPingDuoDuo = SharedPreferencesUtils.getValue(getActivity(), spShopRecordKey);
//					String[] splitPingDuoDuo = shopsPingDuoDuo.split("###");
//                    pingDuoDuoSearchList = new ArrayList<String>();
//                    for (int i = 0; i < splitPingDuoDuo.length; i++) {
//						pingDuoDuoSearchList.add(split[i]);
//                    }
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

				double Allmoney = 7200;
				double money = 0;
				double leijiMoney = 0;
				double currentMoney = 0;
				double buyNum = 0;
				double leijibuyNum = 0;
				double buyMoney = 10;
				Boolean kuisun = true;

				int zhang = 0;
				int die = 0;


				for (int j = 0; j < 100; j++) {
					money = Arith.div(Arith.div(Allmoney,3), 24);
					currentMoney = 0;
					buyNum = 0;
					leijibuyNum = 0;
					kuisun = true;
					for (int i = 0; i < 24; i++) {

						Random random = new Random();


						//涨跌幅
						double gailv = Arith.div(Arith.sub(random.nextInt(100), 50), 10000);
						if (gailv > 0) {
							zhang++;
						} else {
							die++;
						}
						LogUtil.e("currentMoney涨跌幅:" + Arith.mul(gailv, 100));
						//现价
						buyMoney = Arith.mul(Arith.add(1, gailv), buyMoney);
						buyNum = Arith.div(money, buyMoney);
						Allmoney = Arith.sub(Allmoney, money);
						leijibuyNum = leijibuyNum + buyNum;
						LogUtil.e("currentMoney现价:" + buyMoney);
						//每段时间的现有金额
						currentMoney = Arith.mul(leijibuyNum, buyMoney);
//						LogUtil.e("currentMoney每段时间的现有金额:" + currentMoney);
						//累积投入
						leijiMoney = Arith.mul(i + 1, money);
//						LogUtil.e("currentMoney累积投入:" + leijiMoney);


						if ((currentMoney - leijiMoney) > money) {
							kuisun = false;
//							LogUtil.e("currentMoney_break:" + currentMoney);
							break;
						}


					}
					Allmoney = Arith.add(Allmoney,currentMoney);
//					LogUtil.e("currentMoney_end:" + Allmoney);
//					if (kuisun) {
//						LogUtil.e("currentMoney亏损盈利--------亏损");
//					} else {
//						LogUtil.e("currentMoney亏损盈利～～～～～～盈利");
//
//					}
					LogUtil.e("Allmoney-------:"+Allmoney);
				}

				LogUtil.e("zhangdie-------:" + zhang + "," + die);




//				String string = "地毯卧室卧室宜家米垫客厅飘窗床式床边地毯可厅毯防滑垫儿童宝宝宝爬行垫可定制";
//
//				String bidBrand = BidName.BrandName;
//				String bidStr = getSameStr(string, bidBrand);
//				String bidStrCompare = getSameStr("#" + bidStr + "#", bidBrand);
//
//
//				while (!TextUtils.isEmpty(bidStr)) {
//					bidBrand = bidBrand.replace(bidStr, "");
//					if (strLength("#" + bidStr + "#") == strLength(bidStrCompare)) {
//						string = string.replace(bidStr, "");
//					}
//					bidStr = getSameStr(string, bidBrand);
//					bidStrCompare = getSameStr("#" + bidStr + "#", bidBrand);
//				}

//				String bidStr = getSameStr(string, BidName.BrandName);
//				if (strLength(bidStr) > 2) {
//					string = string.replace(bidStr, "");
//				}
//				LogUtil.e(string);

//				foreachSearchTBLM();

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
					getShopsStr();
					spRecordMinUrlKey = md5Password(shopsStr) + Constant.MIN_URL_RECORD;
					minUrlShopNameRecordKey = md5Password(shopsStr) + Constant.MIN_NAME_RECORD;
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
			case R.id.btn_split:
				if (TextUtils.isEmpty(splitStr)) {
					splitStr = et_split.getText().toString();
				}

				if (TextUtils.isEmpty(splitStr)) {
					splitStr = SplitStr.split;
				}
				String[] split = splitStr.split("\n");
				String splitReslt = "";
				String link = "";
				int splitNum = 0;
				for (int i = 0; i < split.length; i++) {
					splitReslt = "";
					link = "";
					if (split[i].contains("发布成功，新商品")||split[i].contains("已经复制")) {
						splitReslt = "********************************************************************************" + splitNum + "\n";
						splitNum++;
						String replace = split[i].split("】")[0].replace("【", "");
						String value = SharedPreferencesUtils.getValue(getActivity(), replace);
						try {

							link = split[i].split("发布成功，新商品:")[1].split("，")[0];
							link = link.split("，")[0];
							link = link.split("id=")[1];
						} catch (Exception e) {
							link = split[i].split("已经复制，商品：")[1].split("，")[0];
							link = link.split("，")[0];
							link = link.split("id=")[1];
						}
						splitReslt = splitReslt + link + "\n" + value ;
						SharedPreferencesUtils.putValue(getActivity(), link, value);
					}
					if (!TextUtils.isEmpty(splitReslt) && splitReslt.split("\n").length > 2) {

						LogUtil.e(splitReslt);
					}
				}
				break;
			case R.id.btn_et_reset:
				splitStr = "";
				shopsStr = "";
				et_shop.setText("");
				et_split.setText("");
				break;

		}


	}



	private void foreachMinurlResult(String[] minUrls) {

		String titleArrayResult;
		ArrayList<String> myLinkList = new ArrayList<String>();
		ArrayList<String> mUploadTitleList = new ArrayList<String>();

		for (int i = 0; i < minUrls.length; i++) {
//		for (int i = 12; i < 13; i++) {

			if (null == mTitleList) {
				mTitleList = new ArrayList<String>();
			} else {
				mTitleList.clear();
			}
			titleArrayResult = "--------------" + i + "-----------------" + "\n";
			String value = SharedPreferencesUtils.getValue(getActivity(), minUrls[i] + Constant.TITLE_ARRAY_SAVE);
			if (!TextUtils.isEmpty(value)) {
				titleArrayResult = minUrls[i];
				String ids = minUrls[i].split("id=")[1].split("&ns=1")[0];
				String idsResult = "";

				myLinkList.add(minUrls[i]);
//				LogUtil.e(value);
				String[] split = value.split("###");
                if (split.length < 15) {
                    continue;
                }
				String shopName = SharedPreferencesUtils.getValue(getActivity(), minUrls[i] + Constant.TITLE_ARRAY_SAVE_SHOPNAME);

				shopName = shopName.replace(" ", "");
				LogUtil.e("shopName:" + shopName);
				mTitleList.clear();
				for (int j = 0; j <split.length; j++) {
					mTitleList.add(split[j].trim());
				}
				for (int j = 0; j < 5; j++) {
					try {
						getRandomTitle(shopName);
					} catch (Exception e) {
                        LogUtil.e(e.toString());
					}


					if (j == 0) {
						mUploadTitleList.add(mTtile);
					}
					titleArrayResult = titleArrayResult + "\n" + mTtile;
					if (TextUtils.isEmpty(idsResult)) {
						idsResult = mTtile;
					} else {
						idsResult = idsResult + "\n" + mTtile;
					}
				}
				idsResult = idsResult.replace(",", "").replace("，", "").replace("。", "");

				SharedPreferencesUtils.putValue(getActivity(), ids, idsResult);
                LogUtil.e("titleArrayResult:" + titleArrayResult);
			}
		}

		getMyMinList(myLinkList);
		getMyMinList(mUploadTitleList);


	}

	private void getMyMinList(ArrayList<String> minUrls) {
		ArrayList<String> minUrlsResultList = new ArrayList<String>();
		ArrayList<Integer> numsList = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			if (i == 0) {
				numsList.add(Constant.MIN_URL_NUMS);
			} else {
				numsList.add(numsList.get(i - 1) + Constant.MIN_URL_NUMS);
			}
		}

		String temple = "================================================" + 1 + "\n";
		for (int i = 0; i < minUrls.size(); i++) {
			int one = i / Constant.MIN_URL_NUMS;

			if (one > 0) {
				int two = one + 1;
				if (i == one * Constant.MIN_URL_NUMS) {
					temple = "================================================" + (one + 1) + "\n";
				}
				if (i >= one * Constant.MIN_URL_NUMS && i < two * Constant.MIN_URL_NUMS) {
					temple = temple + minUrls.get(i) + "\n";

					if (minUrls.size() < two * Constant.MIN_URL_NUMS - 1) {
						if (i == minUrls.size() - 1) {
							minUrlsResultList.add(temple);
						}
					} else if (i == two * Constant.MIN_URL_NUMS - 1) {
						minUrlsResultList.add(temple);
					}
				}
			} else if (one==0) {
				temple = temple + minUrls.get(i) + "\n";
				if (minUrls.size() < numsList.get(one) - 1) {
					if (i == minUrls.size() - 1) {
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
            case Constant.CREIDT_WAY:
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
