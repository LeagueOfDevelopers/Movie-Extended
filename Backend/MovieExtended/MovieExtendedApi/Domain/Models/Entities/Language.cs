namespace Domain.Models.Entities
{
    public class Language
    {
        public Language(int id, string name, Movie movie, File trackFile)
        {
            Id = id;
            Name = name;
            Movie = movie;
            TrackFile = trackFile;
        }

        public Language(string name, Movie movie, File trackFile)
        {
            Name = name;
            Movie = movie;
            TrackFile = trackFile;
        }

        protected Language()
        {
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Movie Movie { get; set; }

        public virtual File TrackFile { get; set; }
    }
}