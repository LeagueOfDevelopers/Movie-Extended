var express = require('express');
var api = require('../internal/api')
var storage = require('../storage/storage');
var router = express.Router();



router.get('/movies' , function(req, res, next) {
  var data = storage.movies.getMovies();
  res.end(JSON.stringify(data));
  console.log("hey");
});

router.post('/movies', function (req, res, next) {
  var data = req.body;
  console.log(data);
  storage.movies.addMovie(data);
  res.end("movie added");

})

module.exports = router;
