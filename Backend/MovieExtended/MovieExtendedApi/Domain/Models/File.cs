using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    public class File
    {
        public File(string id, string filePath, FileType fileType)
        {
            Id = id;
            FilePath = filePath;
            FileType = fileType;
        }

        protected File()
        {
        }

        public virtual string Id { get; protected set; }

        public virtual string FilePath { get; protected set; }

        public virtual FileType FileType { get; protected set; }
    }
    public enum FileType
    {
        Track,
        Subtitles
    }
}
