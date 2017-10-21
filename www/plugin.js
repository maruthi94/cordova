
var exec = require('cordova/exec');

var PLUGIN_NAME = 'ThumbnailGenerator';

// var ThumbnailGenerator = {
//   echo: function(phrase, cb) {
//     exec(cb, null, PLUGIN_NAME, 'echo', [phrase]);
//   },
//   getDate: function(cb) {
//     exec(cb, null, PLUGIN_NAME, 'getDate', []);
//   }
// };

var ThumbnailGenerator = {
  bitmap: function(image,options, cb) {
    exec(cb, null, PLUGIN_NAME, 'echo', [image,options]);
  }
};

module.exports = ThumbnailGenerator;
