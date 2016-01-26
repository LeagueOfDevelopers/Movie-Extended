using System;

namespace Domain.Models
{
    public class Language
    {
        public Language(string id, string name, string movieId, string trackFileId)
        {
            Id = id;
            Name = name;
            MovieId = movieId;
            TrackFileId = trackFileId;
        }

        protected Language() { }

        public virtual string Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string MovieId { get; set; }

        public virtual string TrackFileId { get; set; }
    }
}
