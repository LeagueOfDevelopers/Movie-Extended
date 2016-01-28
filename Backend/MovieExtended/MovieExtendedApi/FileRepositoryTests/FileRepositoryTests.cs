using System;
using Domain.Models;
using Infrastructure.VisitorRepository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

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
            using (var session = provider.GetCurrentSession())
            {
                var fileRepository = new FileRepository(session);
                fileRepository.SaveFileData(saveFile);
            }
        }

        [TestMethod]
        public void GetFileByFileId()
        {
            //var Id=Convert.ToInt32(new Random());
            var provider = new SessionProvider();
            var saveFile = new File(10,"d",FileType.Track);
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var fileRepository = new FileRepository(session);
                fileRepository.SaveFileData(saveFile);
                var test = fileRepository.GetFileData(10);
            }
        }
    }
}
