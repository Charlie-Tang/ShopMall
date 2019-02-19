//使用es5严格模式,可以在封装之前就找到错误
"use strict";

//封装一个代替getElementById()的方法
function byId(id) {
    //判断id的类型，必须为字符串
    return typeof(id) === "string"?document.getElementById(id):id;
}

//当前图片索引
var index = 0;
//定时器
var timer = null;
//获取banner下的所有div
var pics = byId("banner").getElementsByTagName("div");
//判断banner下所有div数目
var len = pics.length;
//获得dots下所有span小圆点
var dots = byId("dots").getElementsByTagName("span");
//获取到上一张、下一张按钮的实例
var prev = byId("prev");
var next = byId("next");
//主菜单及其子项
var menu = byId("menu-content");
var menuItems = menu.getElementsByClassName("menu-item");
//子菜单及其子项
var subMenu = byId("sub-menu");
var subMenuItems = subMenu.getElementsByClassName("inner-box");

function slideImg() {
    var main = byId("main");
    //划过清除定时器，离开继续
    main.onmouseover = function () {
        //划过清除定时器
        if (timer){
            clearInterval(timer);
        }
    };
    //鼠标离开触发事件
    main.onmouseout = function () {
        //每隔2秒执行一次换图 setInterval()方法
        timer = setInterval(function () {
            index++;
            if (index > len-1) {
                index = 0;
            }
            //切换图片
            changeImg();
        },3000);
    };
    //进入网页自动触发轮播
    main.onmouseout();
    //为小圆点绑定点击事件
    setCirclePointEvent();
    //上一张、下一张按钮绑定事件
    setNextAndPrevButtonEvent();
}

//切换图片，改变小圆点特效
function changeImg() {
    //遍历banner下所有div及dots下所有span，将div隐藏，将span清除类
    for (var i = 0; i < len; i++) {
        pics[i].style.display = 'none';
        dots[i].className = "";
    }
    //根据index索引找到当前div，将其显示出来
    pics[index].style.display = 'block';
    dots[index].className = "active";
}

//小圆点绑定点击切换图片事件
function setCirclePointEvent() {
    //遍历所有圆点实例，绑定点击事件，点击圆点切换图片
    for (var i = 0; i < len; i++) {
        //给所有span设置id，值为i，作为span的索引
        dots[i].id = i;
        dots[i].onclick = function (ev) {
            //改变index为当前span的索引,this指的就是onclick负载的span
            index = this.id;
            //切换图片
            changeImg();
        };
    }
}

//上一张、下一张按钮绑定事件
function setNextAndPrevButtonEvent() {
    next.onclick = function () {
        index++;
        if (index > len-1)
        {
            index = 0;
        }
        changeImg();
    };

    prev.onclick = function () {
        index--;
        if (index < 0)
        {
            index = len-1;
        }
        changeImg();
    };
}

//设置菜单导航
function setMenu() {
    //遍历主菜单，绑定事件
    for (var i = 0; i<menuItems.length; i++)
    {
        //为每一个菜单项添加一个自定义的索引属性,因为for循环内的function用i全是最大值,无法具体索引到某一个节点
        menuItems[i].setAttribute("data-index",i);
        //鼠标滑过触发事件
        menuItems[i].onmouseover = function () {
            //显示子菜单
            subMenu.style.display = 'block';
            //显示子菜单项
            for (var j = 0; j < subMenuItems.length; j++){
                subMenuItems[j].style.display = 'none';
                menuItems[j].style.background = 'none';
            }
            var idx = this.getAttribute("data-index");
            subMenuItems[idx].style.display = 'block';
            this.style.background = 'rgba(0,0,0,0.1)';
        };

        //鼠标离开整个主菜单触发事件
        menu.onmouseout = function () {
            //隐藏子菜单
            subMenu.style.display = 'none';
        };
        //保证鼠标移动到子菜单上，离开主菜单时，子菜单不会消失
        //将要用的模块化
        subMenu.onmouseover = function () {
            this.style.display = 'block';
        };
        //鼠标离开子菜单时，子菜单消失
        subMenu.onmouseout = function () {
            this.style.display = 'none';
        };
    }
}

//轮播图片总方法
slideImg();

//导航菜单总方法
setMenu();