using Domain.Models.Entities;

namespace Domain.Models.FrontendEntities
{
    public class FrontendLanguage
    {
        public FrontendLanguage(int id, string name, FrontendMovie movie, FrontendFile trackFile)
        {
            Id = id;
            Name = name;
            Movie = movie;
            TrackFile = trackFile;
        }

        public FrontendLanguage(string name, FrontendMovie movie, FrontendFile trackFile)
        {
            Name = name;
            Movie = movie;
            TrackFile = trackFile;
        }

        public FrontendLanguage(Language language)
        {
            Id = language.Id;
            Name = language.Name;
           // Movie = new FrontendMovie(language.Movie);
            TrackFile = new FrontendFile(language.TrackFile);
        }

        public FrontendLanguage()
        {
        }

        public virtual int Id { get; set; }

        public virtual string Name { get; set; }

        public virtual FrontendMovie Movie { get; set; }

        public virtual FrontendFile TrackFile { get; set; }
    }
}