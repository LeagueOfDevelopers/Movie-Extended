using System.Collections.Generic;
using Domain.Models.Entities;

namespace Domain.Repository
{
    public interface IFileRepository
    {
        void DeleteFileByFileId(int fileId);
        File GetFileData(int fileId);
        void SaveFileData(File file);
        IEnumerable<File> GetAllFiles();
        void Update(int fileId, string filePath);
    }
}