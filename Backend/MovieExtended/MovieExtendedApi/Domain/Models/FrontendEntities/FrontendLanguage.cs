namespace Domain.Models.FrontendEntities
{
    public class FrontendLanguage
    {
        public virtual int Id { get;  set; }

        public virtual string Name { get;  set; }

        public virtual FrontendFile TrackFile { get; set; }

        public virtual FrontendFile Subtitles { get; set; }

    }
}
