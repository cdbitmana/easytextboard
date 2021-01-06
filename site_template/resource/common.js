/* 홈 화면 게시물 내용 토스트UI 적용 시작 */

const homePageToastUI = function home_main__Body__init() {
    
    const Homebody = document.querySelectorAll('.home-main__article-box__body');
    for (var i = 0; i < Homebody.length; i++) {
        var homeInitial = Homebody[i].innerHTML.trim();
        homeInitial = homeInitial.replace(/&gt;/gi, ">");
        homeInitial = homeInitial.replace(/&lt;/gi, "<");
        const viewer = new toastui.Editor.factory({

            el: Homebody[i],
            initialValue: homeInitial.trim(),
            viewer: true,
            plugins: [toastui.Editor.plugin.codeSyntaxHighlight]

        });
    }
}
homePageToastUI();

/* 홈 화면 게시물 내용 토스트UI 적용 끝 */

/* 게시물 내용 토스트UI 적용 시작 */
function EditorViewer2__init() {
    const body =document.querySelector('.article_body');
    if(body == null){
        return;
    }
    var initial = body.innerHTML.trim();
    initial = initial.replace(/&gt;/gi, ">");
    initial = initial.replace(/&lt;/gi, "<");
    var viewer = new toastui.Editor.factory({
      el: body,
      initialValue: initial.trim(),
      viewer:true,
      plugins: [toastui.Editor.plugin.codeSyntaxHighlight]
    });
  }
  EditorViewer2__init();
/* 게시물 내용 토스트UI 적용 끝 */


/* 스크롤탑 버튼 시작 */
$(window).on('scroll',function(){
    if($(window).scrollTop() >= 25){
        $(".scrolltop-button").fadeIn(400);
    }else{
        $(".scrolltop-button").fadeOut(400);
    }
    });
    /* 스크롤탑 버튼 끝 */
    
    
    /* 프로필 페이지 탑바 색전환 시작 */
    function changeTopbar(){
        var pageName = ""; 
        var tempPageName = window.location.href;
        var strPageName = tempPageName.split("/");
        pageName = strPageName[strPageName.length-1].split("?")[0];
        if(pageName == "profile.html"){
            $(".top-bar").css("background-color","#F8E88B");
        }
        return pageName;
    }
    changeTopbar();
    /* 프로필 페이지 탑바 색전환 끝 */
    
    /* 프로필 페이지 펼치기 시작 */
    $(".profile_arrow_extend > i").click(function(){               
            $(".profile_detail").css("width", "900px");
            $(".profile_detail").css("left", "290px");
            $(".profile_arrow_extend").css("display","none");
            $(".profile_arrow_shorten").css("display","flex");
         
    });
    
    $(".profile_arrow_shorten > i").click(function(){
        $(".profile_detail").css("width", "0");
        $(".profile_detail").css("left", "0");
        $(".profile_arrow_shorten").css("display","none");
        $(".profile_arrow_extend").css("display","flex");
      });
    /* 프로필 페이지 펼치기 끝 */
    
    
    /* 사이드바 메뉴 효과 시작 */
    $(".menu-button").click(function () {   
            $(".side-bar").css("transform", "translateX(-100%)");
           $(".scrolltop-button").css("transform", "translateX(-303px)");
          
    });
    
    $(".side-bar__menu-button").click(function () {
        $("div.side-bar").css("transform", "translateX(0%)");
        $(".scrolltop-button").css("transform", "translateX(0%)");
    
    });
    /* 사이드바 메뉴 효과 끝 */
    
    
    /* 사이드바 2차메뉴 기능 시작 */
    var side_box_height = $(".side-bar__menu-box1>ul>li>div.side-bar__menu-box2").css("height");
    
    $(".side-bar__menu-box1 > ul > li:nth-child(2)").hover(function () {
        $(".side-bar__menu-box1 > ul > li:nth-child(2) ~ li").css("transform", "translateY(" + side_box_height + ")");
    });
    
    $(".side-bar__menu-box1 > ul > li:nth-child(2)").mouseleave(function () {
        $(".side-bar__menu-box1 > ul > li:nth-child(2) ~ li").css("transform", "translateY(0%)");
    });
    /* 사이드바 2차메뉴 기능 끝 */
    
    
    /* 홈 화면 상단 이미지 슬라이드 시작 */
     const container = document.querySelector('.img-box'),
        slides = document.querySelectorAll('.img-cell'),
        slidecounter = slides.length;
    let currentIndex = 0;
    
    
    var lele = 0;
    var i = 0;
    function moveleft() {
        if(container == null || slides == null){
            return;
        }
    
        if (i < slidecounter - 1) {
            lele += 100;
            i++;
            container.style.transition = '1s'
            setTimeout('moveleft()', 6000);
        } else {
            container.style.transition = '0s'
            lele = 0;
            i = 0;
            setTimeout('moveleft()', 0);
        }
    
        container.style.left = "-" + lele + "%";
        
    }
    
    moveleft();
    /* 홈 화면 상단 이미지 슬라이드 끝 */
    
    
    