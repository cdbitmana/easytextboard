var isSideMenuOpened = false;


$(".menu-button").click(function () {
   if(isSideMenuOpened==false){
    $(".side-bar").css("transform", "translateX(0%)");
    isSideMenuOpened = true;
   }else {
    $(".side-bar").css("transform", "translateX(-100%)");
    isSideMenuOpened = false;
   }
        
      
});

$("div.close-button").click(function () {
    $("div.side-bar").css("transform", "translateX(-100%)");
    isSideMenuOpened = false;
});



var side_box_height = $("div.side-bar>div>ul>li>div.side-box").css("height");

$("div.side-bar > div > ul > li:nth-child(2)").hover(function () {
    $("div.side-bar > div > ul > li:nth-child(2):hover ~ li").css("transform", "translateY(" + side_box_height + ")");
});

$("div.side-bar > div > ul > li:nth-child(2)").mouseleave(function () {
    $("div.side-bar > div > ul > li:nth-child(2) ~ li").css("transform", "translateY(0%)");
});

function ArticleDetail__Body__init() {

    var body = document.querySelector('.article_body');
    var initialValue = body.innerHTML.trim();
    initialValue = initialValue.replace(/&gt;/gi, ">");
    initialValue = initialValue.replace(/&lt;/gi, "<");

    var viewer = new toastui.Editor.factory({

        el: body,
        initialValue: initialValue,
        viewer: true,
        plugins: [toastui.Editor.plugin.codeSyntaxHighlight]

    });
}
ArticleDetail__Body__init();
