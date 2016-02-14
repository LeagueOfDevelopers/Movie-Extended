using Domain.Models.Entities;
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
            var saveFile = new File(@"C:/filesaudio/77.mp3", FileType.Track);
            
            var provider = new SessionProvider();
            provider.OpenSession();
            
            var fileRepository = new FileRepository(provider);
            fileRepository.SaveFileData(saveFile);
        }

        [TestMethod]
        public void GetFileByFileId()
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
