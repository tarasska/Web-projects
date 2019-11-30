<#macro article post maxLength=250>
    <article>
        <div class="title">${post.title}</div>
        <div class="body">
            <#if post.text?length <= maxLength>
                <p>${post.text}</p>
            <#else>
                <p>${post.text?substring(0, maxLength)} <a href="/post?post_id=${post.id}">...</a></p>
            </#if>
        </div>
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
</#macro>