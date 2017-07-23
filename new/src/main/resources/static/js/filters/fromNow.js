'use strict';

/* Filters */
// need load the moment.js to use this filter. 
angular.module('app')
        .filter('fromNow', function () {
            return function (date) {
                if (parseInt(date) > 1400000) {
                    date = (date + '000')*1;
                }
                return moment(date).fromNow();
            }
        }).filter('datetime', function () {
            return function (date) {
                if (parseInt(date) > 1400000) {
                    date = (date + '000')*1;
                }
                return moment(date).format('MMMDo HH:mm');
            }
        });