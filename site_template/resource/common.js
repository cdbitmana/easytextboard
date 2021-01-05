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

/* chart.js api 시작 */
{{articleHitChart}}
/*
var articleHit = document.getElementById('articleHitChart').getContext('2d');
var chart = new Chart(articleHit, {
    // The type of chart we want to create
    type: 'doughnut',

    // The data for our dataset
    data: {
        labels: ['a','b','c','d','e','f','g'],
        datasets: [{            
            data: [1,2,3,4,5,6,7],
            backgroundColor:['red','orange','yellow']
        }]
    },
    // Configuration options go here
    options: {}
});
*/
/* chart.js api 끝 */

$(window).on('scroll',function(){
if($(window).scrollTop() >= 25){
    $(".scrolltop-button").fadeIn(400);
}else{
    $(".scrolltop-button").fadeOut(400);
}
});

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


$(".profile_arrow > i").hover(function(){
  $(".profile_detail").css("width", "900px");
  $(".profile_detail").css("left", "290px");
  $(".extend").css("visibility","hidden");
});

$(".content-1__wrapper").mouseleave(function(){
    $(".profile_detail").css("width", "0");
    $(".profile_detail").css("left", "0");
    $(".extend").css("visibility","visible");
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





