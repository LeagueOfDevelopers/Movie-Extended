namespace Domain.Models.Entities
{
    public class Cinema
    {
        public Cinema(int id, string name, string address , Movie movie )
        {
            Id = id;
            Name = name;
            Address = address;
            Movie = movie;

        }

        public Cinema(string name, string address, Movie movie)
        {
            Name = name;
            Address = address;
            Movie = movie;
        }

        protected Cinema()
        {
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string Address { get; protected set; }

        public virtual Movie Movie { get; set; }
    }
}