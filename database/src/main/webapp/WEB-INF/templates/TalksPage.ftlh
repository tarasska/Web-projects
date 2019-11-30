<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="userService" type="ru.itmo.wp.model.service.UserService" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <form class="talksForm" id="talksForm" method="post" action="">
        <input type="hidden" name="action" value="send"/>
        <select name="recipientUserId">
            <option disabled selected>выберете адресата</option>
            <#list users as u>
                <option value="${u.id}">${u.login}</option>
            </#list>
        </select>
        <#if error??>
            <div class="error">
                ${error!}
            </div>
        </#if>
        <p><textarea rows="5" cols="45" maxlength="65535" required name="text"></textarea></p>
        <div class="button-field">
            <input type="submit" value="Send">
        </div>
    </form>
    <form class="refreshForm" id="refreshForm" method="post" action="">
        <input type="hidden" name="action" value="refreshTalks"/>
        <div class="button-field">
            <input type="submit" value="Refresh talks">
        </div>
    </form>
    <#if talks??>
        <div class="datatable talksTable">
            <div class="caption">Talks</div>
            <table>
                <thead>
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Text</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <#list talks as t>
                    <tr>
                        <td>${userService.find(t.sourceUserId).getLogin()!}</td>
                        <td>${userService.find(t.targetUserId).getLogin()!}</td>
                        <td>${t.text}</td>
                        <td>${t.creationTime}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    <#else>
        <div class="refreshMessage"> Please, refresh talks list if you want to check you mail </div>
    </#if>
</@c.page>
