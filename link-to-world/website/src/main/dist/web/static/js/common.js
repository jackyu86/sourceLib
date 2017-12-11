$(function () {
    $(document).on("click", ".radio>input[type=radio]+.radio-label", function () {
        console.log("triggered");
        var radios = $(this).parents(".radio-group").find(".radio>input[type=radio]");
        var radio = $(this).prev("input");
        var name = radio.attr("name");
        for (var i = 0; i < radios.length; i++) {
            if (radios.eq(i).attr("name") === name) {
                radios.eq(i).removeAttr("checked");
                radios.eq(i).prop("checked", false);
            }
        }
        radio.attr("checked", "checked");
        radios.eq(i).prop("checked", true);
    });
    $(document).on("click", ".checkbox>input[type=checkbox]+.checkbox-label", function () {
        var checkbox = $(this).prev("input");
        checkbox.prop("checked", !checkbox.prop("checked"));
        checkbox.attr("checked", checkbox.prop("checked") ? "checked" : null);
    });
    WINDOW_CHANGE_EVENT = ('onorientationchange' in window) ? 'orientationchange' : 'resize';
    window.addEventListener(WINDOW_CHANGE_EVENT, function () {
        adjustFooter();
    });

    adjustFooter();

});

function adjustFooter() {
    if (window.innerHeight > document.querySelector("body").clientHeight) {
        $("footer").addClass("fixed");
    } else {
        $("footer").removeClass("fixed");
    }
}

$.confirm = function (options) {
    this.sizes = {"large": "modal-lg", "small": "modal-sm"};
    this.levels = {
        "primary": "btn-primary",
        "info": "btn-info",
        "warning": "btn-warning",
        "danger": "btn-danger",
        "success": "btn-success"
    };
    var _options = {
        size: "large",
        level: "primary",
        confirm: "确认",
        content: "是否确认",
        callback: function () {
        }
    };
    _options = $.extend(_options, options);
    var modal = $("#common-confirm-modal");
    modal.find(".modal-dialog").removeClass("modal-lg").removeClass("modal-sm")
        .addClass(this.sizes[_options.size]).find(".confirm-content").text(_options.content);
    modal.find(".btn-confirm").text(_options.confirm)
        .removeClass("btn-primary").removeClass("btn-info").removeClass("btn-warning").removeClass("btn-danger").removeClass("btn-success")
        .addClass(this.levels[_options.level])
        .off("click")
        .on("click", function () {
            _options.callback();
        });
    modal.modal("show");
};
$.prompt = function (options) {
    this.sizes = {"large": "modal-lg", "small": "modal-sm"};
    this.levels = {
        "primary": "btn-primary",
        "info": "btn-info",
        "warning": "btn-warning",
        "danger": "btn-danger",
        "success": "btn-success"
    };
    var _options = {
        size: "large",
        level: "primary",
        confirm: "确认",
        content: "是否确认",
        callback: function () {
        },
        dismiss: function () {

        }
    };
    _options = $.extend(_options, options);
    var modal = $("#common-prompt-modal");
    modal.find(".modal-dialog").removeClass("modal-lg").removeClass("modal-sm")
        .addClass(this.sizes[_options.size]).find(".confirm-content").text(_options.content);
    modal.find(".btn-confirm").text(_options.confirm)
        .removeClass("btn-primary").removeClass("btn-info").removeClass("btn-warning").removeClass("btn-danger").removeClass("btn-success")
        .addClass(this.levels[_options.level])
        .off("click")
        .on("click", function () {
            _options.callback();
        });
    modal.on("hidden.bs.modal", function () {
        _options.dismiss();
        modal.unbind("hidden.bs.modal");
    });
    modal.modal("show");
};
$.alert = function (options) {
    var modal = $("#common-alert-modal");
    var $options = {
        size: "large",
        level: "primary",
        confirm: "确认",
        content: "是否确认"
    };

    this.sizes = {
        "large": "modal-lg",
        "small": "modal-sm"
    };
    this.levels = {
        "primary": "btn-primary",
        "info": "btn-info",
        "warning": "btn-warning",
        "danger": "btn-danger",
        "success": "btn-success"
    };
    $options = $.extend($options, options);
    modal.find(".modal-dialog").removeClass("modal-lg").removeClass("modal-sm")
        .addClass(this.sizes[$options.size]).find(".confirm-content").text($options.content);
    modal.modal("show");
};

function handleFailResponse(response, form) {
    var e = response.responseJSON;
    if (e.invalidFields) {
        for (var i = 0; i < e.invalidFields.length; i++) {
            var invalidField = e.invalidFields[i];
            var input = form.find('input[name=' + invalidField.path + ']');
            var label = input.siblings(".text-danger.validation-message");
            if (label.length > 0) {
                label.text(invalidField.message);
            } else {
                input.parent().addClass('has-error').append('<label id="' + invalidField.path + '-error" class="text-danger validation-message">' + invalidField.message + '</label>');
            }
        }
    }
}

var CommonUtils = {
    ageToDate: function (age) {
        var now = new Date();
        now.setFullYear(now.getFullYear() - age);
        return now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
    },
    dateToAge: function (dateStr) {
        var date = new Date(dateStr);
        var now = new Date();
        var age = now.getFullYear() - date.getFullYear();
        if (date.getMonth() < now.getMonth()) {
            return age;
        }
        if (now.getMonth() === date.getMonth() && now.getDate() > date.getDate()) {
            return age;
        }
        return age - 1;
    }
};

function getIdTypeLength(idType) {
    switch(idType) {
        case "1": return 18;
        case "2": return 8;
        case "3": return 8;
        case "4": return 10;
        case "9": return 255;
        case "11": return 9;
    }
}
function getIdTypeId(idType) {
    switch(idType) {
        case "身份证": return "1";
        case "军人证": return "2";
        case "护照": return "3";
        case "出生证明": return "4";
        case "其他": return "9";
        case "户口簿": return "11";
        default: return "1";
    }
}
