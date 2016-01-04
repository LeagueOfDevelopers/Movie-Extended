var express = require('express');
var api = require('../internal/api')
var storage = require('../storage/storage');
var router = express.Router();



router.get('/movies' , function(req, res, next) {
  var data = storage.movies.movies_storage;
  res.end(JSON.stringify(data));
  console.log("hey");
});

router.get('/movies/:id' , function(req, res, next) {	
   var data = storage.movies.movies_storage[req.params.id];
  res.end(JSON.stringify(data));
});

router.get('/cinemas' , function(req, res, next) {	
  var data = storage.cinemas;
  res.end(JSON.stringify(data));
  console.log("cinemas");
});

router.get('/cinemas/:id' , function(req, res, next) {	
  var data = storage.cinemas[req.params.id];
  res.end(JSON.stringify(data));
  console.log("cinema");
});



router.post('/movies', function (req, res, next) {
  var data = req.body;
  console.log(data);
  storage.movies.addMovie(data);
  res.end("movie added");

})

module.exports = router;
