/** 注：该JS文件用于存放常用函数，功用相关的函数放在Java文件中注入*/


//##############################################################################################################
//设置账号密码
function tblmShopList(){
    var boxContent = document.getElementsByClassName("box-content");
    localMethod.JI_LOG(boxContent.length);
    if(boxContent.length<1){
        localMethod.pageNextStop();
    }
    var text = "123";
    for(var i=0;i<boxContent.length;i++){
        var as = boxContent[i].getElementsByTagName("a");
//        localMethod.JI_LOG("!!!!!!!"+i);
//        localMethod.JI_LOG(as.length);
        var title = as[0].getAttribute("title");
        text = text +"###"+ title;
    }
    localMethod.JI_LOG(text);
    if(boxContent.length<100){
        localMethod.pageNextStop();
    }
    localMethod.lianmengArray(text);



}

function editTitleAndShangjiaNow(){
    try{
        var titles = document.getElementsByClassName("texbox title-box");
        var titleBeginName = titles[0].getElementsByTagName("input");

        localMethod.JI_LOG(titleBeginName[0].value);
        var mTitle = titleBeginName[0].value;
        titleBeginName[0].value = "";
        titleBeginName[0].focus();
        localMethod.editByOriginalTitle(mTitle+"");
    } catch(e){
        editTitleAndShangjiaNow();
        localMethod.JI_LOG(e.message);
    }
}


function showKeyboardAdfterShangjia(title){

    var titles = document.getElementsByClassName("texbox title-box");
    var titleBeginName = titles[0].getElementsByTagName("input");
    titleBeginName[0].value = title;

    localMethod.showKeyboardAdfterShangjia();

}

function showKeyboard3Way(){

    var titles = document.getElementsByClassName("search-combobox-input");
//    var titleBeginName = titles[0].getElementsByTagName("input");
    titles[0].focus();

    localMethod.showKeyboard3Way();

}

function del5Pic(){
    var dels = document.getElementsByClassName("icon iconfont icon-remove del");
    localMethod.JI_LOG("dels:"+dels.length);
    dels[4].click();
    shangjiaAfterEditTitle();
}

function shangjiaAfterEditTitle(){

    var checkboxs = document.getElementsByClassName("checkbox-wrap");
    var comfirs = document.getElementsByClassName("blue");
    var radiosIndex = 0;
    for(var i=0;i<checkboxs.length;i++){
        if(checkboxs[i].innerText=="立刻上架定时上架放入仓库"){
            localMethod.JI_LOG(checkboxs[i].innerText);
            radiosIndex = i;
            break;
        }
    }
    var radios = checkboxs[radiosIndex].getElementsByTagName("input");
    radios[0].click();
    localMethod.JI_LOG(comfirs.length);
    for(var i=0;i<comfirs.length;i++){
        if(comfirs[i].innerText=="发 布"){
            comfirs[i].click();
            break
        }
    }

    localMethod.checkSuccess();
}


function jsCangkuGoNextPage(){
    localMethod.JI_LOG("jsCangkuGoNextPage");
    var selectors = document.getElementsByClassName("selector");
    var itemCates = document.getElementsByClassName("J_QRCode");
    localMethod.JI_LOG("selectors length:"+selectors.length);
    var cangkuCidIds = "";
    for(var j=0;j<selectors.length;j++){
        var itemids = selectors[j].getAttribute("itemids");
        var icat = itemCates[j].getAttribute("data-param").split("&cid=")[1].split("&title=")[0];
        if(cangkuCidIds==""){
            cangkuCidIds = itemids+"@@@"+icat;
        } else {
            cangkuCidIds = cangkuCidIds + "###" +itemids+"@@@"+icat;
        }
    }
    localMethod.cangkuList(cangkuCidIds+"");
//    localMethod.cangkuForeach();
//    if(selectors.length<20){
//        localMethod.cangkuForeach();
//    }


    var nexts = document.getElementsByClassName("next-page");
    if(nexts.length>0){
        var as = nexts[0].getElementsByTagName("a");
        localMethod.JI_LOG(as[0].innerText)
        as[0].click();
    } else {
        localMethod.cangkuForeach();
    }

}



