$(function () {
    var StatisticsList = {
        filterData: {},
        template: Handlebars.compile($("#tpl-statistics-item").html()),
        counterTemplate: Handlebars.compile($("#tpl-statistics-count").html()),
        init: function () {
            var $search = $(".search-button");
            $search.click(function () {
                StatisticsList.fillFilter();
            });
            $search.click();
        },
        fillFilter: function () {
            var formData = $(".filter-bar").serializeArray();
            for (var i = 0; i < formData.length; i++) {
                if (formData[i].value) {
                    StatisticsList.filterData[formData[i].name] = formData[i].value;
                } else {
                    StatisticsList.filterData[formData[i].name] = undefined;
                }
            }
            StatisticsList.search();

        },
        search: function () {
            var data = JSON.stringify(StatisticsList.filterData);
            $.ajax({
                url: '/ajax/dealer/statistics',
                type: 'put',
                data: data,
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (result) {
                StatisticsList.render(result);
            }).fail(function () {
                $.alert({size: "small", content: "暂时无法获取数据"});
            });
        },
        render: function (result) {
            var html = "",
                counter = {
                    insuredFeeCount: 0,
                    insuredNumCount: 0,
                    insuredInsuredNumCount: 0,
                    surrenderFeeCount: 0,
                    surrenderNumCount: 0,
                    surrenderInsuredNumCount: 0,
                    rejectFeeCount: 0,
                    rejectNumCount: 0,
                    rejectInsuredNumCount: 0
                };
            for (var i = 0; i < result.items.length; i++) {
                var data = result.items[i];
                html += StatisticsList.template(data);

                counter.insuredFeeCount += data.insuredFee;
                counter.insuredNumCount += data.insuredNum;
                counter.insuredInsuredNumCount += data.insuredInsuredNum;
                counter.surrenderFeeCount += data.surrenderFee;
                counter.surrenderNumCount += data.surrenderNum;
                counter.surrenderInsuredNumCount += data.surrenderInsuredNum;
                counter.rejectFeeCount += data.rejectFee;
                counter.rejectNumCount += data.rejectNum;
                counter.rejectInsuredNumCount += data.rejectInsuredNum;
            }
            html += StatisticsList.counterTemplate(counter);
            var table = $(".dealer-table");
            table.find("tbody").html(html);
        }
    };

    StatisticsList.init();
});