﻿using System;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Web.Http;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using File = Domain.Models.File;
using NHibernate;


namespace FrontendService.Controllers
{
    public class FileController : ApiController
    {
        private readonly ISession session;
        private FileRepository fileRepository;

        public FileController(ISession session)
        {
            this.session = session;
            fileRepository = new FileRepository();
        }

        [Route("api/Files/Get/{fileId}")]
        [HttpGet]
        public HttpResponseMessage DownLoadFileFromDataBase(Guid fileId)
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
                result.Content.Headers.ContentType = new MediaTypeHeaderValue("mp3");
                return result;
            }
        }

        [Route("api/Files/NewTrack/{fileId}")]
        [HttpPost]
        public void DownLoadFileToDataBase(HttpPostedFileBase fileUpload, Guid? fileId)
        {

            if (fileUpload != null)
            {
                var directory = @"C:\files\";
                var uploadedFile = new File(new Guid?(), directory + fileUpload.FileName, FileType.Track);
                fileRepository.SaveFileData(uploadedFile);
                //var fileExt = System.IO.Path.GetExtension(fileUpload.FileName).Substring(1);
                var fileName = Path.GetFileName(fileUpload.FileName);

                fileUpload.SaveAs(Path.Combine(directory, fileName));
            }
        }

        [Route("api/Files/Delete/{fileId}")]
        [HttpGet]
        public void DeleteFileByFileId(Guid? fileId)
        {
            fileRepository.DeleteFileByFileId(fileId);
        }
    }
}