function js3WayGoSameUrl(array){

    var itemnames = document.getElementsByClassName("info1__itemname");
    var prices = document.getElementsByClassName("info2__price");
    var paids = document.getElementsByClassName("info3__npaid");
    var comments = document.getElementsByClassName("info3__ncomments");
    var mUrl = document.getElementsByClassName("info1__itemname");
    localMethod.JI_LOG("array"+array.length);
    try{
        var pay = paids[10].innerText.replace("人付款","");
    }catch(e){
        localMethod.after3WaySameResult();
        localMethod.JI_LOG("after3WaySameResult:"+132);
    }
//    var text ="\n";
    var maxPrices = 0;
    var minPrices = 100000;
    var minPingduoduoPrices = 100000;
    var minPricesTitle = "";
    var minPingDuoDuoPricesTitle = "";
    var averPrices = 0;
    var averNum = 0;
    var minPricesUrl = "";
    var minPingduoduoPricesUrl = "";
    var mTitleStr = "123";



    if(paids.length>30&&pay>3){

    for(var i=0;i<itemnames.length;i++){
        if(comments[i].innerText!=""){
            if(paids[i].innerText.replace("人付款","")>10&&comments[i].innerText.replace("条评论","")>5){
                var price = prices[i].innerText.replace("￥","");
                averPrices = accAdd(averPrices,price);
                averNum = averNum +1;
                if(parseFloat(price)>parseFloat(maxPrices)){
                    maxPrices = price;
                }
                if(parseFloat(price)<parseFloat(minPrices)&&parseFloat(9.8)<parseFloat(price)){
                    minPrices = price;
                    minPricesTitle = itemnames[i].innerText;
                    minPricesUrl = mUrl[i].getElementsByTagName("a")[0];

                }

                var mTitle = "";
                for(var j=0;j<array.length;j++){
                    mTitle = itemnames[i].innerText.replace(array[j],"");
                }
                localMethod.JI_LOG("mTitle:"+i+mTitle);
                mTitleStr = mTitleStr + "###" + mTitle;

    //                localMethod.titleSave(itemnames[i].innerText);
    //                text = text + itemnames[i].innerText +"#####"+paids[i].innerText + "\n";
    //                localMethod.sameResultForSort(itemnames[i].innerText,paids[i].innerText.replace("人付款",""));

            }
        }
    }
    if(maxPrices>accMul(2,minPrices)){

        for(var i=0;i<itemnames.length;i++){
            if(comments[i].innerText!=""){
                if(paids[i].innerText.replace("人付款","")>10&&comments[i].innerText.replace("条评论","")>2){

                    if(parseFloat(price)<parseFloat(minPingduoduoPrices)&&accMul(1.3,minPrices)>minPrices){
    //
                        minPingduoduoPrices = price;
                        minPingDuoDuoPricesTitle = itemnames[i].innerText;
                        minPingduoduoPricesUrl = mUrl[i].getElementsByTagName("a")[0];
                    }
                }
            }
        }

            if(mTitleStr!="123"){
                localMethod.titleArrayList(mTitleStr,minPricesTitle);
            }
            localMethod.JI_LOG("minSameRecord b4");
            var minSameRecord = "maxPrices:"+maxPrices+",averPrices:"+accDiv(averPrices,averNum)+",minPrices:"+minPrices+",minPingduoduoPrices:"+minPingduoduoPrices
            +"\n"+"minPricesUrl:"+minPricesUrl;
            localMethod.JI_LOG("minSameRecord after");
            localMethod.TBLM_LOG(minSameRecord);
            localMethod.JI_LOG("getMinPricesUrl b4");
            localMethod.getMinPricesUrl(minPricesUrl+"",minPricesTitle+"");
            localMethod.JI_LOG("getMinPricesUrl after");
            localMethod.getPingDuoDuoMinPricesUrl(minPingduoduoPricesUrl+"",minPingDuoDuoPricesTitle+"",minPricesUrl+"");

        }




    }
        localMethod.after3WaySameResult();
    localMethod.JI_LOG("after3WaySameResult:"+188);




}


