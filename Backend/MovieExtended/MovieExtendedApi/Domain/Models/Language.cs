namespace Domain.Models
{
    public class Language
    {
        public Language(int id, string name, int movieId, int trackFileId)
        {
            Id = id;
            Name = name;
            MovieId = movieId;
            TrackFileId = trackFileId;
        }

        public Language(string name, int movieId, int trackFileId)
        {
            Name = name;
            MovieId = movieId;
            TrackFileId = trackFileId;
        }

        protected Language() { }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual int MovieId { get; set; }

        public virtual int TrackFileId { get; set; }
    }
}
