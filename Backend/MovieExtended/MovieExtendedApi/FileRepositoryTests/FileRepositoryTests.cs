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
            var saveFile = new File(Guid.NewGuid(),"filePath",FileType.Track);
            
            
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var fileRepository = new FileRepository(session);
                fileRepository.SaveFileData(saveFile);
            }
        }
    }
}
