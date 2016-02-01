namespace Domain.Models.Entities
{
    public class Movie
    {
        public Movie(int id, string name, int cinemaId)
        {
            Id = id;
            Name = name;
            CinemaId = cinemaId;
        }

        public Movie(string name, int cinemaId)
        {
            Name = name;
            CinemaId = cinemaId;
        }
        
            

        protected Movie()
        {
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual int CinemaId { get; protected set; }
    }
}
