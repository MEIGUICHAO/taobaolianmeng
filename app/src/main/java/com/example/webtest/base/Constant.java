package com.example.webtest.base;

public interface Constant {

    String SEIZE_STR = "@@##$$%%^^";


    String default_url = "https://s.taobao.com/search?&uniq=imgo&q="+SEIZE_STR;
    String salesDesc_url = "https://s.taobao.com/search?&uniq=imgo&q="+SEIZE_STR+"&sort=sale-desc";
    String renqi_url = "https://s.taobao.com/search?&uniq=imgo&q="+SEIZE_STR+"&sort=renqi";



    int FIND_SAMESTYLE_FROM_TBLM = 0x1;
    int GO_SAMESTYLE_URL = 0x2;
    int WebListenerTime = 3500;
    int TBLM_WAIT_TIME = 12000;
    String uploadUrl_CATID = "https://upload.taobao.com/auction/container/publish.htm?catId=";
    String uploadUrl_ITEMID = "&itemId=";
    String CANGKU_URL = "https://sell.taobao.com/auction/merchandise/auction_list.htm?spm=a1z38n.10677092.favorite.d44.594c1deb6BWBPz&type=1";
    String taoForwardUrl = "https://s.taobao.com/search?q=";
    String taoBackwardUrl = "&s_from=newHeader&ssid=s5-e&search_type=item&sourceId=tb.item&sort=sale-desc";
    //女包
//    String URL = "https://pub.alimama.com/promo/search/index.htm?q=%E5%A5%B3%E5%8C%85&_t=1534403923857";
    //汽配 高佣
//    String URL = "https://pub.alimama.com/promo/item/channel/index.htm?channel=qqhd&catIds=14";
    //家居用品：节庆用品
////    String URL = "https://pub.alimama.com/promo/item/channel/index.htm?&channel=qqhd&catIds=12%2C122950001";
//    //家居用品：居家日用
//    String URL = "https://pub.alimama.com/promo/item/channel/index.htm?&channel=qqhd&toPage=1&catIds=12%2C21";
    //大学生开学
//    String URL = "https://pub.alimama.com/promo/search/index.htm?q=%E5%A4%A7%E5%AD%A6%E7%94%9F%E5%BC%80%E5%AD%A6&_t=1534728615404";
    //童装
    String URL = "https://pub.alimama.com/promo/item/channel/index.htm?channel=muying&toPage=1&catIds=50008165&level=1";

    //    String URL = "https://pub.alimama.com/promo/item/channel/index.htm?spm=a219t.7900221/1.1998910419.d07bd19c4.2a8f75a5Zm3POv&channel=nzjh";
//    String FILTER = "startBiz30day=" + 200 + "&startPrice=" + 0 + "&endPrice=" + 50;
    String FILTER = "startBiz30day=" + 50 + "&startPrice=" + 0 + "&endPrice=" + 50 + "&hPayRate30=1";

    String TBLMTAG = "TBLM:";
    String MIN_URL_RECORD = "MIN_URL_RECORD";

    String SHOP_LIST = "SHOP_LIST";
    String MIN_NAME_RECORD = "MIN_NAME_RECORD";
    int NEXT_PAGE_LOAD = 0x3;

    int EDIT_DETAIL = 0x4;
    String SearchUrl= "https://pub.alimama.com/promo/search/index.htm?q=";
    int CANGKU_NEXT_PAGE_LOAD = 0x5;

    int EDIT_DETAIL_COMPLETE = 0x6;

    int DEFAULT_WAY = 0x7;

    int SALES_DESC = 0x8;

    int RENQI_WAY = 0x9;
    int WAY3_SAMESTYTLE = 0x10;


    String TITLE_ARRAY_SAVE = "TITLE_ARRAY_SAVE";
    String TITLE_ARRAY_SAVE_SHOPNAME = "TITLE_ARRAY_SAVE_SHOPNAME";
    Integer MIN_URL_NUMS = 15;

}
