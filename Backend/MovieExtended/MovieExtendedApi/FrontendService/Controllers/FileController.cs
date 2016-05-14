using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Domain.Models;
using Domain.Repository;
using Journalist;
using File = Domain.Models.Entities.File;

namespace FrontendService.Controllers
{
    public class FileController : ApiController
    {
        private readonly IFileRepository _fileRepository;
        private readonly ISessionKeeper _keeper;
        private readonly IFileManager _fileManager;

        public FileController(IFileRepository fileRepository, ISessionKeeper keeper ,
            IFileManager fileManager )
        {
            Require.NotNull(keeper, nameof(ISessionKeeper));
            _keeper = keeper;
            Require.NotNull(fileRepository, nameof(IFileRepository));
            _fileRepository = fileRepository;
            Require.NotNull(fileManager,nameof(IFileManager));
            _fileManager=fileManager;
        }
        
        [Route("file/{fileId}/token/{sessionId}")]
        [HttpGet]
        public HttpResponseMessage GetAnyFile(int fileId , Guid sessionId)
        {
            if (!_keeper.CheckIfSessionExists(sessionId)) return  new HttpResponseMessage(HttpStatusCode.Unauthorized);
            var file = _fileRepository.GetFileData(fileId);
            if (file==null) return new HttpResponseMessage(HttpStatusCode.NoContent);
            return _fileManager.GetAnyFile(file.FilePath);
        }

        [Route("file/delete/{fileId}")]
        [HttpDelete]

        public void DeleteFileByFileId(int fileId)
        {
            _fileRepository.DeleteFileByFileId(fileId);
        }

        [Route("file/all")]
        [HttpGet]
        public IEnumerable<File> GettAllFiles()
        {
            return _fileRepository.GetAllFiles();
        }

        [Route("track/save/{fileId}")]
        [HttpPut]
        public HttpResponseMessage SaveTrack(int fileId)
        {
            var request = HttpContext.Current.Request;
            var allKeys = request.Files.AllKeys;
            var files = request.Files.GetMultiple(allKeys[0]);
            var audio = files[0];
            if (!_fileManager.CheckExtension(audio.FileName))
                return new HttpResponseMessage(HttpStatusCode.NotAcceptable);
            var filepath = _fileManager.CreateTrackPath(audio.FileName);
            _fileRepository.Update(fileId,filepath);
            _fileManager.SaveFileAs(audio,filepath);
            return new HttpResponseMessage(HttpStatusCode.OK);
        }

        [Route("poster/save/{fileId}")]
        [HttpPut]
        public HttpResponseMessage SaveImage(int fileId)
        {
            var request = HttpContext.Current.Request;
            var allKeys = request.Files.AllKeys;
            var files = request.Files.GetMultiple(allKeys[0]);
            var poster = files[0];
            if (!_fileManager.CheckExtension(poster.FileName))
                return new HttpResponseMessage(HttpStatusCode.NotAcceptable);
            var filepath = _fileManager.CreateImagePath(poster.FileName);
            _fileRepository.Update(fileId, filepath);
            _fileManager.SaveFileAs(poster, filepath);
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [Route("subtitle/save/{fileId}")]
        [HttpPut]
        public HttpResponseMessage SaveSubtitle(int fileId)
        {
            var request = HttpContext.Current.Request;
            var allKeys = request.Files.AllKeys;
            var files = request.Files.GetMultiple(allKeys[0]);
            var subtitle = files[0];
            if (!_fileManager.CheckExtension(subtitle.FileName))
                return new HttpResponseMessage(HttpStatusCode.NotAcceptable);
            var filepath = _fileManager.CreateSubtitlePath(subtitle.FileName);
            _fileRepository.Update(fileId, filepath);
            _fileManager.SaveFileAs(subtitle, filepath);
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }
    }

    }
