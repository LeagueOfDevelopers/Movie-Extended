using System;

namespace Domain.Models
{
    public class Language
    {
        public Language(Guid? id, string name, Guid movieId, Guid trackFileId)
        {
            Id = id;
            Name = name;
            MovieId = movieId;
            TrackFileId = trackFileId;
        }

        protected Language() { }

        public virtual Guid? Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Guid MovieId { get; set; }

        public virtual Guid TrackFileId { get; set; }
    }
}
