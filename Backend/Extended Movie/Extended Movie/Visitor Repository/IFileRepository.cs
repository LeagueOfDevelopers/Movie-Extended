using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Extended_Movie.Models;

namespace Extended_Movie.Visitor_Repository
{
    interface IFileRepository
    {
        void DownLoadFileFromDataBase(Guid? fileId);
        void DeleteFileByFileId(Guid? fileId);
        File GetFileData(Guid? fileId);
        void SaveFileData(File file);
    }
}
