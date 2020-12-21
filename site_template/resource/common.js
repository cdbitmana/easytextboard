


$(window).on('scroll',function(){
if($(window).scrollTop() >= 25){
    $(".scrolltop-button").fadeIn(400);
}else{
    $(".scrolltop-button").fadeOut(400);
}
});


$(".menu-button").click(function () {   
        $(".side-bar").css("transform", "translateX(-100%)");
       $(".scrolltop-button").css("transform", "translateX(-303px)");
});

$(".side-bar__menu-button").click(function () {
    $("div.side-bar").css("transform", "translateX(0%)");
    $(".scrolltop-button").css("transform", "translateX(0%)");
});



var side_box_height = $(".side-bar__menu-box1>ul>li>div.side-bar__menu-box2").css("height");

$(".side-bar__menu-box1 > ul > li:nth-child(2)").hover(function () {
    $(".side-bar__menu-box1 > ul > li:nth-child(2) ~ li").css("transform", "translateY(" + side_box_height + ")");
});

$(".side-bar__menu-box1 > ul > li:nth-child(2)").mouseleave(function () {
    $(".side-bar__menu-box1 > ul > li:nth-child(2) ~ li").css("transform", "translateY(0%)");
});

function home_main__Body__init() {

    var body = document.querySelectorAll('.home-main__article-box__body');
    for (var i = 0; i < body.length; i++) {
        var initialValue = body[i].innerHTML.trim();
        initialValue = initialValue.replace(/&gt;/gi, ">");
        initialValue = initialValue.replace(/&lt;/gi, "<");
        var viewer = new toastui.Editor.factory({

            el: body[i],
            initialValue: initialValue,
            viewer: true,
            plugins: [toastui.Editor.plugin.codeSyntaxHighlight]

        });
    }
}
home_main__Body__init();



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


function ArticleDetail__Body__init() {

    var body = document.querySelector('.article_body');
    if(body == null){
        return;
    }
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
