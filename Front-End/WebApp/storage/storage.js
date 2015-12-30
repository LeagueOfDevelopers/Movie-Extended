

var movies = {

  movies_storage: [{
    Id: 1,
    name: 'Star Wars',
    description: 'Loremdwdwdwdwdwdwdwdw',
    photoUri: 'https://pp.vk.me/c631929/v631929602/52e2/IhLPB0iwKrM.jpg'
  },
  {
    Id: 2,
    name: 'Iron Man',
    description: 'loremdkwokdkdwd',
     photoUri: 'https://pp.vk.me/c417531/v417531602/926a/V0vy9RqxvXo.jpg'
  }],

  getMovies: function () {
    return this.movies_storage;
  },

  addMovie: function (data) {
    this.movies_storage.push(data);
  }

}

var cinemas = [{},{}]


module.exports = {
  movies: movies,
  cinemas: cinemas,
}
