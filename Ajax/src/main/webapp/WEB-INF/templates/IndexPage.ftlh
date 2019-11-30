

<#import "commons.ftlh" as c>

<@c.page>
    <div class="articleList">
        <template class="articleTemplate">
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="text"></div>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        2 days ago
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
        <div class="noData">
            no data
        </div>
    </div>

    <script>
        $(function () {
            var $noData = $(".articleList .noData");
            $noData.find("div").text("Loading...");

            ajax({
                url: "/index",
                data: {
                    action: "findAllArticles"
                },
                successEx: function (response) {
                    response["articles"].forEach(function (article) {
                        if (!article["hidden"]) {
                            $noData.hide();
                            var $article = $($(".articleList template").prop("content")).clone();
                            $article.find(".title").text(article["title"]);
                            setInformation($article.find(".information"), article);
                            $article.find(".text").text(article["text"]);
                            $(".articleList").append($article);
                        }
                    });
                }
            });
        })
    </script>
</@c.page>
