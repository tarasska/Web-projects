<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "commons.ftlh" as c>

<@c.page>
    <form class="noticeForm" id="noticeForm" method="post" action="">
        <input type="hidden" name="action" value="postNotice"/>
        <p><textarea rows="5" cols="45" maxlength="65535" required name="noticeContent"></textarea></p>
        <div class="button-field">
            <input type="submit" value="Publish">
        </div>
        <div class="error"></div>
    </form>
</@c.page>