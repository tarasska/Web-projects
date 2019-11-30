<#-- @ftlvariable name="user_id" type="java.lang.Long" -->

<#import "common.ftl" as c/>
<#import "article.ftl" as at/>

<@c.page>
    <#list posts as p>
        <#if p?? && p.user_id == user_id>
            <@at.article post=p/>
        </#if>
    </#list>
</@c.page>
