var Movie = function (name, description, poster, tracks) {
  this.name = name;
  this.description = description;
  this.poster = poster;
  this.tracks = tracks;
  return this;
}


module.exports = Movie;
