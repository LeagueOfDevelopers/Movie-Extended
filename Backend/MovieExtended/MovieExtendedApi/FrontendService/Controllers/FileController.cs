using System;
using System.Collections.Generic;
using System.Configuration.Internal;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Http.Results;
using Domain.Models;
using Domain.Models.Entities;

using Domain.VisitorRepository;
using Journalist;
using Journalist.Options;
using NHibernate.Util;
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

        [Route("file/{fileId}")]
        [HttpPut]
        public HttpResponseMessage Track(int fileId , [FromBody] Guid sessionId)
        {
            if (_keeper.CheckIfSessionExists(sessionId))
            return GetAnyFile(fileId);
            return new HttpResponseMessage()
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
                FileName = Path.GetDirectoryName(returnFile.FilePath) + '.' + Path.GetExtension(returnFile.FilePath)

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



        [Route("image/set/{movieId}")]
        [HttpPut]
        public IHttpActionResult SetImage(int movieId)
        {
            var request = HttpContext.Current.Request;
            var directory = HttpContext.Current.Server.MapPath("~/Posters");
            var filePath = HttpContext.Current.Server.MapPath(string.Format("~/Posters/{0}.jpg", movieId));
           // _movieRepository.SetPoster(movieId , );
            Directory.CreateDirectory(directory);
            using (var fs = new FileStream(filePath, FileMode.Create))
            {
                request.InputStream.CopyTo(fs);
                
                fs.Flush();
                
            }

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
        public string SaveTrack(int fileId)
        {
            var request = HttpContext.Current.Request;
            var file = request.Files.AllKeys;
            var audio = request.Files.GetMultiple(file[0]);
            //audio.ForEach(postedFile => postedFile.SaveAs(
            //    Path.Combine(HttpContext.Current.Server.MapPath("~/AudioTrack"),postedFile.FileName)) );
            var postFile = audio[0];
            return Path.GetExtension(postFile.FileName);
        }

       

        }

    }
