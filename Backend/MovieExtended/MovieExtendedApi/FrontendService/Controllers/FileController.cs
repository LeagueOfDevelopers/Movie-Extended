using System;
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
        private readonly IMovieRepository _movieRepository;

        public FileController(IFileRepository fileRepository, ISessionKeeper keeper , IMovieRepository movieRepository)
        {
            Require.NotNull(keeper, nameof(ISessionKeeper));
            _keeper = keeper;
            Require.NotNull(fileRepository, nameof(IFileRepository));
            _fileRepository = fileRepository;
            Require.NotNull(movieRepository , nameof(IMovieRepository));
            _movieRepository = movieRepository;
        }
        
        [Route("file/get/{fileId}/token/{sessionId}")]
        [HttpGet]
        public HttpResponseMessage Track(int fileId , Guid sessionId)
        {
            if (_keeper.CheckIfSessionExists(sessionId))
            return GetAnyFile(fileId);
            return new HttpResponseMessage
            {
                StatusCode = HttpStatusCode.Unauthorized
            };
        }

        

        private HttpResponseMessage GetAnyFile(int fileId)
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
                FileName = Path.GetFileName(returnFile.FilePath) 

            };
            return responseMessage;
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

        [Route("save/track/{languageId}")]
        [HttpPost]
        public IHttpActionResult SaveTrack(int languageId)
        {
            var request = HttpContext.Current.Request;
            var allKeys = request.Files.AllKeys;
            var files = request.Files.GetMultiple(allKeys[0]);
            var audio = files[0];
            if (extensions.Contains(Path.GetExtension(audio.FileName)))
            {
                var directory = HttpContext.Current.Server.MapPath("~/AudioTrack");
                var filePath = Path.Combine(directory, audio.FileName);
                audio.SaveAs(filePath);
                return Ok("uploaded");
            }
        else
            {
                return StatusCode(HttpStatusCode.NotAcceptable);
            }
        }

        public string[] extensions  = { ".mp3", ".srt", ".wav", ".jpg", "jpeg", "png", "sub" };

    }

    }
