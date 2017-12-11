var State = {
    $cityDom: {},
    city: {},
    init: function () {
        var $this = this,
            stateDom = $("select[name=state]");
        $this.$cityDom = $("select[name=city]");
        $this.city = City.init();
        $this.refresh(stateDom.find('option:selected').data("id"));
        stateDom.change(function () {
            $this.refresh(stateDom.find('option:selected').data("id"));
        });
        return $this;
    },
    handle: function (stateVal, cityVal, wardVal) {
        var $this = this,
            stateDom = $("select[name=state]");
        if (stateVal.length > 0) {
            stateDom.val(stateVal);
            $this.refresh(stateDom.find('option:selected').data("id"), function () {
                $this.$cityDom.val(cityVal);
                $this.city.handle(cityVal, wardVal);
            });
        }
    },
    refresh: function (stateId, callback) {
        var $this = this;
        $this.clean();
        $.getJSON("/ajax/province/" + stateId).done(function (result) {
            var cities = {};
            for (var index = 0; index < result.length; index += 1) {
                cities[result[index].id] = result[index];
            }
            $this.render(cities);
            if (callback !== undefined) {
                callback();
            }
        }).fail(function (result) {
            console.log(result);
            $.alert({
                "size": "small",
                "content": "城市加载失败！"
            });
        });
    },
    clean: function () {
        this.$cityDom.html("");
        this.city.clean();
    },
    render: function (cities) {
        for (var key in cities) {
            var city = cities[key];
            this.$cityDom.append($("<option data-id='" + city.id + "' value='" + city.name + "'>" + city.name + "</option>"));
        }
        this.city.refresh(this.$cityDom.find('option:selected').data("id"));
    }
};
var City = {
    $wardDom: {},
    init: function () {
        var $this = this,
            cityDom = $("select[name=city]");
        cityDom.change(function () {
            $this.refresh(cityDom.find('option:selected').data("id"));
        });
        this.$wardDom = $("select[name=ward]");
        return this;
    },
    handle: function (cityVal, wardVal) {
        var $this = this,
            cityDom = $("select[name=city]"),
            wardDom = $("select[name=ward]");
        if (cityVal.length > 0) {
            cityDom.val(cityVal);
            $this.refresh(cityDom.find('option:selected').data("id"), function () {
                wardDom.val(wardVal);
            });
            cityDom.change(function () {
                $this.refresh(cityDom.find('option:selected').data("id"));
            });
            $this.$wardDom = wardDom;
            return $this;
        }
        return $this.init();
    },
    refresh: function (cityId, callback) {
        var $this = this;
        $this.clean();
        $.getJSON("/ajax/province/city/" + cityId).done(function (result) {
            var wards = {};
            for (var index = 0; index < result.length; index += 1) {
                wards[result[index].id] = result[index];
            }
            $this.render(wards);
            if (callback !== undefined) {
                callback();
            }
        }).fail(function (result) {
            console.log(result);
            $.alert({
                "size": "small",
                "content": "城市加载失败！"
            });
        });
    },
    clean: function () {
        this.$wardDom.html("");
    },
    render: function (wards) {
        for (var key in wards) {
            var ward = wards[key];
            this.$wardDom.append($("<option value='" + ward.name + "'>" + ward.name + "</option>"));
        }
    }
};