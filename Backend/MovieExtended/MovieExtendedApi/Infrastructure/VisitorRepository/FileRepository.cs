using System;
using System.Linq;
using Domain.Models;
using Domain.VisitorRepository;
using Infrastructure.VisitorRepository;
using Journalist;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class FileRepository : IFileRepository
    {
        private readonly SessionProvider _provider;

        public FileRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }

        

       

        public void DownLoadFileFromDataBase(int fileId)
        {
            throw new NotImplementedException();
        }

        public void DeleteFileByFileId(int fileId)
        {
            var session = _provider.GetCurrentSession();
            var deleteFile = session.Query<File>().SingleOrDefault(file => fileId == file.Id);
            if (deleteFile != null)
            {
                System.IO.File.Delete(deleteFile.FilePath.ToString());
                session.Delete(deleteFile);

            }

        }

        

        public File GetFileData(int fileId)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<File>().SingleOrDefault(file => file.Id == fileId);
        }

        public void SaveFileData(File file)
        {
            var session = _provider.GetCurrentSession();
            session.BeginTransaction();
            session.Save(file);
            session.Transaction.Commit();
        }
    }
}