"use strict";

function SideNav (rootForm, globalEditable) {
    var nav = {
        root: rootForm,
        init: function () {
            this.initEvent();
            this.initForm();
        },
        briefForm: null,
        detailForm: null,
        initEvent: function () {
            $(document).scroll(function () {
                if ($("body").scrollTop() <= 210) {
                    $(".side-nav").removeClass("fixed");
                } else if ($("body").height() - $("body").scrollTop() <= $(".side-nav").height() + 274) {
                    $(".side-nav").css("top", (-274 - ($(".side-nav").height() - ($("body").height() - $("body").scrollTop()))) + "px");
                } else {
                    $(".side-nav").addClass("fixed").css("top", "15px").css("margin-right", (($(window).width() - $(".page").width())/2 - 5) + "px");
                }
            });
            $(".side-nav").find(".popup").mouseenter(function () {
                $(".side-nav").addClass("open-popup");
            });
            $(".side-nav").mouseleave(function () {
                $(".side-nav").removeClass("open-popup");
            });
            $(".side-nav").on("click", ".serial-product-id", function () {
                $(".serial-product-id").parent().removeClass("active");
                $(this).parent().addClass("active");
                var data = {};
                data.productId = $("#pdp-product-id").val();
                data.value = nav.detailForm.val();
                $.ajax({
                    url: "/ajax/checkout",
                    type: "post",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    dataType: "json"
                }).done(function (result) {
                    window.location.href = "";
                }).fail(function () {
                    alert("提交失败");
                });

            });
        },
        initForm: function () {
            this.initBriefForm();
            this.initDetailForm();
            $(".side-nav-wrap").slideDown();
        },
        initBriefForm: function () {
            var formData = {
                groups: [],
                value: {}
            };
            formData.groups.push(this.root.group("plan").group);
            formData.groups.push(this.root.group("liability").group);
            formData.value = this.root.form.value;
            var myForm = new DynamicForm(formData, $(".content")[0]);
            myForm.formBuilder(function (form) {
                var render = Handlebars.compile(document.querySelector("#tpl_sidenav_form").innerHTML);
                var html = render(form);
                return createElement(html);
            }).groupBuilder("common", function (groupAttribute, value) {
                var group = new DynamicGroup(groupAttribute, value);
                var render = Handlebars.compile(document.querySelector("#tpl_sidenav_group").innerHTML);
                var html = render(groupAttribute);
                group.fieldLimit = 5;
                group.element = createElement(html);
                return group;
            }).editable(function () {
                return false;
            });

            myForm.init();
            this.briefForm = myForm;
        },
        initDetailForm: function () {
            var formData = {
                groups: [],
                value: {}
            };
            formData.groups.push(this.root.group("plan").group);
            formData.groups.push(this.root.group("liability").group);
            formData.value = this.root.form.value;
            var myForm = new DynamicForm(formData, $(".popup-panel-content")[0]);
            myForm.formBuilder(function (form) {
                var render = Handlebars.compile(document.querySelector("#tpl_sidenav_form").innerHTML);
                var html = render(form);
                return createElement(html);
            }).groupBuilder("common", function (groupAttribute, value) {
                var group = new DynamicGroup(groupAttribute, value);
                var render = Handlebars.compile(document.querySelector("#tpl_sidenav_group").innerHTML);
                var html = render(groupAttribute);
                group.element = createElement(html);
                return group;
            }).editable(function () {
                return globalEditable;
            });

            myForm.init();
            this.detailForm = myForm;
        }

    };
    nav.init();
    return nav;
}
