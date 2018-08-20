package com.example.webtest.base;

public interface Constant {


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
    String URL = "https://pub.alimama.com/promo/search/index.htm?q=%E5%A4%A7%E5%AD%A6%E7%94%9F%E5%BC%80%E5%AD%A6&_t=1534728615404";

    //    String URL = "https://pub.alimama.com/promo/item/channel/index.htm?spm=a219t.7900221/1.1998910419.d07bd19c4.2a8f75a5Zm3POv&channel=nzjh";
//    String FILTER = "startBiz30day=" + 200 + "&startPrice=" + 0 + "&endPrice=" + 50;
    String FILTER = "startBiz30day=" + 50 + "&startPrice=" + 0 + "&endPrice=" + 50 + "&hPayRate30=1";

    String TBLMTAG = "TBLM:";
    String MIN_URL_RECORD = "MIN_URL_RECORD";

    String SHOP_LIST = "SHOP_LIST";
    String MIN_NAME_RECORD = "MIN_NAME_RECORD";
    int NEXT_PAGE_LOAD = 0x3;

    int EDIT_DETAIL = 0x4;

    int CANGKU_NEXT_PAGE_LOAD = 0x5;

    int EDIT_DETAIL_COMPLETE = 0x6;


    String shops = "割草机\n" +
            "遮阳网\n" +
            "喷壶\n" +
            "割草机 小型 多功能 农用\n" +
            "营养土\n" +
            "营养土 包邮 种植土 通用型\n" +
            "浇花喷壶\n" +
            "打草机\n" +
            "电动喷雾器\n" +
            "电动割草机\n" +
            "高枝剪\n" +
            "鹅卵石\n" +
            "摘果器 高空摘果器\n" +
            "花盆托盘\n" +
            "除草剂\n" +
            "遮阳网防晒网加密加厚 隔热\n" +
            "自动浇花器\n" +
            "防晒网\n" +
            "弹珠\n" +
            "海绵宝宝吸水珠\n" +
            "遮阳网防晒网加密加厚\n" +
            "喷雾器\n" +
            "割草机四冲程\n" +
            "竹子\n" +
            "水管\n" +
            "多菌灵\n" +
            "多肉土专用营养土\n" +
            "海绵宝宝水晶泥\n" +
            "植物营养液通用型\n" +
            "雾化喷头\n" +
            "多肉 土\n" +
            "竹竿\n" +
            "草甘膦\n" +
            "割草机 背负式\n" +
            "割汽油草机\n" +
            "育苗盘\n" +
            "滴灌带\n" +
            "植物营养液\n" +
            "割草机四冲程背负式\n" +
            "软水管\n" +
            "降温雾化喷头\n" +
            "木栅栏\n" +
            "土\n" +
            "电动喷雾器 农用\n" +
            "除草机小型 多功能\n" +
            "修枝剪\n" +
            "喷壶 浇花\n" +
            "四冲程背负式割草机\n" +
            "滴灌管\n" +
            "电动喷雾器 农用高压 多功能\n" +
            "蛭石\n" +
            "遮阳布户外 防晒 隔热\n" +
            "割草机 电动 家用\n" +
            "除草机 割草机\n" +
            "雨花石\n" +
            "多肉营养土\n" +
            "竹篱笆\n" +
            "吸水石\n" +
            "海绵宝宝\n" +
            "电动割草机 充电\n" +
            "花剪\n" +
            "电动喷雾器 锂电池\n" +
            "洒水壶 浇花 家用\n" +
            "浇花洒水壶\n" +
            "遮阳网防晒网加密加厚隔热\n" +
            "割草机刀片\n" +
            "鹅卵石 石头\n" +
            "除草剂 草甘膦\n" +
            "花土\n" +
            "割草机 小型 多功能 农用汽油\n" +
            "陶粒\n" +
            "割草机配件\n" +
            "遮阴网\n" +
            "喷水壶\n" +
            "家用浇花喷壶\n" +
            "营养液\n" +
            "土壤 种植土\n" +
            "吸水珠\n" +
            "农用喷雾器\n" +
            "多功能割草机\n" +
            "花盆托盘 塑料\n" +
            "喷雾器 电动\n" +
            "农用水带\n" +
            "割草机 家用\n" +
            "花土壤包邮 盆栽通用\n" +
            "高空剪枝剪\n" +
            "滴灌\n" +
            "锄头\n" +
            "汽油喷药机\n" +
            "种植土\n" +
            "打草机 割草机 除草机\n" +
            "兰花营养土\n" +
            "园艺剪刀\n" +
            "假山摆件 配件\n" +
            "弥雾机\n" +
            "石头\n" +
            "土工布\n" +
            "洒水壶\n" +
            "手推式割草机\n" +
            "遮阳网包边打孔\n" +
            "自动浇花\n" +
            "摘果器\n" +
            "花盆垫底托盘\n" +
            "泡大珠\n" +
            "橡胶水管 软管\n" +
            "磷酸二氢钾\n" +
            "花盆托盘 圆形\n" +
            "水管 软管 家用\n" +
            "浇水壶 小型\n" +
            "篱笆\n" +
            "农药喷雾器\n" +
            "草坪机\n" +
            "油锯\n" +
            "防晒网遮阳网 隔热网\n" +
            "万向轮花盆托盘\n" +
            "微喷带\n" +
            "防晒网 遮阳网 隔热网\n" +
            "水晶宝宝\n" +
            "高枝锯\n" +
            "遮阳网 防晒\n" +
            "水带\n" +
            "电动喷雾器送风筒\n" +
            "打草机 割草机 除草机 多功能\n" +
            "兰花土\n" +
            "除草机\n" +
            "除草剂 杂草\n" +
            "浇水水管\n" +
            "椰砖\n" +
            "喷雾器 农用 电动 高压 多功能\n" +
            "移动花盆托盘\n" +
            "背负式电动喷雾器\n" +
            "摘果剪\n" +
            "种植土 营养土\n" +
            "浇花水管套装\n" +
            "割草机 小型家用\n" +
            "浇花神器\n" +
            "绿篱机\n" +
            "玻璃球\n" +
            "充电喷雾器 农用\n" +
            "喷雾器农用\n" +
            "豆芽菜育苗盘\n" +
            "玻璃珠\n" +
            "盆景摆件\n" +
            "兰花土专用土\n" +
            "阳台装饰\n" +
            "育苗盆\n" +
            "家用浇花水管 软管\n" +
            "多肉土\n" +
            "喷农药壶\n" +
            "营养土通用型\n" +
            "割灌机\n" +
            "1.5寸水管\n" +
            "洒水壶 浇花\n" +
            "浇水壶\n" +
            "背负式割草机\n" +
            "苏云金杆菌\n" +
            "耕地机\n" +
            "小喷壶园艺\n" +
            "95%草甘膦\n" +
            "爬藤网\n" +
            "生根\n" +
            "泥炭土\n" +
            "灌溉水带\n" +
            "绿篱修剪机\n" +
            "白色鹅卵石\n" +
            "割草机 汽油\n" +
            "浇花水壶\n" +
            "喷雾器 农用 电动\n" +
            "硫酸亚铁\n" +
            "高压水管\n" +
            "颗粒土 多肉\n" +
            "营养土 包邮\n" +
            "修剪刀\n" +
            "遮阳网 防晒网\n" +
            "草甘膦除草剂\n" +
            "栅栏\n" +
            "泥土\n" +
            "园林喷头\n" +
            "赤玉土\n" +
            "浇水壶 园艺 家用\n" +
            "网\n" +
            "充电喷雾器\n" +
            "家用水管软管\n" +
            "电喷雾器农用\n" +
            "家用浇花水管软管\n" +
            "消防水带\n" +
            "压力喷壶\n" +
            "铲子 园艺\n" +
            "浇水喷头\n" +
            "草坪修剪机\n" +
            "养鸡围网\n" +
            "背式割草机\n" +
            "套装浇花水管\n" +
            "竹围栏\n" +
            "剪草机\n" +
            "喷灌喷头\n" +
            "花盘托盘\n" +
            "果园除草剂\n" +
            "油锯 伐木锯\n" +
            "浇花喷头\n" +
            "降温喷头\n" +
            "黑色遮阳网\n" +
            "割草机除草\n" +
            "防虫网\n" +
            "五彩石\n" +
            "水管软管 家用\n" +
            "彩绘鹅卵石\n" +
            "割草机除草机\n" +
            "小型割草机\n" +
            "挖树机\n" +
            "多肉颗粒土\n" +
            "充电农用喷雾器\n" +
            "竹篱笆 栅栏 围栏\n" +
            "电动喷雾器农用\n" +
            "多肉花盆托盘\n" +
            "灭草剂除杂草\n" +
            "锂电池喷雾器\n" +
            "果树打药机\n" +
            "剪草机 电动 家用小型\n" +
            "营养钵\n" +
            "喷壶 喷雾瓶\n" +
            "防鸟网\n" +
            "防腐木围栏\n" +
            "麻袋\n" +
            "除尘雾化喷头\n" +
            "葡萄防鸟网\n" +
            "农用电喷雾器\n" +
            "小锄头\n" +
            "大棚\n" +
            "割草机 充电式\n" +
            "耙子\n" +
            "绿篱剪\n" +
            "多功能除草机\n" +
            "铲子\n" +
            "水培植物营养液\n" +
            "铁锹\n" +
            "气压式喷壶\n" +
            "毛竹\n" +
            "铺路\n" +
            "红蜘蛛\n" +
            "小型家用割草机\n" +
            "花园栅栏\n" +
            "天然鹅卵石\n" +
            "电动绿篱机\n" +
            "养殖大棚\n" +
            "白石子白色小石头\n" +
            "本田王割草机\n" +
            "剪刀 修枝剪\n" +
            "菜青虫\n" +
            "白石子\n" +
            "花盆托盘 万向轮\n" +
            "温室大棚\n" +
            "围栏\n" +
            "园艺铲子\n" +
            "木栅栏 围栏\n" +
            "发财树营养液\n" +
            "电喷雾器农用 多功能 电动 充电\n" +
            "篱笆栅栏\n" +
            "茶叶修剪机\n" +
            "喷雾机 农用\n" +
            "汽油喷雾器\n" +
            "大棚配件\n" +
            "蔬菜大棚骨架\n" +
            "竹子装修\n" +
            "南京雨花石\n" +
            "白粉病\n" +
            "塑钢线 大棚线 拉线\n" +
            "背负式锄草机\n" +
            "小型多功能割草机\n" +
            "育苗袋\n" +
            "汽油锯\n" +
            "降温喷雾\n" +
            "本田割草机\n" +
            "喷雾瓶\n" +
            "多肉土 颗粒土\n" +
            "纸上种菜\n" +
            "收割机\n" +
            "草坪除草剂\n" +
            "阿维菌素\n" +
            "打药机 高压\n" +
            "四冲程割草机\n" +
            "割草机小型多功能农用\n" +
            "松土机\n" +
            "电动草坪机\n" +
            "紫竹\n" +
            "割草机 小型 多功能\n" +
            "育苗\n" +
            "汽油割草机\n" +
            "除草机 松土机\n" +
            "背负式割草机四冲程\n" +
            "水车\n";

}
