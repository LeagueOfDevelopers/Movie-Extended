namespace Domain.Models
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
            FileType = fileType;
            FileType = fileType;
        }

        public virtual int Id { get; protected set; }

        public virtual string FilePath { get; protected set; }

        public virtual FileType FileType { get; protected set; }
    }

    public enum FileType
    {
        Track,
        Subtitles
    }
}