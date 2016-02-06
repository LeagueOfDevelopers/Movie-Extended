using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Cache;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.VisitorRepository;
using File = Domain.Models.Entities.File;
using NHibernate;


namespace FrontendService.Controllers
{
    public class FileController : ApiController
    {
       // private readonly ISession session;
        private IFileRepository fileRepository;

        public FileController(IFileRepository fileRepository)
        {
           // this.session = session;
            this.fileRepository = fileRepository;
        }

        [Route("api/Files/Get/{fileId}")]
        [HttpGet]
        public HttpResponseMessage DownLoadFileFromDataBase(int fileId)
        {
            var returnFile = fileRepository.GetFileData(fileId);
            if (returnFile == null)
            {
                return new HttpResponseMessage(HttpStatusCode.NoContent);
            }
            else
            {
                var result = new HttpResponseMessage(HttpStatusCode.OK);
                var stream = new FileStream(returnFile.FilePath, FileMode.Open);
                result.Content = new StreamContent(stream);
                result.Content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
                return result;
            }
        }

        [Route("api/myfileupload/{fileId}")]
        [HttpPost]
        public string MyFileUpload(int fileId)
        {
            var request = HttpContext.Current.Request;
            string directory = "C:/filesaudio";
            System.IO.Directory.CreateDirectory(directory);
            var filePath = String.Format("C:/filesaudio/{0}.mp3",fileId);
            using (var fs = new System.IO.FileStream(filePath, System.IO.FileMode.Create))
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
            fileRepository.DeleteFileByFileId(fileId);
        }


        
        [Route("api/Files/All")]
        [HttpGet]
        public IEnumerable<File> GettAllFiles()
        {
           return fileRepository.GetAllFiles();
        }  
    }
}