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

function sortNumber(a,b) {
    return a - b;
}

function median(values) {

    values.sort( function(a,b) {return a - b;} );

    var half = Math.floor(values.length/2);
    var _mean = null;

    if(values.length % 2)
        _mean = values[half];
    else
        _mean = (values[half-1] + values[half]) / 2.0;

    
    return {
       mean:_mean
    }
}

(function () {
    'use strict';

    angular
        .module('gpmrApp')
        .controller('NormalsByPetController', NormalsByPetController);

    NormalsByPetController.$inject = ['$scope', 'RaceNormal','$log'];



    function NormalsByPetController($scope, RaceNormal, $log) {

        var vm = this;
        vm.normals = null;




        vm.loadAll = function () {

            RaceNormal.query(function (result) {
                vm.normals = {};
                for (var i = 0; i < result.length; i++) {
                    var j = result[i];
                    if (!(j.petCategoryName in vm.normals)) {
                        vm.normals[j.petCategoryName] = {
                            normals: j.normals,
                            normalLoc: j.normalLoc,
                            normalScale: j.normalScale
                        }
                    } else {
                        vm.normals[j.petCategoryName].normals.push(j.normals);
                    }
                }

                Object.keys(vm.normals).forEach(function(key,index) {
                    vm.normals[key].normals
                    // key: the name of the object key
                    // index: the ordinal position of the key within the object
                });

                for (var property in vm.normals) {
                    if (vm.normals.hasOwnProperty(property)) {
                        var normals = vm.normals.sort(sortNumber);

                    }
                }

            });

        };

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
        ];

        vm.loadAll();
    }
})();

