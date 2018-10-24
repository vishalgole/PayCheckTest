var exec = require('cordova/exec');

module.exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'TestPayment', 'coolMethod', [arg0]);
};

module.exports.add = function(arg0, success, error) {
    exec(success, error, 'TestPayment', 'add', [arg0]);
}

module.exports.triggerWid = function(arg0, success, error) {
    exec(success, error, 'TestPayment', 'triggerWid', [arg0]);
}

module.exports.triggerPay = function(arg0, success, error) {
    exec(success, error, 'TestPayment', 'triggerPay', [arg0]);
}