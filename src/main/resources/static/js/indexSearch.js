//È meglio usare delle librerie per fare queste richieste
/*
searchPostNoLibraries = function(){
    var request = new XMLHttpRequest();

    request.onreadystatechange = function(){
        if(request.readyState == 4 && request.status == 200){
            alert("OK");
        } else {
            alert("Could not search posts...Error code: "+request.status);
        }
    };

    request.open("GET","http://localhost:8080/blogposts/search?q="+$("#searchText").text(), true);
    request.send();
}
*/

$(document).ready(function(){
    $('#searchText').on('input',searchPost);
});

writePosts = function(posts){
    let retval = "";
    for(let i = 0; i<posts.length; i++){
        let postTitle = posts[i].title;
        let text = posts[i].text;
        let author = posts[i].author.username;
        let postID = posts[i].id;
        let date = posts[i].date;

        retval += "<article class=\"card mb-4 row\">" +
                       "<div class=\"col card-body\">\n" +
                            "<p class=\"card-header\">"+author+"</p>\n" +
                            "<a style=\"font-family: fantasy; font-size: 2rem;\" class=\"card-title mb-5\" href=\"/blog/"+postID+"\">"+postTitle+"</a>\n" +
                            "<p class=\"card-text\">"+text+"</p>\n" +
                            "<time class=\"card-footer mt-3\"><em>"+date+"</em></time>\n" +
                        "</div>"+
                    "</article>";
    }

    return retval;
}

//jQuery AJAX implementation
searchPost = function(){
    if($("#searchText").val().length < 3)
        return;

    $.ajax({
        url:"blogposts/search?q="+$("#searchText").val(),
        type:"GET",
        dataType:"json"
    })
        .done(function(data) {
             $('article').remove();
             $('main').after(writePosts(data));
        })
        .fail(function() {
            alert( "Could not search for posts" );
        });
}