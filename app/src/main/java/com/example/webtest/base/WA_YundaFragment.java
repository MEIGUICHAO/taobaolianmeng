package com.example.webtest.base;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.webtest.io.LogUtil;
import com.example.webtest.io.SharedPreferencesUtils;
import com.example.webtest.io.WA_Parameters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * @author z.h
 * @desc 存放基本业务逻辑&Js调用本地方法的接口函数
 */
public class WA_YundaFragment extends WA_BaseFragment
{



	protected Instrumentation instrumentation;
	private HashMap<String, Float> jzlMap;
	private HashMap<String, Float> rcMap;
	private HashMap<String, Float> titleMap;
	protected String[] shops;
	protected int index = 0;
	protected int toPage = 1;
	protected int randomtime = 1000;
	protected String resultStr = "";
	protected String rcresultStr = "";
	protected String titleresultStr = "";
	protected String TAOBAO = "TAOBAO";
	protected String TAOBAOJZL = "TAOBAOJZL";
	protected String TAOBAORC = "TAOBAORC";
	protected String TAOBAOTITLE = "TAOBAOTITLE";
	protected ArrayList<String> taoSearchList;
	protected ArrayList<String> taoNameList;
	protected int searIndex;
	protected int currentPShopSize;
	protected String[] titlesArray;
	protected String spRecordMinUrlKey,minUrlShopNameRecordKey;
	protected String beginUrl;


	private ArrayList<String> mTitleList;
	private boolean pageNextStop = false;
    private boolean PU_SHOP_LIST = true;
    protected boolean IS_CANGKU = false;
	protected ArrayList<String> xiajiaRecordList;
	protected int shangjiaIndex;
	protected boolean NEXT_PAGE_END = false;


	protected enum SearchType
	{
		All, Shop, Mall
	}

	public Handler handler = new Handler();

	protected void goSearchClick() {

		listWeb.loadUrl("https://sycm.taobao.com/mq/words/search_words.htm");
//		handlerJs("goSearchClick();");
	}
	protected void goSearchWord() {

		handlerJs("titleCombination();");
	}
	public void biao1() {
		listWeb.reload();
		handlerJs("relativeTitle();",3000+randomtime);
//		handlerJs("relativeTitle();");
	}
	protected void goGetChecked() {

		handlerJs("goGetChecked();");
	}
	protected void check() {

		handlerJs("check();");
	}

	protected void goSearch(final String search,int randomtime) {
//		handlerJs("setSearchWord(\""+search+"\",\""+randomtime+"\");");
		handlerJs("setSearchWord(\"" + search + "\");");
	}