function jsGoSameUrl(array){

    var itemnames = document.getElementsByClassName("info1__itemname");
    var prices = document.getElementsByClassName("info2__price");
    var paids = document.getElementsByClassName("info3__npaid");
    var comments = document.getElementsByClassName("info3__ncomments");
    var mUrl = document.getElementsByClassName("info1__itemname");
    localMethod.JI_LOG("array"+array.length);
    try{
    var pay = paids[5].innerText.replace("人付款","");
    }catch(e){
        localMethod.afterSameResult();
    }
//    var text ="\n";
    var maxPrices = 0;
    var minPrices = 100000;
    var minPingduoduoPrices = 100000;
    var minPricesTitle = "";
    var averPrices = 0;
    var averNum = 0;
    var minPricesUrl = "";
    var minPingduoduoPricesUrl = "";
    var mTitleStr = "123";



    if(paids.length>20&&pay>3){

    for(var i=0;i<itemnames.length;i++){
        if(comments[i].innerText!=""){
            if(paids[i].innerText.replace("人付款","")>10&&comments[i].innerText.replace("条评论","")>2){
                var price = prices[i].innerText.replace("￥","");
                averPrices = accAdd(averPrices,price);
                averNum = averNum +1;
                if(parseFloat(price)>parseFloat(maxPrices)){
                    maxPrices = price;
                }
                if(parseFloat(price)<parseFloat(minPrices)){
                    minPrices = price;
                    minPricesTitle = itemnames[i].innerText;
                    minPricesUrl = mUrl[i].getElementsByTagName("a")[0];

                }

                var mTitle = "";
                for(var j=0;j<array.length;j++){
                    mTitle = itemnames[i].innerText.replace(array[j],"");
                }
                localMethod.JI_LOG("mTitle:"+i+mTitle);
                mTitleStr = mTitleStr + "###" + mTitle;

    //                localMethod.titleSave(itemnames[i].innerText);
    //                text = text + itemnames[i].innerText +"#####"+paids[i].innerText + "\n";
    //                localMethod.sameResultForSort(itemnames[i].innerText,paids[i].innerText.replace("人付款",""));

            }
        }
    }
    if(mTitleStr!="123"){
        localMethod.titleArrayList(mTitleStr,minPricesTitle);
    }

    localMethod.getMinPricesUrl(minPricesUrl+"",minPricesTitle+"");


    for(var i=0;i<itemnames.length;i++){
        if(comments[i].innerText!=""){
            if(paids[i].innerText.replace("人付款","")>10&&comments[i].innerText.replace("条评论","")>2){

                if(parseFloat(price)<parseFloat(minPingduoduoPrices)&&accMul(1.3,minPrices)>minPrices){
//
                    minPingduoduoPrices = price;
                    minPingduoduoPricesUrl = mUrl[i].getElementsByTagName("a")[0];
                }
            }
        }
    }

    var minSameRecord = "maxPrices:"+maxPrices+",averPrices:"+accDiv(averPrices,averNum)+",minPrices:"+minPrices
    +"\n"+"minPricesUrl:"+minPricesUrl+",minPingduoduoPrices:"+minPingduoduoPrices;
    localMethod.TBLM_LOG(minSameRecord);


    localMethod.getPingDuoDuoMinPricesUrl(minPingduoduoPricesUrl+"");

    }
    localMethod.afterSameResult();





}
//search-popupmenu-content search-menu-content
//search-menuitem ks-component-child563
//item-wrapper
//item-text

function findKeyShopName(){
    var items = document.getElementsByClassName("item-wrapper");
    localMethod.JI_LOG("items:"+items.length);
    var itemArray = new Array();
    for(var i=0;i<items.length;i++){
        var keyShopName = items[i].getAttribute("data-key").replace("q=","").split("&suggest=")[0];
        localMethod.JI_LOG("keyShopName:"+keyShopName);
        itemArray[i] = keyShopName;
    }
    localMethod.afterShopName(itemArray);


}


function find3WaySameStyle(){
    try{
        var rowTitle = document.getElementsByClassName("row row-2 title");
        var nids = document.getElementsByClassName("J_ClickStat");
        var titles=new Array();
        var links=new Array();

        for(var i=0;i<nids.length;i++){
            var nid = nids[i].getAttribute("trace-pid");
             if(nid!=""){
                var position =links.length;
                var mlink =  "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid="+nid+"&sort=sale-desc";
                links[position] =mlink;
             }
        }

//        for(var i=0;i<rowTitle.length;i++){
//            var titls = rowTitle[i].getElementsByClassName("H");
//            for(var j=0;j<titls.length;j++){
//                var position =links.length;
//                titles[position] = titls[j].innerText;
//                localMethod.JI_LOG(titles[position]);
//            }
//         }
        localMethod.JI_LOG("find3WaySameStyle~~~~~~~~");

//        localMethod.get3WaySplitTitle(titles);
        localMethod.go3WaySameUrl(links);
        localMethod.nextWay3Way();


    }catch(err){
        localMethod.JI_LOG("error:"+err.message);
    }

}


