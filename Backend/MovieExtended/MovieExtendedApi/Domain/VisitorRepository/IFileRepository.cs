using System;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface IFileRepository
    {
        void DownLoadFileFromDataBase(Guid? fileId);
        void DeleteFileByFileId(Guid? fileId);
        File GetFileData(Guid? fileId);
        void SaveFileData(File file);
    }
}
