'use strict';

/**
 * @ngdoc service
 * @name potApp.module
 * @description # Util service which is used in all modules.
 */
app.service('utilservice', function () {

    var me = this;

    /**
     * items - Array of items
     * key - Name of the unique key to find the item
     * newItem - item which to be added
     */
    me.addItemToArray = function (items, key, newItem) {
        const existing = items.find(function (item) {
            return item[key] === newItem[key];
        });
        // To insert latest item, removing the old one
        if (existing)
            items.splice(items.indexOf(existing), 1);
        items.push(newItem);
    };

    /**
     * items - Array of items
     * key - Name of the unique key to find the item
     * newItem - item which to be added
     */
    me.addItemKeyValueToArray = function (items, key, newItem) {
        const existing = items.find(function (item) {
            return item === newItem[key];
        });
        // To insert latest item, removing the old one
        if (existing)
            items.splice(items.indexOf(existing), 1);
        items.push(newItem[key]);
    };

});