function findSameStyle(){
    try{
        var rowTitle = document.getElementsByClassName("row row-2 title");
        var nids = document.getElementsByClassName("J_ClickStat");
        var titls = rowTitle[0].getElementsByClassName("H");
        var titles=new Array();
        for(var i=0;i<titls.length;i++){
            titles[i] = titls[i].innerText;
        }
        localMethod.getSplitTitle(titles);

        var as = document.getElementsByTagName("a");
        var text = "";
        var tongkuan = false;
        var notBreak = true;
        var nid = nids[0].getAttribute("trace-pid");
        var link = "https://s.taobao.com/search?type=samestyle&app=i2i&rec_type=1&uniqpid="+nid+"&sort=sale-desc";
        localMethod.goSameUrl(link);

//                            localMethod.JI_LOG("11111111");
//
//        for(var i=0;i<as.length;i++){
//            if(tongkuan){
//                if(as[i].innerText=="找同款"&&as[i]!=""){
//                                            localMethod.JI_LOG("33333333");
//
//                    var link = as[i]+"&sort=sale-desc";
//                    localMethod.JI_LOG(link);
//
//                    localMethod.goSameUrl(link);
//                    notBreak = false;
//                    break;
//                }
//            } else if(name==as[i].innerText){
//                                        localMethod.JI_LOG("22222222");
//
//                tongkuan = true;
//            }
//        }
//        if(notBreak){
//            localMethod.noSame();
//        }

    }catch(err){
        localMethod.JI_LOG("error:"+err.message);
        localMethod.noSame();
    }

//                    localMethod.noSame();
}



//标题组合
function titleCombination(){

    var as = document.getElementsByTagName("a");
    findForClick(as,"关联修饰词");
    for(var j=0;j<5;j++){
        setTimeout(function(){
        getTableTitleData();
            var as = document.getElementsByTagName("a");
            findForClick(as,"下一页 >");
        },500*(j+1));
    }

    setTimeout(function(){
        localMethod.getTitleResult();
    },3000);
//    localMethod.getTitleResult();

}

//标题组合
function relativeTitle(){

    var as = document.getElementsByTagName("a");
    findForClick(as,"关联热词");
    for(var j=0;j<5;j++){
        setTimeout(function(){
        getTableTitleData();
            var as = document.getElementsByTagName("a");
            findForClick(as,"下一页 >");
        },500*(j+1));
    }

       setTimeout(function(){
            titleCombination();
        },3000);

//    localMethod.getTitleResult();

}

//跳到市场
function goSearchClick(){

    var as = document.getElementsByTagName("a");
//    for (var i = 0; i < as.length; i++) {
//        localMethod.JI_LOG(as[i].innerHTML+"~~~~"+i);
//    }
    as[18].click();

    setTimeout(function(){
                var as2 = document.getElementsByTagName("a");
                as2[27].click();
                localMethod.JI_LOG("as2~~~~click");
    },3000);

}


function check(){
    var as = document.getElementsByTagName("a");
    var checkboxs = document.getElementsByClassName("checkbox undefined undefined");
    var options = document.getElementsByClassName("option");
        localMethod.JI_LOG(as.length);
        localMethod.JI_LOG(checkboxs.length);
        localMethod.JI_LOG(options.length);
    var text = "--------as--------"+ "\n";
    for(var i=0;i<as.length;i++){
        text = text + i + "\n"+",innerText:"+as[i].innerText+
//        ",innerHTML:"+as[i].innerHTML+
        ",value:"+as[i].value + ",as:"+as[i]+"\n";
    }
        localMethod.JI_LOG(text);
    text = "--------checkboxs--------"+ "\n";
    for(var i=0;i<checkboxs.length;i++){
        text = text + i+ "\n" +"checkboxs:"+checkboxs[i]+",innerText:"+checkboxs[i].innerText+
//        ",innerHTML:"+checkboxs[i].innerHTML+
        ",value:"+checkboxs[i].value+"\n";
    }
            localMethod.JI_LOG(text);
    text = "--------options--------"+ "\n";
    for(var i=0;i<options.length;i++){
        text = text + i+ "\n" + "options:"+options[i]+",innerText:"+options[i].innerText+",innerHTML:"+options[i].innerHTML+",value:"+options[i].value+"\n";
    }
    localMethod.JI_LOG(text);
}


