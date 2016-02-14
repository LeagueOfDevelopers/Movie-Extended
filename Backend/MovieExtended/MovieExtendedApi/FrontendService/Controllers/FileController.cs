using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.VisitorRepository;
using File = Domain.Models.Entities.File;


namespace FrontendService.Controllers
{
    public class FileController : ApiController
    {
        private readonly ISessionKeeper _keeper;
        private readonly IFileRepository _fileRepository;

        public FileController(IFileRepository fileRepository, ISessionKeeper keeper)
        {
            _keeper = keeper;
            _fileRepository = fileRepository;
        }

        [Route("api/Files/Get/{fileId}/Session/{sessionId}")]
        [HttpGet]
        public HttpResponseMessage DownLoadFileFromDataBase(int fileId,Guid sessionId)
        {
            if (!_keeper.CheckIfSessionExists(sessionId) || _keeper.GetSessionState(sessionId) !=                 SessionState.Active)
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }

            var returnFile = _fileRepository.GetFileData(fileId);
            if (returnFile == null)
            {
                return new HttpResponseMessage(HttpStatusCode.NoContent);
            }

            var result = new HttpResponseMessage(HttpStatusCode.OK);
            var stream = new FileStream(HttpContext.Current.Server.MapPath(returnFile.FilePath), FileMode.Open);
            result.Content = new StreamContent(stream);
            result.Content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");

            return result;
        }

        [Route("api/myfileupload/{fileId}")]
        [HttpPost]
        public string MyFileUpload(int fileId)
        {
            var request = HttpContext.Current.Request;
            string directory = HttpContext.Current.Server.MapPath("~/AudioTrack");
            
            Directory.CreateDirectory(directory);
            var filePath = HttpContext.Current.Server.MapPath(String.Format("~/AudioTrack/{0}.mp3",fileId));
            using (var fs = new FileStream(filePath, FileMode.Create))
            {
                request.InputStream.CopyTo(fs);
                fs.Flush();
                //request.GetBufferedInputStream().CopyToAsync(fs);
            }
            return "uploaded";
        }

        [Route("api/Files/Delete/{fileId}")]
        [HttpPost]
        public void DeleteFileByFileId(int fileId)
        {
            _fileRepository.DeleteFileByFileId(fileId);
        }
        
        [Route("api/Files/All")]
        [HttpGet]
        public IEnumerable<File> GettAllFiles()
        {
           return _fileRepository.GetAllFiles();
        }  
    }
}