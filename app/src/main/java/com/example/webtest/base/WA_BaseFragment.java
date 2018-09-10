package com.example.webtest.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * @author z.h
 * @des commmon tools
 */
public class WA_BaseFragment extends Fragment
{


	protected EditText et_shop;
	protected EditText et_split;
	protected Button btn_et_reset;
	protected Button btn_split;
	protected Button btnRefresh;
	protected Button btnBack;
	protected Button btnSearch;
	protected Button btnGosearch;
	protected Button btnGosearchworld;
	protected Button btnGetchecked,btn_check,btn_biao1;
	protected Button btn_str_result;
	protected MyWebView listWeb;
	protected int SwitchMethod = -1;
	protected int nextPageIndex = 0;
	protected String minUrlRecord = "";
	protected String minUrlShopNameRecord = "";
	protected String spShopRecordKey = "";
	protected String shopsStr = "";
	protected String splitStr = "";




	private static final String LOG_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/web_auto.log";

	/** 注入需自动执行的JS代码 */
	protected String doAutoTest(String code)
	{
		return "function doAutoTest() { " + code + "}";
	}

	/** 组装整个JS代码 */
	protected String buildTest(String logicStr)
	{
		String js = "var newscript = document.createElement(\"script\");" + "newscript.text = window.onload=doAutoTest();" + logicStr + "document.body.appendChild(newscript);";
		return js;
	}

	protected void loadUrl(final String url)
	{

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listWeb.loadUrl(url);
			}
		});
	}

	/** Load JS代码，然后会自动执行doAutoTest()里的内容 */
	protected void loadUrl(WebView webView, String logicStr)
	{
		String js = buildTest(logicStr);
		Log.e(TAG, "loadUrl: " + js);
		webView.loadUrl("javascript:" + js);
	}

	/** 注入本地文件中的JS方法 */
	protected String getJsFromFile(Activity mContext, String jsPath)
	{
		InputStream in = null;
		try
		{
			in = mContext.getBaseContext().getAssets().open(jsPath);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		byte buff[] = new byte[1024];
		ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
		do
		{
			int numRead = 0;
			try
			{
				numRead = in.read(buff);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			if (numRead <= 0)
			{
				break;
			}
			fromFile.write(buff, 0, numRead);
		} while (true);

		return fromFile.toString();
	}

	/** 创建本地日志文件 */
	protected void createLog(String infoStr)
	{

		FileWriter fw = null;
		try
		{
			fw = new FileWriter(LOG_FILE_PATH, true);
			fw.write("\r\n" + infoStr + "\r\n");
			fw.flush();

		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				fw.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	/** 删除本地日志文件 */
	protected void deleteLog()
	{
		File file = new File(LOG_FILE_PATH);
		if (file.exists())
		{
			file.delete();
		} else
		{
			Toast.makeText(getActivity(), "This file is not exist!", Toast.LENGTH_SHORT).show();
		}
	}

	/** 线程延时 */
	protected void doSleep(int time)
	{
		try
		{
			Thread.sleep(time * 1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/** 线程终止 */
	protected void doInterreput()
	{
		Thread.interrupted();
	}


	public String getURLEncoderString(String str) {//url编码
		String result = "";
		if (null == str) {
			return "";
		}
		try {
			result = java.net.URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String md5Password(String password) {

		try {
			// 得到一个信息摘要器
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(password.getBytes());
			StringBuffer buffer = new StringBuffer();
			// 把每一个byte 做一个与运算 0xff;
			for (byte b : result) {
				// 与运算
				int number = b & 0xff;// 加盐
				String str = Integer.toHexString(number);
				if (str.length() == 1) {
					buffer.append("0");
				}
				buffer.append(str);
			}

			// 标准的md5加密后的结果
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

	}

    public String getSameStr(String s1, String s2) {
		String max = "",min = "";
		max = (s1.length()>s2.length())?s1: s2;
		min = (max==s1)?s2: s1;
//		sop("max="+max+"...min="+min);
		for(int x=0; x<min.length(); x++)
		{
			for(int y=0,z=min.length()-x; z!=min.length()+1; y++,z++)
			{
				String temp = min.substring(y,z);
				if(max.contains(temp))//if(s1.indexOf(temp)!=-1)
					return temp;
			}
		}
		return "";


	}

	public boolean getBidResult(String keyword) {
		String bidNameMd5 = md5Password(BidName.BrandName);
		String replace = BidName.BrandName.replace(keyword, "");
		return bidNameMd5.equals(md5Password(replace));
	}

	public boolean judgeContainsStr(String str) {
		String regex=".*[a-zA-Z]+.*";
		Matcher m= Pattern.compile(regex).matcher(str);
		return m.matches();
	}

	public void getShopsStr() {
		if (TextUtils.isEmpty(shopsStr)) {
			shopsStr = et_shop.getText().toString();
		}
		if (TextUtils.isEmpty(shopsStr)) {
			shopsStr = Shops.shops;
		}
	}

}
