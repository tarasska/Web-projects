window.notify = function (message) {
    $.notify(message, {position: "bottom right"})
};



ajax = function (options) {
    var setting = {
        type: "POST",
        url: "",
        dataType: "json",
        error: null,
        successEx: function (response) {
            // empty
        },
        success: function (response) {
            if (response["redirect"]) {
                location.href = response["redirect"];
            } else if (response["error"] ) {
                if (options.error != null) {
                    options.error.text(response["error"]);
                } else {
                    notify(response["error"])
                }
            }
            if (options.successEx != null) {
                options.successEx(response);
            }
        }
    };
    var args = $.extend(setting, options);

    $.ajax(args);
    return this;
};

setInformation = function (information, curArticle) {
    ajax({
        url: "/index",
        data: {
            action: "findUserLoginById",
            userId: curArticle["userId"]
        },
        success: function (response) {
            if (response["userLogin"]) {
                information.text("By " + response["userLogin"] + " ," + curArticle["creationTime"]);
            } else {
                information.text("By " + "?" + ", " + curArticle["creationTime"]);
            }
        }
    });
};