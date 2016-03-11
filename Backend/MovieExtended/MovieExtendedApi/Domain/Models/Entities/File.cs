namespace Domain.Models.Entities
{
    public class File
    {
        public File(int id, string filePath, FileType fileType)
        {
            Id = id;
            FilePath = filePath;
            FileType = fileType;
        }

        protected File()
        {
        }

        public File(string filePath, FileType fileType)
        {
            FilePath = filePath;
            FileType = fileType;
        }

        public virtual int Id { get; protected set; }

        public virtual string FilePath { get;  set; }

        public virtual FileType FileType { get; protected set; }
    }

    public enum FileType
    {
        Track,
        Subtitles,
        Poster
    }
    
}