var express = require('express');
var api = require('../internal/api')
var router = express.Router();

/* GET home page. */

router.get('/registry', function(req, res, next) {
  res.render('registry', {ngModule:"MovieExtended.Main"});
});

router.get('/profile', function(req, res, next) {
  res.render('profile', {ngModule: "MovieExtended.Profile"});
});

module.exports = router;
