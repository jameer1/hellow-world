'use strict';

app.factory('TreeService', function () {
    const me = this;

    me.populateTreeData = function (data, level, newList, codePropName, childPropName,
        isChild, parent) {
        for (const obj of data) {
            obj.level = level;
            obj.expand = false;
            if (!isChild) {
                obj.collapse = false;
                obj.parent = obj[codePropName];
                
            } else {
                obj.collapse = true;
                obj.parent = parent;
                //obj.projGeneralMstrTO.provisionName = projGeneralMstrTO.provisionName;
            }
            newList.push(obj);
            if (obj[childPropName] && obj[childPropName].length > 0) {
                me.populateTreeData(obj[childPropName], level + 1, newList,
                    codePropName, childPropName, true, obj.parent);
            }
        }
        return newList;

    };

    me.treeItemClick = function (item, collapse, childPropName) {
        if (item[childPropName] && item[childPropName].length > 0) {
            item.expand = !collapse;
            for (const child of item[childPropName]) {
                child.collapse = collapse;
                me.treeItemClick(child, collapse, childPropName);
            }

        }
    }

    me.populateDynamicTreeData = function (data, level, newList, codePropName, childPropName,
        isChild, parentProj) {
        populateDynamicTreeRecursive(data, level, newList, codePropName, childPropName,
            isChild, parentProj, null, 0);
        return newList;
    };

    /**
     * Populate data for dynamic tree.
     * Concept: Generate and assign a uuId to each element for unique identification.
     *          parentUuId i for identifying parent of a element
     *          Maintain new list with empty childs.
     *          
     * @param {*} data 
     * @param {*} level 
     * @param {*} newList 
     * @param {*} codePropName 
     * @param {*} childPropName 
     * @param {*} isChild 
     * @param {*} parentProj 
     * @param {*} parentUuId 
     * @param {*} uuId 
     */
    function populateDynamicTreeRecursive(data, level, newList, codePropName, childPropName,
        isChild, parentProj, parentUuId, uuId) {
        for (const obj of data) {
            obj.level = level;
            obj.collapse = false;
            obj.expand = true;
            obj.edit = true;
            obj.uuId = uuId;
            uuId += 1;
            obj.parentUuId = parentUuId;
            if (!isChild)
                obj.parentProj = obj[codePropName];
            else
                obj.parentProj = parentProj;
            const newObj = {};
            for (const key in obj) {
                if (key !== childPropName)
                    newObj[key] = obj[key];
            }
            newObj[childPropName] = new Array();
            newList.push(newObj);
            if (obj[childPropName] && obj[childPropName].length > 0) {
                for (const child of obj[childPropName]) {
                    uuId = populateDynamicTreeRecursive([child], level + 1, newList, codePropName,
                        childPropName, true, obj.parentProj, obj.uuId, uuId);
                }
            }
        }
        return uuId;
    }

    me.dynamicTreeItemClick = function (mainTree, item, collapse) {
        expandOrCollapseItems(mainTree, [item.uuId], collapse) && (item.expand = !collapse);
    }

    /**
     * Add given element to the tree at proper index
     * Concept: Find index for new element by calling findLastChildIndex method.
     *          Generate and assign a uuId to element and set parentUuId.
     *          Insert the item at the calculated index
     */
    me.addItemToTree = function (mainTree, parentObj, itemToAdd, index) {
        const elementIndex = findLastChildIndex(mainTree, parentObj.uuId, index);
        itemToAdd.uuId = findNextUuId(mainTree);
        itemToAdd.parentUuId = parentObj.uuId;
        const originalArrayCopy = angular.copy(mainTree);
        mainTree = [];
        originalArrayCopy.splice(elementIndex, 0, itemToAdd);
        // if parent not expanded, then expand the tree
        if (!parentObj.expand) {
            me.dynamicTreeItemClick(originalArrayCopy, parentObj, false);
            originalArrayCopy[index].expand = true;
        }
        return originalArrayCopy;
    }

    me.deleteDynamicTreeItem = function (mainTree, index) {
        const parentUuId = mainTree[index].uuId;
        mainTree.splice(index, 1);
        findAndDeleteChildsFromMainTree(mainTree, [parentUuId]);
    }

    me.extractTreeDataForSaving = function (mainTree, childPropName) {
        const fianlData = new Array();
        const mainTreeCopy = angular.copy(mainTree);
        for (const item of mainTreeCopy) {
            if (item.parentUuId === null) {
                const parent = item;
                findAndAddChildsFromMainTree(mainTreeCopy, parent.uuId, parent[childPropName], childPropName);
                fianlData.push(parent);
            }
        }
        return fianlData;
    }

    /**
     * Find and add childs to the given array.
     * Concept: If element uuId equal to given parentUuId then add the child to list and splice 
     *          the array and continue searching for childs.
     *          The second for loop is for adding childs of the given childs array
     *
     * 
     * @param {*} mainTree 
     * @param {*} parentUuId 
     * @param {*} childs 
     * @param {*} childPropName 
     */
    function findAndAddChildsFromMainTree(mainTree, parentUuId, childs, childPropName) {
        for (let startIndex = 0; startIndex < mainTree.length; startIndex++) {
            const element = mainTree[startIndex];
            if (element.parentUuId === parentUuId) {
                childs.push(element);
                mainTree.splice(startIndex, 1);
                findAndAddChildsFromMainTree(mainTree, parentUuId, childs);
                break;
            }
        }
        for (const child of childs) {
            if (child[childPropName]) {
                child[childPropName] = new Array();
                findAndAddChildsFromMainTree(mainTree, child.uuId, child[childPropName], childPropName);
            }
        }
    }

    function findItemByUuId(mainTree, uuIdValue, childPropName) {
        for (const obj of mainTree) {
            if (obj.uuId === uuIdValue)
                return obj;
            findItemByUuId(obj[childPropName], uuIdValue, childPropName);
        }
    }

    function findNextUuId(mainTree) {
        let maxId = 0;
        for (const obj of mainTree) {
            if (obj.uuId > maxId) {
                maxId = obj.uuId;
            }
        }
        return maxId + 1;
    }

    /**
     * Fine the index for new item to add
     * Concept: Traverse the tree from bottom to top, if child found for the given parent then that would be 
     * last child of parent because we are coming from bottom.
     * Now find the lastChild of current child
     * 
     * @param {*} mainTree 
     * @param {*} parentUuId 
     * @param {*} maxIndex 
     */
    function findLastChildIndex(mainTree, parentUuId, maxIndex) {
        if ((mainTree.length - 1) === maxIndex) {
            return mainTree.length;
        }
        let lastChildIndex = null;
        // traverse tree from bottom to top, find last child index
        for (let searchIndex = (mainTree.length - 1); searchIndex > maxIndex; searchIndex--) {
            const mainItem = mainTree[searchIndex];
            if (mainItem.parentUuId === parentUuId) {
                lastChildIndex = findLastChildIndex(mainTree, mainItem.uuId, searchIndex);
                return lastChildIndex;
            }
        }
        // if child not found then return maxIndex+1
        return maxIndex + 1;
    }

    /**
     * Expand or collapse item childs.
     * Concept: Maintain a parent UuId list, if item belongs to any of parentUuIdList then apply collapse to it
     *  and push the item uuId to parentUuIdList
     * @param {*} mainTree MainTree array
     * @param {Array} parentUuIdList 
     * @param {*} collapse 
     */
    function expandOrCollapseItems(mainTree, parentUuIdList, collapse) {
        let expandedItems = false;
        for (let index = 0; index < mainTree.length; index++) {
            const mainItem = mainTree[index];
            if (parentUuIdList.indexOf(mainItem.parentUuId) !== -1) {
                mainItem.expand = !collapse;
                mainItem.collapse = collapse;
                parentUuIdList.push(mainItem.uuId);
                expandedItems = true;
            }
        }
        return expandedItems;
    }

    /**
     * Deletec all childs of deleted item
     * Concept: Find all the childs of given parentUuIdList, if child found then 
     *          add the uuId of child to the list and contnue the recursion
     * @param {*} mainTree 
     * @param {*} param1 
     * @param {*} startIndex 
     */
    function findAndDeleteChildsFromMainTree(mainTree, parentUuIdList) {
        for (let startIndex = 0; startIndex < mainTree.length; startIndex++) {
            const element = mainTree[startIndex];
            if (parentUuIdList.indexOf(element.parentUuId) !== -1) {
                mainTree.splice(startIndex, 1);
                parentUuIdList.push(element.uuId);
                findAndDeleteChildsFromMainTree(mainTree, parentUuIdList);
                break;
            }
        }
    }


    return me;
});
