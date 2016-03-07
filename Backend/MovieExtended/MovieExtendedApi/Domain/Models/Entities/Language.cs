namespace Domain.Models.Entities
{
    public class Language
    {
        public Language(int id, string name, File trackFile , File subtitles)
        {
            Id = id;
            Name = name;
            TrackFile = trackFile;
            Subtitles = subtitles;
        }

        public Language(string name,  File trackFile , File subtitles)
        {
            Name = name;
            TrackFile = trackFile;
            Subtitles = subtitles;
        }

        protected Language()
        {
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual File TrackFile { get; set; }
        
        public virtual File Subtitles { get; set; }


    }
}