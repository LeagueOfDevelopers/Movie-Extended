﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.Repository;
using Journalist;
using static Domain.Models.Entities.FileType;
using File = Domain.Models.Entities.File;

namespace FrontendService.Controllers
{
    public class FileController : ApiController
    {
        private readonly IFileRepository _fileRepository;
        private readonly ISessionKeeper _keeper;
        private readonly IFileManager _fileManager;
        private readonly ILanguageRepository _languageRepository;

        public FileController(IFileRepository fileRepository, ISessionKeeper keeper ,
            IFileManager fileManager , ILanguageRepository languageRepository)
        {
            Require.NotNull(keeper, nameof(ISessionKeeper));
            _keeper = keeper;
            Require.NotNull(fileRepository, nameof(IFileRepository));
            _fileRepository = fileRepository;
            Require.NotNull(fileManager,nameof(IFileManager));
            _fileManager=fileManager;
            Require.NotNull(languageRepository,nameof(ILanguageRepository));
            _languageRepository = languageRepository;
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

        

        

        [Route("api/myfileupload/{fileId}/fileType/{filetype}")]
        [HttpPost]
        public IHttpActionResult MyFileUpload(int fileId , FileType filetype)
        {
            var request = HttpContext.Current.Request;
            string directory;
            string filePath;
            switch (filetype)
            {
                    case FileType.Track :
                {
                    directory= HttpContext.Current.Server.MapPath("~/AudioTrack");
                    filePath= HttpContext.Current.Server.MapPath(string.Format("~/AudioTrack/{0}.mp3", fileId));
                        break;
                }
                    case Subtitles:
                {
                    directory = HttpContext.Current.Server.MapPath("~/SubTitles");
                    filePath = HttpContext.Current.Server.MapPath(string.Format("~/SubTitles/{0}.srt", fileId));

                        break;
                }
                default:
                    return BadRequest("wrong type of file");
            }
            Directory.CreateDirectory(directory);
            var fs = new FileStream(filePath, FileMode.Create);
            request.InputStream.CopyTo(fs);

            fs.Flush();
            //request.GetBufferedInputStream().CopyToAsync(fs);


            return Ok("uploaded");
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

        [Route("save/track/{fileId}")]
        [HttpPost]
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
            return new HttpResponseMessage(HttpStatusCode.Accepted);
        }

        [Route("save/poster/{fileId}")]
        [HttpPost]
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

        [Route("save/subtitle/{fileId}")]
        [HttpPost]
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
