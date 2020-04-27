function $(args) {
    return document.querySelector(args);
}
function getElement(args) {
    return $(args);
}
function createElement(type, parent) {
    var BODY = $("body");
    var ele = document.createElement(type);
    if (parent) {
        parent.appendChild(ele);
    }
    else {
        BODY.appendChild(ele);
    }
    return ele;
}
function deleteElement(element) {
    try {
        var PARENT = element.parentElement;
        PARENT.removeChild(element);
    }
    catch (e) {
        console.error(e.message);
    }
}
function getOffsetLeft(elem) {
    var offsetLeft = 0;
    do {
        if (!isNaN(elem.offsetLeft)) {
            offsetLeft += elem.offsetLeft;
        }
    } while ((elem = elem.offsetParent));
    return offsetLeft;
}
function getOffsetTop(elem) {
    var offsetTop = 0;
    do {
        if (!isNaN(elem.offsetTop)) {
            offsetTop += elem.offsetTop;
        }
    } while ((elem = elem.offsetParent));
    return offsetTop;
}