	protected void handlerJs(final String strlogic) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				String logicStr = strlogic;
				String completeJs = doAutoTest(logicStr);
				loadUrl(listWeb, completeJs);
			}
		});
	}

	public void handlerJs(final String strlogic,long time) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				String logicStr = strlogic;
				String completeJs = doAutoTest(logicStr);
				loadUrl(listWeb, completeJs);
			}
		},time);
	}

	/** Function：选择商品所在的商铺类型(天猫或淘宝) */
	protected String selectSearchType(boolean isTMall)
	{
		String str = "var sortType= doGetTextByCN(\"s-input-tab-txt\");" + "if(!" + isTMall + "){" + "if(sortType!=\"天猫\"){" + "doClickByCN(\"s-input-tab-txt\",1);" + "doClickByCN(\"all\",2);" + "doClickByCN(\"s-input-tab-txt\",2);" + "}}else{" + "if(sortType!=\"宝贝\"){" + "doClickByCN(\"s-input-tab-txt\",1);" + "doClickByCN(\"mall\",2);" + "doClickByCN(\"s-input-tab-txt\",2);" + "}}";
		return str;
	}

	/** Function：点击进入搜索(BelongTo Step1) */
	protected void doEnterSearchPage()
	{
		handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				enterSearchPage(listWeb);
			}
		});
	}

	/** Function：选择商铺类型(BelongTo Step2) */
	protected void doSelectStoreType(final WA_Parameters parameter)
	{
		handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				selectStoreType(listWeb, parameter.getIsTMall());
			}
		});
	}

	/** Function：进行商品搜索(BelongTo Step2) */
	protected void doSearch(final WA_Parameters parameter)
	{
		handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				searchFor(listWeb, parameter.getKeywordStr());
			}
		});
	}

	/** Function：首次进行商品浏览(BelongTo Step3) */
	protected void doScan(final WA_Parameters parameter)
	{
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				scanGoods(listWeb, parameter.getTitleStr());
			}
		}, 4000);

	}

	/** Function：根据销量排序(BelongTo Step4) */
	protected void doOrderBySellAmount()
	{

		handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				orderBySellAmount(listWeb);
			}
		});
	}

	/** Function：若当前页中不存在该商铺则翻页，同时另一个页面进行随机商品浏览，浏览时长随机(BelongTo Step5) */
	protected void doFlipAndScan(final WA_Parameters parameter, final int randomTime)
	{
		// 跳转到下一页
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				getNextPage(listWeb);
			}
		}, 2000);

		// 在当前页查找，若没查到则翻到下一页递归查找
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				scanGoods(listWeb, parameter.getTitleStr());
			}
		}, 5000 + randomTime * 1000);
	}

	/** Function：不翻页，在当前页进行随机商品浏览，浏览时长随机(BelongTo Step5) */
	protected void doScanForLongTime(final WA_Parameters parameter, final int randomTime)
	{
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				scanGoods(listWeb, parameter.getTitleStr());
			}
		}, 5000 + randomTime * 1000);

	}


	/** Function：选择商品SKU */
	protected void doSelectSku()
	{

	}

	/** 点击进入搜索页面(主页面) */
	private void enterSearchPage(WebView webView)
	{
		// 拼接业务逻辑
//		String logicStr = "doClickByRI(\"search-placeholder\",2);";
		//侧滑菜单
//		String logicStr = "doClickByCN(\"button button-icon button-clear\",2);";
		String logicStr = "selectNumRange(3,2);";
//		String logicStr = "selectNumRange(\"col col-50 bet ok\",2);";
//		String logicStr = "selectNumRange(2);";
		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 选择店铺类型 */
	private void selectStoreType(WebView webView, boolean isTMall)
	{
		// 拼接业务逻辑
//		String logicStr = selectSearchType(isTMall);
		String logicStr = "doComfir();";
		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 输入搜索内容，然后查找 */
	private void searchFor(WebView webView, String keywordStr)
	{
		// 拼接业务逻辑
		String logicStr = "doInputByCN(\"J_autocomplete\",\"" + keywordStr + "\",2);" + "doClickByCN(\"icons-search\",4);";

		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 点击筛选按钮 */
	private void filterGoods(WebView webView)
	{
		// 拼接业务逻辑
		String logicStr = "doTapByRI(\"J_Sift\");";

		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 确定筛选条件 */
	private void confirmFilter(WebView webView)
	{
		// 拼接业务逻辑
		String logicStr = "doClickByRI(\"J_SiftCommit\",2);";

		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 按销量优先排序 */
	private void orderBySellAmount(WebView webView)
	{
		// 拼接业务逻辑
		String logicStr = "doTapByParentCN(\"sort-tab\",\"sort\");";

		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 浏览商铺 */
	private void scanGoods(WebView webView, String titleStr)
	{
		// 拼接业务逻辑
		String logicStr = "var currentPage=doGetTextByCNByInner(\"currentPage\");" + "var totalSize=getSize(\"list-item\");"
				// +"localMethod.JI_showToast(totalSize);"
				// + "localMethod.JI_showToast(currentPage);"
				+ "doTapForScanGoodsByTitle(\"list-item\",\"d-title\",\"" + titleStr + "\",currentPage,totalSize);";

		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 关闭提示框 */
	private void alertHide(WebView webView)
	{
		// 拼接业务逻辑
		String logicStr = "doClickByCN(\"btn-hide\",2);";

		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 进入目标商铺 */
	private void enterShop(WebView webView, String url)
	{
		webView.loadUrl("https:" + url);
	}

	private void skuSelect(WebView webView)
	{
		// 拼接业务逻辑
		String logicStr = "doTapByCN02(); ";

		String completeJs = doAutoTest(logicStr);
		loadUrl(webView, completeJs);
	}

	/** 翻页 */
	private void getNextPage(WebView mWebView)
	{
		String logicStr = "doTapByCN(\"J_PageNext\"); ";

		String completeJs = doAutoTest(logicStr);
		loadUrl(mWebView, completeJs);
	}

	/* 暴露给JavaScript脚本调用的方法* */
	public class LocalMethod
	{
		Context mContext;
		private WA_Parameters parameter;

		public LocalMethod(Context c, WA_Parameters parameter)
		{
			this.mContext = c;
			this.parameter = parameter;
		}

		public WA_Parameters getParameter()
		{
			return parameter;
		}

		public void setParameter(WA_Parameters parameter)
		{
			this.parameter = parameter;
		}

		@JavascriptInterface
		public void cangkuList(String cangkuids)
		{

            if (null == xiajiaRecordList || xiajiaRecordList.size() < 1) {
                xiajiaRecordList = new ArrayList<String>();
                shangjiaIndex = 0;
            }
            final String[] split = cangkuids.split("###");
            for (int i = 0; i < split.length; i++) {
                xiajiaRecordList.add(split[i]);
            }
		}



		@JavascriptInterface
		public void cangkuForeach()
		{
			NEXT_PAGE_END = true;
			goEditDetailsUrl();
		}


		@JavascriptInterface
		public void showKeyboard()
		{
//			listWeb.requestFocus(View.FOCUS_DOWN);
//			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			showGuide();

		}


		@JavascriptInterface
		public void titleResult(String name,String count)
		{

			if (null == titleMap) {
				titleMap = new HashMap<String, Float>();
			}

			if (getWordCount(name)>2){
				titleMap.put(name,Float.parseFloat(count));
			}

		}

		@JavascriptInterface
		public void shopResult(String name,String jzl,String rc)
		{

			if (null == jzlMap) {
				jzlMap = new HashMap<String, Float>();
			}
			if (null == rcMap) {
				rcMap = new HashMap<String, Float>();
			}
			if (Float.parseFloat(jzl)>Float.parseFloat("0.2")){
				jzlMap.put(name, Float.parseFloat(jzl));
			}
			if (Float.parseFloat(rc)>Float.parseFloat("0.2")){
				rcMap.put(name, Float.parseFloat(rc));
			}



		}


		@JavascriptInterface
		public void getHotShopResult()
		{
			Log.e(TAG, "------------------------------------------------");
			sortMap(jzlMap,"---------------------zjl---------------------------"+shops[index]+"\n");
			Log.e(TAG, "*************************************************");
			sortMap(rcMap,"---------------------rc---------------------------"+shops[index]+"\n");
			mapClear();

			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					biao1();
				}
			},randomtime);
//			listWeb.reload();
//			handlerJs("relativeTitle();",3000);

		}

		@JavascriptInterface
		public void getTitleResult()
		{
			Log.e(TAG, "--------------------title----------------------------");
			sortTitleMap(titleMap,"---------------------title---------------------------"+shops[index]+"\n");
			titleMap.clear();
			index++;
			randomtime =3000+(int)(Math.random()*2000);		//返回大于等于m小于m+n（不包括m+n）之间的随机数
			if (index<shops.length){
				goSearch(shops[index],randomtime);
			}

		}




		@JavascriptInterface
		public void lianmengArray(String array)
		{
			String[] split = array.split("###");
			if (null == taoSearchList) {
				taoSearchList = new ArrayList<String>();
			}
			if (null == taoNameList) {
				taoNameList = new ArrayList<String>();
			}
			searIndex = 0;
			currentPShopSize = split.length - 1;

			for (int i = 1; i < split.length; i++) {
				taoSearchList.add(Constant.taoForwardUrl + getURLEncoderString(split[i]) + Constant.taoBackwardUrl);
				taoNameList.add(split[i]);
			}
			if (pageNextStop) {
				foreachSearchTBLM();
			} else {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
//						handlerJs("tblmShopList();");
						SwitchMethod = Constant.NEXT_PAGE_LOAD;

						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								toPage++;
								String url = beginUrl + "&userType=0&jpmj=1&" + Constant.FILTER + "&level=1" + "&toPage=" + toPage + "&perPageSize=100";
								LogUtil.e(Constant.TBLMTAG + url);
								loadUrl(url);
							}
						});
					}
				});
			}


//			foreachSearchTBLM();

		}



		@JavascriptInterface
		public void titleArrayList(String titles,String minPricesTitle)
		{
			String[] split = titles.split("###");
			LogUtil.e(Constant.TBLMTAG + "minPricesTitle"+minPricesTitle);
			String templeRecord = "123";
			mTitleList = new ArrayList<String>();
			for (int i = 0; i <titlesArray.length; i++) {
				mTitleList.add(titlesArray[i]);
			}

			int foreachSize = 5;
			if (split.length < 5) {
				foreachSize = split.length;
			}
			for (int i = 1; i < foreachSize; i++) {
				if (!templeRecord.contains(split[i])) {
					templeRecord = templeRecord + split[i];
					int mLen = strLength(split[i]);
					int splitLen = mLen / 6;
//					LogUtil.e(Constant.TBLMTAG + "mLen" + mLen);
//					LogUtil.e(Constant.TBLMTAG + "split" + split[i]);
					String titleArray1 = split[i].substring(0, splitLen);
//					LogUtil.e(Constant.TBLMTAG + "titleArray1" + titleArray1);
					String titleArray2 = split[i].substring(splitLen, 2 * splitLen);
//					LogUtil.e(Constant.TBLMTAG + "titleArray2" + titleArray2);
					String titleArray3 = split[i].substring(2 * splitLen, mLen/2);
//					LogUtil.e(Constant.TBLMTAG + "titleArray3" + titleArray3);
					mTitleList.add(titleArray1);
					mTitleList.add(titleArray2);
					mTitleList.add(titleArray3);
				}
			}

			int[] ints = randomArray(mTitleList.size());
			String mTtile = "";
			for (int j = 0; j < mTitleList.size(); j++) {
				if (strLength(mTtile) + strLength(mTitleList.get(ints[j]))< 120) {
					if (ints[j] < mTitleList.size()) {
						mTtile = mTtile + mTitleList.get(ints[j]);
					}
				} else if (strLength(mTtile) < 100) {
					continue;
				} else {
					break;
				}
			}
			SharedPreferencesUtils.putValue(getActivity(), minPricesTitle, mTtile);
			LogUtil.e(Constant.TBLMTAG + "mTtile: " + SharedPreferencesUtils.getValue(getActivity(), minPricesTitle));
		}


		@JavascriptInterface
		public void pageNextStop()
		{
			pageNextStop = true;
		}

		@JavascriptInterface
		public void showKeyboardAdfterShangjia()
		{

			new Thread( new Runnable( ) {
				@Override
				public void run() {
					try {
						Thread.sleep( 1000 );
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					// “旋转”的拼音
//				int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X,KeyEvent.KEYCODE_U,KeyEvent.KEYCODE_A,KeyEvent.KEYCODE_N,KeyEvent.KEYCODE_SPACE,KeyEvent.KEYCODE_Z,KeyEvent.KEYCODE_H,KeyEvent.KEYCODE_U,KeyEvent.KEYCODE_A,KeyEvent.KEYCODE_N};
					int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X,KeyEvent.KEYCODE_DEL};
					for( int keycode : keyCodeArray ){
						try {
							typeIn( keycode );
							Thread.sleep( 200 );
							handlerJs("shangjiaAfterEditTitle();", 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							handlerJs("shangjiaAfterEditTitle();", 1000);
						}
					}
				}
			}).start( );


		}

		@JavascriptInterface
		public void getSplitTitle(String[] array)
		{
			titlesArray = array;
		}


		@JavascriptInterface
		public void editByOriginalTitle(String originalTitle)
		{

			String mTitle = SharedPreferencesUtils.getValue(getActivity(), originalTitle);
			if (TextUtils.isEmpty(mTitle)) {
				mTitle = originalTitle;
			}
			handlerJs("showKeyboardAdfterShangjia(\"" + mTitle + "\")",1000);

		}

		@JavascriptInterface
		public void noSame()
		{
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					foreachSearchTBLM();
				}
			});

		}


		@JavascriptInterface
		public void goSameUrl(final String url)
		{
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					SwitchMethod = Constant.GO_SAMESTYLE_URL;
					loadUrl(url);
				}
			});

		}


		@JavascriptInterface
		public void getMinPricesUrl(String url,String name)
		{
			if (!minUrlRecord.contains(url)) {
                if (TextUtils.isEmpty(minUrlRecord)) {
                    minUrlRecord = url;
                } else {
                    minUrlRecord = minUrlRecord + "###" + url;
                }
				LogUtil.e(Constant.TBLMTAG + "minUrl" + "\n" + url);


				if (TextUtils.isEmpty(minUrlShopNameRecord)) {
					minUrlShopNameRecord = name;
				} else {
					minUrlShopNameRecord = minUrlShopNameRecord + "###" + name;
				}
				LogUtil.e(Constant.TBLMTAG + "minUrlShopNameRecord" + "\n" + name);
			}

		}


		@JavascriptInterface
		public void afterSameResult()
		{
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					foreachSearchTBLM();
				}
			});

		}

		@JavascriptInterface
		public void JI_showToast(String content)
		{
			Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
		}

		@JavascriptInterface
		public void JI_LOG(String content)
		{
			LogUtil.e(TAG, "JI_LOG: " + content);
		}


		@JavascriptInterface
		public void TBLM_LOG(String content)
		{
			LogUtil.e(Constant.TBLMTAG + content);
		}

		@JavascriptInterface
		public void JI_scrollView()
		{
			listWeb.scrollBy(0, 1800);
		}

		@JavascriptInterface
		public void JI_doGetNextPage(int randomTime)
		{
			doFlipAndScan(parameter, randomTime);
		}

		@JavascriptInterface
		public void JI_doScanCurrentPage(int randomTime)
		{

			doScanForLongTime(parameter, randomTime);
		}

		@JavascriptInterface
		public void JI_createLog(String infoStr) throws IOException
		{
			createLog(infoStr);
		}


		@JavascriptInterface
		public void getTargetIndex() throws IOException
		{
			LogUtil.e("------------getTargetIndex------------");
			handlerJs("operaSearch();");
		}
	}

	protected void goEditDetailsUrl() {

		String itemId = xiajiaRecordList.get(shangjiaIndex);
		final String url = Constant.uploadUrl_CATID + itemId.split("@@@")[1] + Constant.uploadUrl_ITEMID + itemId.split("@@@")[0];

		getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SwitchMethod = Constant.EDIT_DETAIL;
                listWeb.loadUrl(url);
            }
        });
    }

    protected void foreachSearchTBLM() {
		getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (PU_SHOP_LIST) {
                    String spShopRecord = "";
                    for (int i = 0; i <taoSearchList.size() ; i++) {
                        if (TextUtils.isEmpty(spShopRecordKey)) {
                            spShopRecord = taoSearchList.get(i);
                        } else {
                            spShopRecord = spShopRecord + "###" + taoSearchList.get(i);
                        }
                    }
                    SharedPreferencesUtils.putValue(getActivity(),spShopRecordKey,spShopRecord);
                    PU_SHOP_LIST = false;
					currentPShopSize = taoSearchList.size();
                }
				if (searIndex < currentPShopSize) {
					SwitchMethod = Constant.FIND_SAMESTYLE_FROM_TBLM;
					if (searIndex == currentPShopSize - 1) {
						SharedPreferencesUtils.putValue(getActivity(), spRecordMinUrlKey, minUrlRecord);
						SharedPreferencesUtils.putValue(getActivity(), minUrlShopNameRecordKey, minUrlShopNameRecord);
					}
					loadUrl(taoSearchList.get(searIndex));
				}

            }
        });
	}

	public int getWordCount(String s)
	{
		int length = 0;
		for(int i = 0; i < s.length(); i++)
		{
			int ascii = Character.codePointAt(s, i);
			if(ascii >= 0 && ascii <=255)
				length++;
			else
				length += 2;

		}
		return length;

	}

	private void sortMap(Map map,String str) {
		List<Map.Entry<String,Float>> list = new ArrayList<Map.Entry<String,Float>>(map.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Float>>() {
            //升序排序
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });

		for(Map.Entry<String,Float> mapping:list){
			str = str + mapping.getKey()+"######"+mapping.getValue()+"\n";
        }
		putSp(str);
			Log.e("sortMap: ",str);
	}

	private void putSp(String str) {
//		if (str.contains("------title")){
//			SharedPreferencesUtils.putValue(getActivity(), TAOBAO, shops[index]+"title", str);
//		} else if (str.contains("------zjl")){
//			SharedPreferencesUtils.putValue(getActivity(), TAOBAO, shops[index]+"zjl", str);
//		} else if (str.contains("------rc")){
//			SharedPreferencesUtils.putValue(getActivity(), TAOBAO, shops[index]+"rc", str);
//		}
	}

	private void sortTitleMap(Map map,String str) {
		List<Map.Entry<String,Float>> list = new ArrayList<Map.Entry<String,Float>>(map.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Float>>() {
            //升序排序
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });

		for(Map.Entry<String,Float> mapping:list){
			str = str + mapping.getKey()+"\n";
        }
		putSp(str);
			Log.e("sortMap: ",str);
	}

	public void mapClear()
	{

		if (null != jzlMap) {
			jzlMap.clear();
		}
		if (null != rcMap) {
			rcMap.clear();
		}
	}


	private void showGuide( ){
		new Thread( new Runnable( ) {
			@Override
			public void run() {
				try {
					Thread.sleep( 1000 );
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				// “旋转”的拼音
//				int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_X,KeyEvent.KEYCODE_U,KeyEvent.KEYCODE_A,KeyEvent.KEYCODE_N,KeyEvent.KEYCODE_SPACE,KeyEvent.KEYCODE_Z,KeyEvent.KEYCODE_H,KeyEvent.KEYCODE_U,KeyEvent.KEYCODE_A,KeyEvent.KEYCODE_N};
				int[] keyCodeArray = new int[]{KeyEvent.KEYCODE_DEL,KeyEvent.KEYCODE_DEL};
				for( int keycode : keyCodeArray ){
					try {
						typeIn( keycode );
						Thread.sleep( 200 );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start( );
	}

	private void typeIn( final int KeyCode ){
		try {
			Instrumentation inst = new Instrumentation();
			inst.sendKeyDownUpSync( KeyCode );
		} catch (Exception e) {
			Log.e("Exception：", e.toString());
		}
	}



	public int strLength(String value) {
		if (TextUtils.isEmpty(value)) {
			return 0;
		}
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	public int[] randomArray(int size){
		int splitNum = 30;
		int max = mTitleList.size() - 1;
		if (null == mTitleList || mTitleList.size() < 1) {
			max = size;
		}
		int len = max - 0 + 1;

		if (splitNum > len) {
			splitNum = len;
		}
		if(max < 0 || splitNum > len){
			return null;
		}

		//初始化给定范围的待选数组
		int[] source = new int[len];
		for (int i = 0; i < 0+len; i++){
			source[i-0] = i;
		}

		int[] result = new int[splitNum];

		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			//待选数组0到(len-2)随机一个下标
			index = Math.abs(rd.nextInt() % len--);
			//将随机到的数放入结果集
			result[i] = source[index];
			//将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
			source[index] = source[len];
		}
		return result;
	}


}
