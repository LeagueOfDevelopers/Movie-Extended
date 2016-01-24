using System;
using System.Linq;
using Domain.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class FileRepository : IFileRepository
    {
        private readonly ISession _session;

        public FileRepository(ISession session)
        {
            _session = session;
        }
        
        
        public void DownLoadFileFromDataBase(Guid? fileId)
        {
            
        }

        public void DeleteFileByFileId(Guid? fileId)
        {
            var deleteFile = _session.Query<File>().SingleOrDefault(file => fileId == file.Id);
            if (deleteFile != null)
            {
                System.IO.File.Delete(deleteFile.FilePath.ToString());
                _session.Delete(deleteFile);

            }

        }

        

        public File GetFileData(Guid? fileId)
        {
            return _session.Query<File>().SingleOrDefault(file => file.Id == fileId);
        }

        public void SaveFileData(File file)
        {
            _session.BeginTransaction();
            _session.Save(file);
            _session.Transaction.Commit();
        }
    }
}