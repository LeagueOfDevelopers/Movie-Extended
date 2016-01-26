using System;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface IFileRepository
    {
        void DownLoadFileFromDataBase(string fileId);
        void DeleteFileByFileId(string fileId);
        File GetFileData(string fileId);
        void SaveFileData(File file);
    }
}