//减法函数
function accSub(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     return ((arg2*m-arg1*m)/m).toFixed(n);
}

/**
 ** 加法函数，用来得到精确的加法结果
 ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
 ** 调用：accAdd(arg1,arg2)
 ** 返回值：arg1加上arg2的精确结果
 **/
function accAdd(arg1, arg2) {
    var r1, r2, m, c;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}

//除法
 function accDiv(arg1,arg2){
 try{
    var t1=0,t2=0,r1,r2;
    try{
        t1=arg1.toString().split(".")[1].length
        }catch(e){}
    try{
        t2=arg2.toString().split(".")[1].length
        }catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
 }catch(e){
    return 0;
 }
}



function goSearchWord(){
    var as = document.getElementsByTagName("a");
    as[26].click();
}


function setSearchWord(shopword){

    var as = document.getElementsByTagName("a");
    var searchWord = document.getElementsByClassName("main-search-input");
    searchWord[0].focus();
    localMethod.showKeyboard();
    searchWord[0].value = shopword;
    setTimeout(function(){
            findForClick(as,"搜索");
            var as2 = document.getElementsByTagName("a");
            findForClick(as2,"相关搜索词");
            setTimeout(function(){localMethod.getTargetIndex();},2000);

    },2000);
}


function next3WayPage(){
    var as = document.getElementsByTagName("a");
    for(var i=0;i<as.length;i++){
        if(as[i].innerText=="下一页 "){
           as[i].click();
               localMethod.JI_LOG("as!!:"+as.length);
               localMethod.next3WayPage();
        }
    }
}

//根据搜索词点击
function findForClick(as,word){
    for(var i=0;i<as.length;i++){
        if(as[i].innerText==word){
           as[i].click();
        }
    }
}

//竞争力、热词获取排序
function goGetChecked(){

    for(var j=0;j<5;j++){
        setTimeout(function(){
            getTableData();
            var as = document.getElementsByTagName("a");
            findForClick(as,"下一页 >");
        },500*(j+1));
    }

    setTimeout(function(){
        localMethod.getHotShopResult();
    },3000);
}


function getTableTitleData(){
    var table = document.getElementsByClassName("table-ng table-ng-basic related-word-table")[0];
    for(var i=0;i<table.rows.length;i++){
        var child = table.getElementsByTagName("tr")[i];
        var text = child.children[0].innerText;
        var text1 = child.children[1].innerText;
        if(i>0){
            text1 = text1.replace("-","0");

            text1 = text1.replace(",","").replace(",","");
//            localMethod.JI_LOG(text+"~~~~~~~~~~");
//            localMethod.JI_LOG(text1+"!!!!!!!!");
            if(text1>700){
                localMethod.titleResult(text,text1);
            }


        }
    }
}

function getTableData(){
    var table = document.getElementsByClassName("table-ng table-ng-basic related-word-table")[0];
    for(var i=0;i<table.rows.length;i++){
        var child = table.getElementsByTagName("tr")[i];
        var text = child.children[0].innerText;
        var text1 = child.children[1].innerText;
        var text2 = child.children[2].innerText;
        var text3 = child.children[3].innerText;
        var text4 = child.children[4].innerText;
        if(i>0){
            var djl = text2.replace("%","");
            djl= djl/100;
            var zhl = text4.replace("%","");
            zhl= zhl/100;
            text1 = text1.replace("-","0");
            text2 = text2.replace("-","0");
            text3 = text3.replace("-","0");
            text4 = text4.replace("-","0");

            text1 = text1.replace(",","").replace(",","");
            text3 = text3.replace(",","").replace(",","");
//            localMethod.JI_LOG(text1+"!!!!!!!!");
//            localMethod.JI_LOG(text3+"!!!!!!!!");
            if(text3!=("0")&&text1>2000){
                var jzl = accDiv(accMul(accMul(text1,djl),zhl),text3);
                var rc = accDiv(text1,text3);
//                localMethod.JI_LOG(jzl+"~~~~~");
//                localMethod.JI_LOG(rc+"~~~~~");
                localMethod.shopResult(text,jzl,rc);
            }


        }
    }
}

