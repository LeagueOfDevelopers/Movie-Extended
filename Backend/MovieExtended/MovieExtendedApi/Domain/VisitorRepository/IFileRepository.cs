using System.Collections.Generic;
using Domain.Models;
using Domain.Models.Entities;

namespace Domain.VisitorRepository
{
   public interface IFileRepository
    {
        void DownLoadFileFromDataBase(int fileId);
        void DeleteFileByFileId(int fileId);
        File GetFileData(int fileId);
        void SaveFileData(File file);
       IEnumerable<File> GetAllFiles();
    }
}
