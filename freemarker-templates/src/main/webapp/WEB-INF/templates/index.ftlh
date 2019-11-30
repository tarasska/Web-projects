<#import "common.ftl" as c/>
<#import "article.ftl" as at/>

<@c.page>
    <#list posts?reverse as p>
        <#if p??>
            <@at.article post=p/>
        </#if>
    </#list>
</@c.page>
