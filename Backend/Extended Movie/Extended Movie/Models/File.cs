using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Extended_Movie.Models
{
    public class File
    {
        public File(Guid? id, Uri filePath, FileType fileType)
        {
            Id = id;
            FilePath = filePath;
            FileType = fileType;
        }

        protected File()
        {
        }

        public virtual Guid? Id { get; protected set; }

        public virtual Uri FilePath { get; protected set; }

        public virtual FileType FileType { get; protected set; }
    }
    public enum FileType
    {
        Track,
        Subtitles
    }
}