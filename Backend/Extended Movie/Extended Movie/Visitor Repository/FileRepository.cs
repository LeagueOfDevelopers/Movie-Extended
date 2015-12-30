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
            throw new NotImplementedException();
        }

        public File GetFileData(Guid? fileId)
        {
            return session.Query<File>().SingleOrDefault(file => file.Id == fileId);
        }
    }
}