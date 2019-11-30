<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Visibility</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as a>
                <tr>
                    <td class="id-${a.id}">${a.id}</td>
                    <td class="title">${a.title}</td>
                    <td class="button">
                        <form method="post" action="">
                            <div class="button-field">
                                <#if a.hidden>
                                    <#assign val="Show">
                                <#else>
                                    <#assign val="Hide">
                                </#if>
                                <input name="articleId" type="hidden" value=${a.id} readonly>
                                <input name="buttonId" type="submit" value="${val}">
                            </div>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <script>
        $(function () {
            $(".articles form").submit(function () {
                var articleId = $(this).find("input[name='articleId']").val();
                var button = $(this).find("input[name='buttonId']");
                ajax({
                    data: {
                        action: "changeVisibility",
                        articleId: articleId
                    },
                    successEx: function (response) {
                        if (response["newVisibility"]) {
                            button.val(response["newVisibility"]);
                        }
                    }
                });

                return false;
            });
        })
    </script>
</@c.page>