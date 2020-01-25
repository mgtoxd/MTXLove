function mclick(){

    justTest.callPhone('15304769009')
}

function mdate() {
    var da = justTest.getDate();
    // da = 1;

//    justTest.callPhone('15304769009')

    var i = 0;
    var mtxs = ['','','',''];
    var wyqs = ['','','',''];
    var mtxw = ['','','',''];
    var wyqw = ['','','',''];
    switch (da) {
        case 1:
            mtxs=['企业资源计划-张哲','','',''];
            mtxw=['新C401','','',''];
            wyqs=['','','就业指导与发展-谢巨升','证券投资学'];
            wyqw=['','','学院A207','学院A110'];
            break;
        case 2:
            mtxs=['企业资源计划-张哲','就业指导与发展-杜宏','信息系统开发实训-高博',''];
            mtxw=['新C401','学院A207','新C305',''];
            wyqs=['','形势与政策-张铁梅','',''];
            wyqw=['','新C101','',''];
            break;
        case 3:
            mtxs=['形势与政策-郝玉梅','','XML技术与应用-赵俊岚',''];
            mtxw=['新C401','','新C304',''];
            wyqs=['经济法-王春艳','保险会计学-勿恩琪琪格','保险营销学-吴晓成',''];
            wyqw=['学院B306','学院A202','学院A201',''];
            break;
        case 4:
            mtxs=['','','',''];
            mtxw=['','','',''];
            wyqs=['','社会保险-哈斯琪琪格','',''];
            wyqw=['','学院A109','',''];
            break;
        case 5:
            mtxs=['信息系统开发实训-高博','','',''];
            mtxw=['新C305','','',''];
            wyqs=['保险精算学-包双宝','','','金融风险管理'];
            wyqw=['学院A203','','','学院A203'];
            break;
        case 8:
            mtxs=['企业资源计划-张哲','','',''];
            mtxw=['新C401','','',''];
            wyqs=['','','就业指导与发展-谢巨升','证券投资学'];
            wyqw=['','','学院A207','学院A110'];
            break;
        case 9:
            mtxs=['','就业指导与发展-杜宏','',''];
            mtxw=['','学院A207','',''];
            wyqs=['保险精算学-包双宝','形势与政策-张铁梅','',''];
            wyqw=['学院A204','新C101','',''];
            break;
        case 10:
            mtxs=['形势与政策-郝玉梅','','XML技术与应用-赵俊岚',''];
            mtxw=['新B307','','新C304',''];
            wyqs=['经济法-王春艳','保险会计学-勿恩琪琪格','保险营销学-吴晓成',''];
            wyqw=['学院B306','学院A202','学院A201',''];
            break;
        case 11:
            mtxs=['XML技术与应用-赵俊岚','','',''];
            mtxw=['新C304','','',''];
            wyqs=['','社会保险-哈斯琪琪格','',''];
            wyqw=['','学院A109','',''];
            break;
        case 12:
            mtxs=['信息系统开发实训-高博','','',''];
            mtxw=['新C305','','',''];
            wyqs=['保险精算学-包双宝','','','金融风险管理'];
            wyqw=['学院A203','','','学院A203'];
            break;
        
    }
    $("#wyq .price").each(function () {
        this.text = wyqw[i];
        i+=1;
    });
    i = 0;
    $("#mtx .price").each(function () {
        this.text = mtxw[i];
        i+=1;
    });
    i = 0;
    $("#wyq h3 span").each(function () {
        this.innerHTML = wyqs[i];
        i+=1;
    });
    i = 0;
    $("#mtx h3 span").each(function () {
        this.innerHTML = mtxs[i];
        i+=1;
    });


}

function mlocation(){
    var i = justTest.getLocation();
    // window.location.replace(i);
}