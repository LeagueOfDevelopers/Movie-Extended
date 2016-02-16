using System.Reflection;
using Domain.Models.Entities;

namespace Domain.Models.FrontendEntities
{
    public class FrontendFile
    {
        public FrontendFile(int id, string filePath, FileType fileType)
        {
            Id = id;
            FilePath = filePath;
            FileType = fileType;
        }

        protected FrontendFile()
        {
        }

        public FrontendFile(string filePath, FileType fileType)
        {
            FilePath = filePath;
            FileType = fileType;
        }

        public FrontendFile(File file)
        {
            Id = file.Id;
            FilePath = file.FilePath;
            FileType = file.FileType;
        }

        public virtual int Id { get;  set; }

        public virtual string FilePath { get; protected set; }

        public virtual FileType FileType { get; protected set; }
    }

    
}
