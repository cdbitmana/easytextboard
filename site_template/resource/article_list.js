

/* 스크롤탑 버튼 시작 */
$(window).on('scroll',function(){
if($(window).scrollTop() >= 25){
    $(".scrolltop-button").fadeIn(400);
}else{
    $(".scrolltop-button").fadeOut(400);
}
});
/* 스크롤탑 버튼 끝 */

/* 게시물 검색 기능 시작 */
const articleList = [];
let listName = ''
let getPageListName = function(){
    var pageName = ""; 
    var tempPageName = window.location.href;
    var strPageName = tempPageName.split("/");
    pageName = strPageName[strPageName.length-1].split("?")[0];    
    
    pageName = pageName.split("-")[0];    
    listName = pageName;
    return pageName;
}
getPageListName();
console.log(listName);
let boardCode = listName +'-list.json';

$.get(    
	boardCode,
	{},
	function(data) {
		data.forEach((row, index) => {
			

			const article = {
				id: row.id,
				regDate: row.regDate,
				writer: row.extra__writer,
				title: row.title,
				body: row.body
			};
            
            articleList.push(article);
            
		});
	},
	'json'
);

const articleListBoxVue = new Vue({
	el: ".article-list-wrap",
	data: {
		articleList: articleList,
        searchKeyword: '',
        searchResult:''
	},
	methods: {
		searchKeywordInputed: _.debounce(function(e) {
			this.searchKeyword = e.target.value;
        }, 500),
        searchKeywordClick:function(){
            this.searchResult = this.searchKeyword;
        }
	},
	computed: {
		filterKey: function() {
			return this.searchResult.toLowerCase();
		},
		filtered: function() {
			if (this.filterKey.length == 0) {
				return this.articleList;
			}

			return this.articleList.filter((row) => {
				const keys = ['title', 'writer', 'body', 'regDate'];

				const match = keys.some((key) => {
					if (row[key].toLowerCase().indexOf(this.filterKey) > -1) {
						return true;
					}
				});
                
				return match;
			});
        }
        
	}
});

/* 게시물 검색 기능 끝 */




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

