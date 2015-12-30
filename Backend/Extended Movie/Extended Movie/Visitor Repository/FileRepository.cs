using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using Extended_Movie.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class FileRepository : IFileRepository
    {
        private readonly ISession session;

        public FileRepository()
        {
            
        }
        public HttpResponseMessage GetFileToDataBase(Guid? fileId)
        {
            var downLoadFile = GetFileData(fileId);
            if (downLoadFile != null)
            {

            }
            else
            {
                return new HttpResponseMessage(HttpStatusCode.NoContent);
            }
        }

        public void DownLoadFileFromDataBase(Guid? fileId)
        {
            
        }

        public void DeleteFileByFileId(Guid? fileId)
        {
            var deleteFile = session.Query<File>().SingleOrDefault(file => fileId == file.Id);
            if (deleteFile != null)
            {
                System.IO.File.Delete(deleteFile.FilePath.ToString());
                session.Delete(deleteFile);

            }

        }

        public File GetFileData(Guid? fileId)
        {
            return session.Query<File>().SingleOrDefault(file => file.Id == fileId);
        }

        public void SaveFileData(File file)
        {
            session.BeginTransaction();
            session.SaveOrUpdate(file);
            session.Transaction.Commit();
        }
    }
}