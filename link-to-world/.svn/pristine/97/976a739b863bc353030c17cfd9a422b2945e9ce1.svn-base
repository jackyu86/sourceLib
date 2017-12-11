"use strict";

/*  */
$(function () {
    var Dealer = {
        dealerId: $("#dealer-id").val(),
        tree: [],
        autoAllocate: $("#auto-allocate").find("input").prop("checked"),
        nodeMap: {},
        init: function () {
            $(".submit-button").click(function () {
                Dealer.submit();
            });
            $(".management-panel").on("click", ".checkbox>input[type=checkbox]+.checkbox-label", function (event) {
                var checkbox = $(event.currentTarget).prev();
                var nodes = this.nodeMap[checkbox.val()];
                if (nodes.length) {
                    for (var index = 0; index < nodes.length; index += 1) {
                        var node = nodes[index];
                        node.checked = !checkbox.prop("checked");
                        this.checkNode(node, node.checked);
                    }
                } else {
                    nodes.checked = !checkbox.prop("checked");
                    this.checkNode(nodes, nodes.checked);
                }

                this.renderTree();
            }.bind(this)).on("click", ".btn-delete", function (event) {
                var nodes = this.nodeMap[$(event.currentTarget).data("id")];
                var index = 0;
                for (; index < nodes.length; index += 1) {
                    nodes[index].checked = false;
                }
                this.renderTree();
            }.bind(this)).on("click", "ul>li", function (event) {
                $(event.currentTarget).toggleClass("open");
            });

            $(".btn-save").click(function () {
                if (this.autoAllocate) {
                    this.autoAllocateSubmit();
                } else {
                    this.submit();
                }
            }.bind(this));

            $("#auto-allocate").click(function () {
                this.autoAllocate = !this.autoAllocate;
                this.display();
            }.bind(this));
            this.initProductTree();
        },
        display: function () {
            if (this.autoAllocate) {
                $(".total-product-panel .mask").show();
                $(".btn-delete").hide();
            } else {
                $(".total-product-panel .mask").hide();
                $(".btn-delete").show();
            }
        },
        autoAllocateSubmit: function () {
            $.ajax({
                url: "/ajax/dealer/" + $("#dealer-id").val() + "/auto-allocate",
                type: "put"
            }).done(function () {
                $.prompt({
                    "size": "small",
                    "level": "success",
                    "confirm": "确定",
                    "content": "自动分配切换成功",
                    callback: function () {
                        window.location.href = "";
                    }
                });
            }).fail(function () {
                $.alert({
                    "size": "small",
                    "content": "自动分配切换失败"
                })
            });
        },
        checkNode: function (node, checked) {
            node.checked = checked;
            if (node.children && node.children.length > 0) {
                for (var index = 0; index < node.children.length; index += 1) {
                    var subNode = node.children[index];
                    this.checkNode(subNode, checked);
                }
            }
            if (node.productList) {
                for (var productIndex = 0; productIndex < node.productList.length; productIndex += 1) {
                    var relatedNodes = this.nodeMap[node.productList[productIndex].name];
                    if (relatedNodes.length) {
                        for (var relatedIndex = 0; relatedIndex < relatedNodes.length; relatedIndex += 1) {
                            var relatedNode = relatedNodes[relatedIndex];
                            relatedNode.checked = checked;
                            this.checkNode(relatedNode, relatedNode.checked);
                        }
                    } else {
                        relatedNodes.checked = checked;
                        this.checkNode(relatedNodes, relatedNodes.checked);
                    }
                }
            }
        },
        initProductTree: function () {
            $.get("/account/dealer/" + this.dealerId + "/products").done(function (tree) {
                this.tree = tree;
                this.flatTree();
                this.renderTree();
            }.bind(this)).fail(function () {
                $(".form-error").show();
            });
        },
        flatTree: function () {

            function fillNode(category, dealer) {
                dealer.nodeMap[category.category.id] = category;
                if (category.productList) {
                    for (var productIndex = 0; productIndex < category.productList.length; productIndex += 1) {
                        category.productList[productIndex].isProduct = true;
                        if (!dealer.nodeMap[category.productList[productIndex].name]) {
                            dealer.nodeMap[category.productList[productIndex].name] = [];
                        }
                        dealer.nodeMap[category.productList[productIndex].name].push(category.productList[productIndex]);
                    }
                }
                if (category.children && category.children.length > 0) {
                    for (var categoryIndex = 0; categoryIndex < category.children.length; categoryIndex += 1) {
                        fillNode(category.children[categoryIndex], dealer);
                    }
                }
            }

            for (var index = 0; index < this.tree.length; index += 1) {
                fillNode(this.tree[index], this);
            }
        },
        renderTree: function () {
            this.validateTree();
            this.validateAllocateTree();
            var totalRender = Handlebars.compile(document.querySelector("#tpl_tree").innerHTML);
            $(".total-product-panel").html(totalRender({list: this.tree}));

            var allocatedRender = Handlebars.compile(document.querySelector("#tpl_tree_allocated").innerHTML);
            $(".allocated-product-panel").html(allocatedRender({list: this.tree}));

            this.display();
        },

        validateTree: function () {
            function validate(categoryList) {
                var flag = false;
                for (var index = 0; index < categoryList.length; index += 1) {
                    var category = categoryList[index];
                    if (category.productList.length > 0) {
                        category.show = true;
                        flag = true;
                    }
                    if (category.children) {
                        if (validate(category.children)) {
                            category.show = true;
                            flag = true;
                        }
                    }
                }
                return flag;
            }

            validate(this.tree);
        },
        validateAllocateTree: function () {
            function validate(categoryList) {
                var flag = false;
                for (var index = 0; index < categoryList.length; index += 1) {
                    var category = categoryList[index];
                    var categoryFlag = false;
                    if (category.productList.length > 0) {
                        if (validateAllocateProduct(category.productList)) {
                            category.checked = true;
                            flag = true;
                            categoryFlag = true;
                        }
                    }
                    if (category.children) {
                        if (validate(category.children)) {
                            category.checked = true;
                            flag = true;
                            categoryFlag = true;
                        }
                    }
                    category.checked = categoryFlag;
                }

                return flag;
            }

            function validateAllocateProduct(productList) {
                var flag = false;
                for (var index = 0; index < productList.length; index += 1) {
                    if (productList[index].checked) {
                        flag = true;
                    }
                }
                return flag;
            }

            validate(this.tree);
        },

        submit: function () {
            $(".form-error").hide();
            var data = {productNameList: []};
            for (var key in this.nodeMap) {
                if (this.nodeMap[key]) {
                    if (this.nodeMap[key].length) {
                        for (var index = 0; index < this.nodeMap[key].length; index += 1) {
                            if (this.nodeMap[key][index].isProduct && this.nodeMap[key][index].checked) {
                                data.productNameList.push(key);
                                break;
                            }
                        }
                    } else {
                        if (this.nodeMap[key].isProduct && this.nodeMap[key].checked) {
                            data.productNameList.push(key);
                        }
                    }

                }
            }
            $.ajax({
                url: "/ajax/dealer/" + $("#dealer-id").val() + "/products",
                type: "put",
                data: JSON.stringify(data),
                contentType: "application/json"
            }).done(function () {
                $.prompt({
                    "size": "small",
                    "level": "success",
                    "confirm": "确定",
                    "content": "商品权限更新成功",
                    callback: function () {
                        window.location.href = "";
                    }
                });
            }).fail(function () {
                $(".form-error").show();
            });
        }
    };

    Dealer.init();
});