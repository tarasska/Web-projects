<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->

<#import "common.ftl" as c/>

<@c.page>
    <#if handle??>
        <#assign viewedUser=c.findBy(users, "handle", handle)!/>
    <#else>
        <#assign viewedUser=c.findBy(users, "id", user_id)!/>
    </#if>
    <#if viewedUser.id??>
        <div class="user-profile">
            <div class="info">
                <@c.userlink user=viewedUser className="user-name"/>
                <ul>
                    <li>id: ${viewedUser.id}</li>
                    <li>handle: ${viewedUser.handle}</li>
                    <li>name: ${viewedUser.name}</li>
                    <li>posts: <a href="/posts?user_id=${viewedUser.id}"> ${postCount(viewedUser.id)} </a></li>
                </ul>
            </div>
            <div class="user-img">
                <img src="/img/user.jpg" alt="User" title="User"/>
            </div>
        </div>

        <#-- arrow begin-->
        <#assign prevInd=c.findIndexWithShift(users, viewedUser, -1)/>
        <#if prevInd!=-1>
            <@c.arrowLink user=users[prevInd] direction="left"/>
        <#else>
            <a class="inactive-arrow"> ←</a>
        </#if>
        <#assign nextInd=c.findIndexWithShift(users, viewedUser, 1)/>
        <#if nextInd!=-1>
            <@c.arrowLink user=users[nextInd] direction="right"/>
        <#else>
            <a class="inactive-arrow"> →</a>
        </#if>
        <#-- arrow end-->
    <#else>
        <h1>No such user</h1>
    </#if>
</@c.page>

<#function postCount userId>
    <#local cnt = 0>
    <#list posts as p>
        <#if p?? && p.user_id == userId>
            <#local cnt = cnt + 1>
        </#if>
    </#list>
    <#return cnt>
</#function>