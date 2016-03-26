﻿using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using Domain.Models.Entities;
using Domain.Repository;
using Journalist;
using NHibernate.Linq;

namespace Infrastructure.Repository
{
    public class FileRepository : IFileRepository
    {
        private readonly SessionProvider _provider;

        public FileRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }


        

        public void DeleteFileByFileId(int fileId)
        {
            var session = _provider.GetCurrentSession();
            var deleteFile = session.Query<File>().SingleOrDefault(file => fileId == file.Id);
            if (deleteFile != null)
            {
                System.IO.File.Delete(deleteFile.FilePath);
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

        public IEnumerable<File> GetAllFiles()
        {
            var session = _provider.GetCurrentSession();
            return session.Query<File>();
        }

        public void Update(int fileId, string filePath)
        {
            var session = _provider.GetCurrentSession();
            var fileupdate = session.Query<File>().SingleOrDefault(file1 =>file1.Id==fileId);
            fileupdate.FilePath = filePath;
            session.Update(fileupdate);



        }
    }
}