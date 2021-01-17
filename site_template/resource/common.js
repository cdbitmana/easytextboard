/* 사이트 주소 쿼리스트링 가져오기 시작 */
function getUrlParams() {
    var params = {};
    window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
    return params;
}

/* 사이트 주소 쿼리스트링 가져오기 끝 */

/* 페이지 탑바 색전환 시작 */

function changeTopbar(){
    var pageName = ""; 
    var tempPageName = window.location.href;
    var strPageName = tempPageName.split("/");
    pageName = strPageName[strPageName.length-1].split("?")[0];
    
    if(pageName.indexOf("profile.html") != -1){
        $(".logo_box").css("color","black");
        $(".menu_bar").css("color","black");
    }
    if(pageName.indexOf("list.html") != -1){
        $(".logo_box").css("color","black");
        $(".menu_bar").css("color","black");
    }
    if(pageName.indexOf("statindex.html") != -1){
        $(".logo_box").css("color","black");
        $(".menu_bar").css("color","black");
    }
    if(pageName.indexOf("article-detail-") != -1){
        $(".logo_box").css("color","black");
        $(".menu_bar").css("color","black");
    }
    if(pageName.indexOf("article_tag.html") != -1){
        $(".logo_box").css("color","black");
        $(".menu_bar").css("color","black");
    }
    return pageName;
}
changeTopbar();
/* 페이지 탑바 색전환 끝 */

/* 프로필 페이지 클릭 버튼 시작 */
$(".showdetail > button").click(function(){
    var scrollposition = $("#profile_detail").offset();
    $('html').animate({
        scrollTop: scrollposition.top -100
    },
    10
    );
    $("#profile_detail").animate({
        height:"500px"
    },
    500
    );
    
});

$(".hidedetail > button").click(function(){
    $("html").animate({
        scrollTop: 100
    },
    10
    );
    $("#profile_detail").animate({
        height:"0"
    },
    500
    );
    });
/* 프로필 페이지 클릭 버튼 끝 */

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
    
   
    
    
    /* 게시물 검색 기능 시작 */
const articleList = [];
let listName = ''
let getPageListName = function(){
    let pageName = ""; 
    let tempPageName = window.location.href;
    let strPageName = tempPageName.split("/");
    pageName = strPageName[strPageName.length-1].split("?")[0];    
    
    pageName = pageName.split("-")[0];    
    listName = pageName;
    return pageName;
}
let param = getUrlParams();

let boardCode = param.board +'-list.json';

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
				body: row.body,
				hitCount : row.hitCount,
                likesCount : row.likesCount,
                commentsCount : row.commentsCount
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
        searchResult:'',
        currentPage : 1,
        lastPage : 1
	},
	methods: {
		searchKeywordInputed: _.debounce(function(e) {
			this.searchKeyword = e.target.value;
        },500),
        searchKeywordClick:function(){
            this.searchResult = this.searchKeyword;
            this.currentPage = 1;
        },
        searchKeywordInputedEnter:function(){
            if(event.keyCode==13){
                this.searchResult = this.searchKeyword;
                this.currentPage = 1;
            }
        },
        movePage:function(page){
            this.currentPage = page;           
            
        },
        movePageNext:function(){
            this.currentPage = this.currentPage + 10;
            this.currentPage = Math.ceil(this.currentPage / 10 );
            this.currentPage = (this.currentPage - 1) * 10 + 1;
        },
        movePagePrev:function(){
            this.currentPage = this.currentPage - 10;
            this.currentPage = Math.ceil(this.currentPage / 10 );
            this.currentPage = (this.currentPage - 1) * 10 + 1;
        },
        movePageFirst:function(){
            this.currentPage = 1;
        },
        movePageLast:function(){
            this.currentPage = this.lastPage;
            console.log(this.lastPage);
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
					return row[key].toLowerCase().indexOf(this.filterKey) > -1;
				});
                
				return match;
            });
            
		},
		articles: function(){  
           
            if(this.filtered.length == 0){
                $('table > tbody.articles_notExists').css('display' ,'table-row-group');
                $('table > tbody.articles_exists').css('display' ,'none');
                
            }
            if(this.filtered.length > 0){
                $('table > tbody.articles_notExists').css('display' ,'none');
                $('table > tbody.articles_exists').css('display' ,'table-row-group');
               
            }
            let itemsInAPage = 10;
            let start = (this.currentPage - 1) * itemsInAPage;
            let end = start + itemsInAPage;
            if(end > this.filtered.length){
                end = this.filtered.length;
            }

         

			if(this.filtered.length > 10){
                const ar = [];                

                let index = 0;
				for (var j = start ; j < end ; j++){
					
					ar[index] = this.filtered[j];
                    index++;
				}             
                 return ar;
			}else {
                return this.filtered;
            }
        },
        pages:function(){
                
            let itemsInAPage = 10;
            let pagesCount = this.filtered.length / 10;
            pagesCount =  Math.ceil(pagesCount);
            this.lastPage = pagesCount;
            let lastPages = pagesCount / 10;
            lastPages = Math.ceil(lastPages);
            lastPages = (lastPages-1) * itemsInAPage + 1;       
            let start = this.currentPage / 10;
            start = Math.ceil(start);
            start = (start - 1) * itemsInAPage + 1;
            let end = start + itemsInAPage - 1; 
            if(end > pagesCount){
                end = pagesCount; 
            }
            let pages = [];            
            
            for(let i = start; i <= end ; i++){
                pages.push(i);
            }
            if(pages.length < 2){
                $('div.movePageFirst').css('display' , 'none');
                $('div.movePageLast').css('display' , 'none');
            }
            else if(pages.length >= 2){
                $('div.movePageFirst').css('display' , 'block');
                $('div.movePageLast').css('display' , 'block');
            }

            if(this.currentPage <= 10){
                $('span.movePagePrev').css('display', 'none');
            }
            if(this.currentPage > 10){
                $('span.movePagePrev').css('display', 'inline-block');
            }
            if(this.currentPage >= lastPages){
                $('span.movePageNext').css('display', 'none');
            }
            if(this.currentPage < lastPages){
                $('span.movePageNext').css('display', 'inline-block');
            }

           return pages;

        }
        
	}
});


