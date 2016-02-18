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
using Journalist;
using File = Domain.Models.Entities.File;

namespace FrontendService.Controllers
{
    public class FileController : ApiController
    {
        private readonly IFileRepository _fileRepository;
        private readonly ISessionKeeper _keeper;

        public FileController(IFileRepository fileRepository, ISessionKeeper keeper)
        {
            Require.NotNull(keeper, nameof(ISessionKeeper));
            _keeper = keeper;
            Require.NotNull(fileRepository, nameof(IFileRepository));
            _fileRepository = fileRepository;
        }

        [Route("api/Files/Get/{fileId}")]
        [HttpPost]
        public HttpResponseMessage DownLoadFileFromDataBase(int fileId, [FromBody] string session)
        {
            var sessionId = new Guid(session);
            if (!_keeper.CheckIfSessionExists(sessionId) || _keeper.GetSessionState(sessionId) != SessionState.Ready
                ||_keeper.GetSessionState(sessionId)!=SessionState.Active)
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }

            var returnFile = _fileRepository.GetFileData(fileId);
            if (returnFile == null)
            {
                return new HttpResponseMessage(HttpStatusCode.NoContent);
            }

            var responseMessage = new HttpResponseMessage(HttpStatusCode.OK);
            var stream = new FileStream(HttpContext.Current.Server.MapPath(returnFile.FilePath), FileMode.Open);
            responseMessage.Content = new StreamContent(stream);
            responseMessage.Content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
            responseMessage.Content.Headers.ContentDisposition = new ContentDispositionHeaderValue("attachment")
            {
                FileName = string.Format("{0}.mp3", fileId)
            };


            return responseMessage;
        }

        [Route("api/File/Get/{fileId}")]
        [HttpGet]
        public HttpResponseMessage DownLoadFileFromDataBase1(int fileId)
        {
            var returnFile = _fileRepository.GetFileData(fileId);
            if (returnFile == null)
            {
                return new HttpResponseMessage(HttpStatusCode.NoContent);
            }

            var responseMessage = new HttpResponseMessage(HttpStatusCode.OK);
            var stream = new FileStream(HttpContext.Current.Server.MapPath(returnFile.FilePath), FileMode.Open,
                FileAccess.Read);

            responseMessage.Content = new StreamContent(stream);


            responseMessage.Content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
            responseMessage.Content.Headers.ContentDisposition = new ContentDispositionHeaderValue("attachment")
            {
                FileName = string.Format("{0}.mp3", fileId)
            };

            return responseMessage;
        }


        [Route("api/myfileupload/{fileId}")]
        [HttpPost]
        public string MyFileUpload(int fileId)
        {
            var request = HttpContext.Current.Request;
            var directory = HttpContext.Current.Server.MapPath("~/AudioTrack");

            Directory.CreateDirectory(directory);
            var filePath = HttpContext.Current.Server.MapPath(string.Format("~/AudioTrack/{0}.mp3", fileId));
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