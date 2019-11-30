<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as u>
                <tr>
                    <td class="id">${u.id}</td>
                    <td class="login"><@c.userLink u/></td>
                    <td class="creationTime">${u.creationTime}</td>
                    <td class="status">
                        <form name="disabledForm" method="post" action="setDisabled">
                            <div class="button-field">
                                <#if u.isDisabled()>
                                    <#assign val="Enable">
                                <#else>
                                    <#assign val="Disable">
                                </#if>
                                <input name="userId" type="hidden" value="${u.id}">
                                <input name="disabled" type="hidden" value=${(!u.isDisabled())?c}>
                                <input name="status" type="submit" value="${val}">
                            </div>
                        </form>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
