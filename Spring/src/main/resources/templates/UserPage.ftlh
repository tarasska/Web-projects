<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring/>

<@c.page>
    <#if user??>
        <div class="user-profile">
            <div class="info">
                <ul>
                    <li>id: ${user.getId()}</li>
                    <li>login: ${user.getLogin()}</li>
                    <li>creationTime: ${user.getCreationTime()}</li>
                </ul>
            </div>
            <div class="user-img">
                <img src="<@spring.url '/img/user.png'/>" alt="User" title="User"/>
            </div>
        </div>
    <#else>
        <h1>No such user</h1>
    </#if>
</@c.page>


