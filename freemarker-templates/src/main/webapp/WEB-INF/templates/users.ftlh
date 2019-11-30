<#import "common.ftl" as c/>

<@c.page>
<#--    <ul>-->
<#--        <#list users as u>-->
<#--        <li><#if user?? && u.id == user.id>*</#if> <@c.userlink user=u/>-->
<#--            </#list>-->
<#--    </ul>-->
    <div class="datatable">
        <div class="caption">Users list</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>User</th>
                <th>Handle</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td><@c.userlink user=u/></td>
                    <td>${u.handle}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
