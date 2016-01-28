using System.IO;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Web.Http;
using Domain.Models;
using Domain.VisitorRepository;
using NHibernate;
using File = Domain.Models.File;

namespace FrontendService.Controllers
{
    public class FileController : ApiController
    {
        private readonly ISession _session;
        private readonly IFileRepository _fileRepository;

        public FileController(ISession session,IFileRepository fileRepository)
        {
            _session = session;
            _fileRepository = fileRepository;
        }

        [Route("api/Files/Get/{fileId}")]
        [HttpGet]
        public HttpResponseMessage DownLoadFileFromDataBase(int fileId)
        {
            var returnFile = _fileRepository.GetFileData(fileId);
            if (returnFile == null)
            {
                return new HttpResponseMessage(HttpStatusCode.NoContent);
            }
            var result = new HttpResponseMessage(HttpStatusCode.OK);
            var stream = new FileStream(returnFile.FilePath, FileMode.Open);
            result.Content = new StreamContent(stream);
            result.Content.Headers.ContentType = new MediaTypeHeaderValue("mp3");
            return result;
        }

        [Route("api/Files/NewTrack/{fileId}")]
        [HttpPost]
        public void DownLoadFileToDataBase(HttpPostedFileBase fileUpload, int fileId)
        {
            if (fileUpload != null)
            {
                var directory = @"C:\files\";
                var uploadedFile = new File(7, directory + fileUpload.FileName, FileType.Track);
                _fileRepository.SaveFileData(uploadedFile);
                //var fileExt = System.IO.Path.GetExtension(fileUpload.FileName).Substring(1);
                var fileName = Path.GetFileName(fileUpload.FileName);

                fileUpload.SaveAs(Path.Combine(directory, fileName));
            }
        }

        [Route("api/Files/Delete/{fileId}")]
        [HttpGet]
        public void DeleteFileByFileId(int fileId)
        {
            _fileRepository.DeleteFileByFileId(fileId);
        }
    }
}