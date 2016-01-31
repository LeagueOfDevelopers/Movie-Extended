using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

        public File(string FilePath, FileType fileType)
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
