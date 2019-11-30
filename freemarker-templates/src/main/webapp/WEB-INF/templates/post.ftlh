<#-- @ftlvariable name="post_id" type="java.lang.Long" -->

<#import "common.ftl" as c/>
<#import "article.ftl" as at/>

<@c.page>
    <#assign currentPost=c.findBy(posts, "id", post_id)!/>
    <#if currentPost??>
        <@at.article post=currentPost/>
    </#if>
</@c.page>