//乘法
function accMul(arg1,arg2){
 try{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{
        m+=s1.split(".")[1].length
        }catch(e){
        }
    try{
        m+=s2.split(".")[1].length
        }catch(e){
        }
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
 }catch(e){
    return 0;
 }
}

//除法
 function accDiv(arg1,arg2){
 try{
    var t1=0,t2=0,r1,r2;
    try{
        t1=arg1.toString().split(".")[1].length
        }catch(e){}
    try{
        t2=arg2.toString().split(".")[1].length
        }catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
 }catch(e){
    return 0;
 }
}

//指标选择
function operaSearch(){

    var checkboxs = document.getElementsByClassName("checkbox undefined undefined");
    var optionsClick = document.getElementsByClassName("option");

    for(var i=0;i<checkboxs.length;i++){
        if(checkboxs[i].innerText=="搜索人数占比"||checkboxs[i].innerText=="搜索热度"||checkboxs[i].innerText=="商城点击占比"
        ||checkboxs[i].innerText=="直通车参考价"||checkboxs[i].innerText=="支付转化率"){
           optionsClick[i].click();
        }
    }
    goGetChecked();
}

function foreachThings(options,i){

        setTimeout(function(){
//           options[i].click();
           localMethod.JI_LOG(options[i].innerHTML+"check_option~~~~"+i);

        },500*(i+1));

}


/** No.1 模拟点击事件############################################################################################*/
//模拟点击事件
function doClickByRI(resId,time) {
 var btn = document.getElementById(resId);
 if(null!=btn){
    setTimeout(function(){
        btn.click();
    },time*1000);
    }
}

function doClickByTag(){
  var itemli = document.getElementsByTagName("li");
  localMethod.JI_showToast("length："+itemli.length);

}
function doComfir(){

    setTimeout(function(){

  var btn = document.getElementsByClassName("layui-layer-btn0");
    localMethod.JI_showToast("btn:"+btn.length);
    btn[0].click();
        },3000);

}


function selectNumRange(position,amount){
  var itema = document.getElementById('framePage').contentWindow.document.getElementsByTagName('input');
  var commitBtn = document.getElementById('framePage').contentWindow.document.getElementById('openBetWinBtn2');


  localMethod.JI_showToast("itema:"+itema.length);
    itema[position].click();
    itema[position].value = amount;
    setTimeout(function(){
        commitBtn.click()
    },2000);

//  localMethod.JI_LOG(btn.className);
    setTimeout(function(){
        btn.click()
    },3000);

}


function doClickByCN(className,time) {
  var itemli = document.getElementsByTagName("li");
  localMethod.JI_showToast("length："+itemli.length);

  var btn = document.getElementsByClassName(className)[0];
  if(null!=btn){
    setTimeout(function(){
        btn.click();
    },time*1000);
    }
}

//模拟触摸事件
function doTapByRI(resId,index) {
   if(null==index){index=0;}
   $("#"+resId).eq(index).trigger("tap");
}

function doTapByCN(className,index) {
  if(null==index){index=0;}
  $("."+className).eq(index).trigger("tap")
}

//根据父控件查找子控件再触摸
function doTapByParentCN(parentCN,className,index) {
  if(null==index){index=0;}
  $("."+parentCN).children("."+className) .eq(index).trigger("tap");
}

function doTapForScanGoods(parentCN,index) {
   if(null==index){index=0;}
   $("."+parentCN).eq(index).children(".p").children("a").trigger("tap");
}


/** No.2 输入文本信息至输入框中############################################################################################*/
function doInputByRI(resId,context,time) {
   var text = document.getElementById(resId);
    setTimeout(function(){
        text.value = context;
    },time*1000);
}

function doInputByCN(className,context,time) {
    var text = document.getElementsByClassName(className)[0];
    setTimeout(function(){
        text.value = context;
    },time*1000);
}


/** No.3 获取控件的文本信息###########################################################################################*/
function doGetTextByRI(resId) {
    var text = document.getElementById(resId);
    return text.value;
}

function doGetTextByCN(className) {
    var text = document.getElementsByClassName(className)[0];
    return text.value;
}

function doGetTextByCNByInner(className) {
    var text = document.getElementsByClassName(className)[0];
    return text.innerHTML;
}



