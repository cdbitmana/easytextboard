function ArticleDetail__Body__init() {

    var body = document.querySelector('.article_body');
    var initialValue = body.innerHTML.trim();
    initialValue = initialValue.replace("&gt;",">");
    initialValue = initialValue.replace("&lt;","<");

    var viewer = new toastui.Editor.factory({
        
        el : body,
        initialValue : initialValue,
        viewer : true,
        plugins : [toastui.Editor.plugin.codeSyntaxHighlight]
    
    });
  }
  ArticleDetail__Body__init();