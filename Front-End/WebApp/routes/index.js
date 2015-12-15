var express = require('express');
var api = require('../internal/api')
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('profile');
});



router.get('/registry', function(req, res, next) {
  res.render('registry');
});
module.exports = router;
