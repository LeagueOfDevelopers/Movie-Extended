

var movies = {

  movies_storage: {
  1:{
    Id: 1,
    name: 'Star Wars',
    description: 'Loremdwdwdwdwdwdwdwdw',
    photoUri: 'https://pp.vk.me/c631929/v631929602/52e2/IhLPB0iwKrM.jpg'
  },
  2:{
    Id: 2,
    name: 'Iron Man',
    description: 'loremdkwokdkdwd',
     photoUri: 'https://pp.vk.me/c417531/v417531602/926a/V0vy9RqxvXo.jpg'
  }},


  addMovie: function (data) {
    this.movies_storage['data.Id'] = data;
  }

}

var cinemas = {
 1:{
    Id: 1,
    name: 'Luxor'
},
2:{
    Id: 2,
    name: 'Karo'
}
};


module.exports = {
  movies: movies,
  cinemas: cinemas,
}
