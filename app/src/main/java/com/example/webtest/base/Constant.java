package com.example.webtest.base;

public interface Constant {


    int FIND_SAMESTYLE_FROM_TBLM = 0x1;
    int GO_SAMESTYLE_URL = 0x2;
    int WebListenerTime = 3500;
    int TBLM_WAIT_TIME = 12000;
    String taoForwardUrl = "https://s.taobao.com/search?q=";
    String taoBackwardUrl = "&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&sort=sale-desc";
    //女包
    String URL = "https://pub.alimama.com/promo/search/index.htm?q=%E5%A5%B3%E5%8C%85&_t=1534403923857";
//    String URL = "https://pub.alimama.com/promo/item/channel/index.htm?spm=a219t.7900221/1.1998910419.d07bd19c4.2a8f75a5Zm3POv&channel=nzjh";
//    String FILTER = "startBiz30day=" + 200 + "&startPrice=" + 0 + "&endPrice=" + 50;
    String FILTER = "startBiz30day=" + 100 + "&startPrice=" + 51 + "&endPrice=" + 100+"&hPayRate30=1";

    String TBLMTAG = "TBLM:";
    String MIN_URL_RECORD = "MIN_URL_RECORD";

    String SHOP_LIST = "SHOP_LIST";
    String MIN_NAME_RECORD = "MIN_NAME_RECORD";
    int NEXT_PAGE_LOAD = 0x3;

}
