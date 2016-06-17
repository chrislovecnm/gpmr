/**
 * Created by clove on 6/16/16.
 */
var randomColor = (function(){
    var golden_ratio_conjugate = 0.618033988749895;
    var h = Math.random();

    var hslToRgb = function (h, s, l){
        var r, g, b;

        if(s == 0){
            r = g = b = l; // achromatic
        }else{
            function hue2rgb(p, q, t){
                if(t < 0) t += 1;
                if(t > 1) t -= 1;
                if(t < 1/6) return p + (q - p) * 6 * t;
                if(t < 1/2) return q;
                if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;
                return p;
            }

            var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            var p = 2 * l - q;
            r = hue2rgb(p, q, h + 1/3);
            g = hue2rgb(p, q, h);
            b = hue2rgb(p, q, h - 1/3);
        }

        return '#'+Math.round(r * 255).toString(16)+Math.round(g * 255).toString(16)+Math.round(b * 255).toString(16);
    };

    return function(){
        h += golden_ratio_conjugate;
        h %= 1;
        return hslToRgb(h, 0.5, 0.60);
    };
})();

(function () {
    'use strict';

    angular
        .module('gpmrApp')
        .controller('NormalsByPetController', NormalsByPetController);

    NormalsByPetController.$inject = ['$scope', 'RaceNormal','$log'];



    function NormalsByPetController($scope, RaceNormal, $log) {

        var vm = this;
        vm.data = [];
        vm.normals = {};
        vm.options = {
            chart: {
                type: 'boxPlotChart',
                height: 450,
                margin: {
                    top: 20,
                    right: 20,
                    bottom: 60,
                    left: 40
                },
                color: [],
                x: function (d) {
                    return d.label;
                },
                // y: function(d){return d.values.Q3;},
                maxBoxWidth: 75,
                yDomain: [-10, 0]
            }
        };

        vm.loadAll = function () {

            RaceNormal.query(function (result) {
                for (var i = 0; i < result.length; i++) {
                    var j = result[i];
                    if (!(j.petCategoryName in vm.normals)) {
                        vm.normals[j.petCategoryName] = {
                            normals: j.normals,
                            normalLoc: j.normalLoc,
                            normalScale: j.normalScale
                        }
                    } else {
                        vm.normals[j.petCategoryName].normals.concat(j.normals);
                    }
                }

                Object.keys(vm.normals).forEach(function(key,index) {
                    var a = vm.normals[key].normals;
                    var nums = stats(vm.normals[key].normals).sort();
                    $log.log("stats");
                    $log.log(nums);
                    var _outliers = nums.clone().findOutliers();
                    $log.log("outliers");
                    $log.log(_outliers.toArray());
                    var _clean_nums = nums.removeOutliers();
                    $log.log("clean");
                    $log.log(_clean_nums.toArray());
                    var _max = _clean_nums.max();
                    vm.data.push({
                       label: key,
                        values: {
                            Q1:  _clean_nums.q1(),
                            Q2:  _clean_nums.median(),
                            Q3:  _clean_nums.q3(),
                            whisker_low: _clean_nums.min(),
                            whisker_high: _max,
                            outliers: _outliers.toArray()
                        }
                    });
                    vm.options.chart.color.push(randomColor());
                    if (vm.options.chart.yDomain[1] < _max + 10) {
                        vm.options.chart.yDomain[1] = Math.ceil((_max + 10));
                    }
                });

                $scope.options = vm.options;
                $scope.data = vm.data;
            });

        };

        /*
        $scope.options = {
                chart: {
                    type: 'boxPlotChart',
                    height: 450,
                    margin: {
                        top: 20,
                        right: 20,
                        bottom: 60,
                        left: 40
                    },
                    color: [randomColor(), randomColor(), randomColor(), randomColor(), randomColor()],
                    //color: ['darkblue', 'darkorange', 'green', 'darkred', 'darkviolet'],
                    x: function (d) {
                        return d.label;
                    },
                    // y: function(d){return d.values.Q3;},
                    maxBoxWidth: 75,
                    yDomain: [0, 500]
                }
            };


        $scope.data = [
            {
                label: "Sample A",
                values: {
                    Q1: 180,
                    Q2: 200,
                    Q3: 250,
                    whisker_low: 115,
                    whisker_high: 400,
                    outliers: [50, 100, 425]
                }
            },
            {
                label: "Sample B",
                values: {
                    Q1: 300,
                    Q2: 350,
                    Q3: 400,
                    whisker_low: 225,
                    whisker_high: 425,
                    outliers: [175, 450, 480]
                }
            },
            {
                label: "Sample C",
                values: {
                    Q1: 100,
                    Q2: 200,
                    Q3: 300,
                    whisker_low: 25,
                    whisker_high: 400,
                    outliers: [450, 475]
                }
            },
            {
                label: "Sample D",
                values: {
                    Q1: 75,
                    Q2: 100,
                    Q3: 125,
                    whisker_low: 50,
                    whisker_high: 300,
                    outliers: [450]
                }
            },
            {
                label: "Sample E",
                values: {
                    Q1: 325,
                    Q2: 400,
                    Q3: 425,
                    whisker_low: 225,
                    whisker_high: 475,
                    outliers: [50, 100, 200]
                }
            }
        ];*/

        vm.loadAll();
        
    }
})();

