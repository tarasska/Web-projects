<#-- @ftlvariable name="uri" type="java.lang.String" -->
<#import "article.ftl" as at/>

<#macro header>
    <header>
        <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
        <div class="languages">
            <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
            <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user=user nameOnly="on"/>
                |
                <a href="#">Logout</a>
            <#else>
                <a href="#">Enter</a>
                |
                <a href="#">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <#list menuURI as current>
                    <#if uri==current.uri>
                        <li class="current"><a href=${current.uri}>${current.name}</a></li>
                    <#else>
                        <li><a href=${current.uri}>${current.name}</a></li>
                    </#if>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar post>
<#--<aside>-->
<#--    <section>-->
<#--        <div class="header">-->
<#--            Pay attention-->
<#--        </div>-->
<#--        <div class="body">-->
<#--            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit nobis,-->
<#--            nulla placeat quam suscipit unde voluptatibus.-->
<#--        </div>-->
<#--        <div class="footer">-->
<#--            <a href="#">View all</a>-->
<#--        </div>-->
<#--    </section>-->
<#--</aside>-->
<#--    <aside>-->
    <section>
        <div class="header">
            Post #${post.id}
        </div>
        <div class="body">
            <@at.article post=post/>
        </div>
        <div class="footer">
            <a href="/post?post_id=${post.id}">View all</a>
        </div>
    </section>
<#--    </aside>-->
</#macro>

<#macro footer>
    <footer>
        <a href="#">Codeforces</a> &copy; 2010-2019 by Mike Mirzayanov
    </footer>
</#macro>

<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" type="text/css" href="/css/normalize.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <link rel="stylesheet" type="text/css" href="/css/user.css">
        <link rel="stylesheet" type="text/css" href="/css/post.css">
        <link rel="stylesheet" type="text/css" href="/css/datatable.css">
        <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    </head>
    <body>
    <@header/>
    <div class="middle">
        <aside>
            <#list posts?reverse as p>
                <@sidebar post=p/>
            </#list>
        </aside>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro userlink user nameOnly="off" className="">
    <#if nameOnly=="on">
        <a href="/user?handle=${user.handle}"><span class=${className}>${user.name}</span></a>
    <#else>
        <a class="beauty-handle" href="/user?handle=${user.handle}"><span class="${className} ${user.color}">${user.name}</span></a>
    </#if>
</#macro>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#return item/>
        </#if>
    </#list>
</#function>

<#------------------->

<#function findIndexWithShift items baseItem shift>
    <#list items as item>
        <#if item==baseItem>
            <#if shift==-1>
                <#if item?index gt 0>
                    <#return item?index - 1/>
                <#else>
                    <#return -1/>
                </#if>
            <#else>
                <#if item?has_next>
                    <#return item?index + 1/>
                <#else>
                    <#return -1/>
                </#if>
            </#if>
        </#if>
    </#list>
</#function>

<#macro arrowLink user direction>
    <#if direction=="left">
        <a class="arrow" href="/user?handle=${user.handle}"> ←</a>
    <#else>
        <a class="arrow" href="/user?handle=${user.handle}"> →</a>
    </#if>
</#macro>