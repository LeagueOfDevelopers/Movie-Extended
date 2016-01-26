using System;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace FileRepositoryTests
{
    [TestClass]
    public class FileRepositoryTests
    {
        [TestMethod]
        public void SaveFileData()
        {
            var saveFile = new File("id","filePath",FileType.Track);
            
            
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var fileRepository = new FileRepository(session);
                fileRepository.SaveFileData(saveFile);
            }
        }

        [TestMethod]
        public void getFileByFileId()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var fileRepository = new FileRepository(session);
                var str = "418ff8ec-50ae-4872-be51-3ce12d75be6";
                
                var test = fileRepository.GetFileData("418ff8ec-50ae-4872-be51-3ce12d75be6");
            }
        }
    }
}