/* 게시물 검색 기능 끝 */




/* 게시물 태그 리스트 검색 기능 시작 */
const articleTagList = [];

$.get(    
	'article_tag.json',
	{},
	function(data) {

        let queryStr = getUrlParams();
        let tag = queryStr.tag;
        $('.tagList__title').text(tag +'태그를 가진 게시글');
        for(var i = 0 ; i < data[tag].length ; i++){
            const article = {
				id: data[tag][i].id,
				regDate: data[tag][i].regDate,
				writer: data[tag][i].extra__writer,
				title: data[tag][i].title,
				body: data[tag][i].body,
				hitCount : data[tag][i].hitCount,
                likesCount : data[tag][i].likesCount,
                commentsCount : data[tag][i].commentsCount,
                extra__boardCode : data[tag][i].extra__boardCode
			};
            
            articleTagList.push(article);
            
        }
		
	},
	'json'
);

const articleTagListBoxVue = new Vue({
	el: ".article-taglist-wrap",
	data: {
		articleList: articleTagList,
        searchKeyword: '',
        searchResult:'',
        currentPage : 1,
        lastPage : 1
	},
	methods: {
		searchKeywordInputed: _.debounce(function(e) {
			this.searchKeyword = e.target.value;
        },500),
        searchKeywordClick:function(){
            this.searchResult = this.searchKeyword;
            this.currentPage = 1;
        },
        searchKeywordInputedEnter:function(){
            if(event.keyCode==13){
                this.searchResult = this.searchKeyword;
                this.currentPage = 1;
            }
        },
        movePage:function(page){
            this.currentPage = page;           
            
        },
        movePageNext:function(){
            this.currentPage = this.currentPage + 10;
            this.currentPage = Math.ceil(this.currentPage / 10 );
            this.currentPage = (this.currentPage - 1) * 10 + 1;
        },
        movePagePrev:function(){
            this.currentPage = this.currentPage - 10;
            this.currentPage = Math.ceil(this.currentPage / 10 );
            this.currentPage = (this.currentPage - 1) * 10 + 1;
        },
        movePageFirst:function(){
            this.currentPage = 1;
        },
        movePageLast:function(){
            this.currentPage = this.lastPage;
            console.log(this.lastPage);
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
					return row[key].toLowerCase().indexOf(this.filterKey) > -1;
				});
                
				return match;
            });
            
		},
		articles: function(){  
           
           

            let itemsInAPage = 10;
            let start = (this.currentPage - 1) * itemsInAPage;
            let end = start + itemsInAPage;
            if(end > this.filtered.length){
                end = this.filtered.length;
            }

         

			if(this.filtered.length > 10){
                const ar = [];                

                let index = 0;
				for (var j = start ; j < end ; j++){
					
					ar[index] = this.filtered[j];
                    index++;
				}             
                 return ar;
			}else {
                return this.filtered;
            }
        },
        pages:function(){
                
            let itemsInAPage = 10;
            let pagesCount = this.filtered.length / 10;
            pagesCount =  Math.ceil(pagesCount);
            this.lastPage = pagesCount;
            let lastPages = pagesCount / 10;
            lastPages = Math.ceil(lastPages);
            lastPages = (lastPages-1) * itemsInAPage + 1;       
            let start = this.currentPage / 10;
            start = Math.ceil(start);
            start = (start - 1) * itemsInAPage + 1;
            let end = start + itemsInAPage - 1; 
            if(end > pagesCount){
                end = pagesCount; 
            }
            let pages = [];            
            
            for(let i = start; i <= end ; i++){
                pages.push(i);
            }
            if(pages.length < 2){
                $('div.movePageFirst').css('display' , 'none');
                $('div.movePageLast').css('display' , 'none');
            }
            else if(pages.length >= 2){
                $('div.movePageFirst').css('display' , 'block');
                $('div.movePageLast').css('display' , 'block');
            }
            if(this.currentPage <= 10){
                $('span.movePagePrev').css('display', 'none');
            }
            if(this.currentPage > 10){
                $('span.movePagePrev').css('display', 'inline-block');
            }
            if(this.currentPage >= lastPages){
                $('span.movePageNext').css('display', 'none');
            }
            if(this.currentPage < lastPages){
                $('span.movePageNext').css('display', 'inline-block');
            }

           return pages;

        }
        
	}
});
/* 게시물 태그 리스트 검색 기능 끝 */
    
    
    