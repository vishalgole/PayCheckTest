var exec = require('cordova/exec');

module.exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'TestPayment', 'coolMethod', [arg0]);
};

module.exports.add = function(arg0, success, error) {
    exec(success, error, 'TestPayment', 'add', [arg0]);
}