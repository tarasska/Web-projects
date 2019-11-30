<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "commons.ftlh" as c>

<@c.page>
    <form class="articleForm" id="articleForm" method="post" action="">
        <input type="hidden" name="action" value="createArticle"/>
        <div class="field">
            <div class="title">
                <label for="title">Title</label>
            </div>
            <div class="value">
                <input id="title" name="title"/>
            </div>
        </div>
        <p><textarea rows="5" cols="45" maxlength="65535" required name="text"></textarea></p>
        <div class="button-field">
            <input type="submit" value="Create">
        </div>
        <div class="error"></div>
    </form>
    <script>
        $(function () {
            $(".articleForm").submit(function () {
                var title = $(this).find("input[name='title']").val();
                var text = $(this).find("textarea[name='text']").val();
                var $error = $(this).find(".error");

                ajax({
                    data: {
                        action: "createArticle",
                        title: title,
                        text: text
                    },
                    error: $error
                });

                return false;
            });
        })
    </script>
</@c.page>