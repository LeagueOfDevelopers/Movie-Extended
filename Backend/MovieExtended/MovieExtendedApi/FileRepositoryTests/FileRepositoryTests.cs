using System;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using Infrastructure.VisitorRepository;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using NHibernate.Mapping.ByCode;

namespace FileRepositoryTests
{
    [TestClass]
    public class FileRepositoryTests
    {
        [TestMethod]
        public void SaveFileData()
        {
            var saveFile = new File(Convert.ToInt32(new Random()), "filePath",FileType.Track);
            
            
            var provider = new SessionProvider();
            provider.OpenSession();
            
                var fileRepository = new FileRepository(provider);
                fileRepository.SaveFileData(saveFile);
            
        }

        [TestMethod]
        public void getFileByFileId()
        {
            //var Id=Convert.ToInt32(new Random());
            var provider = new SessionProvider();
            var saveFile = new File(10,"d",FileType.Track);
            provider.OpenSession();
            
                var fileRepository = new FileRepository(provider);
                fileRepository.SaveFileData(saveFile);
                var test = fileRepository.GetFileData(10);
            
        }
    }
}
