namespace Domain.Models.Entities
{
    public class Language
    {
        public Language(int id, string name, File trackFile)
        {
            Id = id;
            Name = name;
            TrackFile = trackFile;
        }

        public Language(string name,  File trackFile)
        {
            Name = name;
            TrackFile = trackFile;
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