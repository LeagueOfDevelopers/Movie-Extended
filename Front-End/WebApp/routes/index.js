var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('profile');
});

router.get('/getsome' , function(req, res, next) {
});

router.get('/registry', function(req, res, next) {
  res.render('registry');
});
module.exports = router;
