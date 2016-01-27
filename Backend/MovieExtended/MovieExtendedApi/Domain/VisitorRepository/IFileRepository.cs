using Domain.Models;

namespace Domain.VisitorRepository
{
   public interface IFileRepository
    {
        void DownLoadFileFromDataBase(int fileId);
        void DeleteFileByFileId(int fileId);
        File GetFileData(int fileId);
        void SaveFileData(File file);
    }
}
