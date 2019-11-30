<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <#if user?? && user.admin>
                        <td class="user_admin">
                            <a class="isAdmin"></a>
                            <a id="link" class="button" href="/#"></a>
                        </td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            var $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax({
                data: {
                    action: "findAll"
                },
                successEx: function(response) {
                    response["users"].forEach(function(user) {
                        $noData.hide();
                        var $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find(".user_admin .isAdmin").text(user["admin"] + " ");
                        var status;
                        if (user["admin"]) {
                            status = "disable";
                        } else {
                            status = "enable";
                        }
                        $tr.find(".user_admin .button").text(status);
                        $tr.find("tr").attr("data-userId", user["id"]);
                        $(".users tbody").append($tr);
                    });
                    $(".users tr td a").click(function () {
                        var curNode = this;
                        ajax({
                            data: {
                                action: "changeAdminStatus",
                                newAdminStatus: $(this.parentNode).find(".button").text(),
                                userId: $(curNode.parentNode.parentNode).attr("data-userId")
                            },
                            successEx: function (response) {
                                if (response["newAdminStatus"]) {
                                    $(curNode).text(response["newAdminStatus"]);
                                    $(curNode.parentNode).find(".isAdmin").text(response["isAdmin"]);
                                }
                            }
                        });
                        return false;
                    });
                    $(".users tr").click(function() {
                        ajax({
                            data: {
                                action: "findUser",
                                userId: $(this).attr("data-userId")
                            },
                            successEx: function (response) {
                                notify(response["user"].creationTime);
                            }
                        })
                    });
                }
            });

        })
    </script>
</@c.page>
