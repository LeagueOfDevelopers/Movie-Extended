using System;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using Extended_Movie.Models;
using Extended_Movie.Visitor_Repository;
using File = Extended_Movie.Models.File;
using NHibernate;
using NHibernate.Linq;
using NHibernate.Util;
using System.IO;

namespace Extended_Movie.Controllers
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

        [Route("api/Files/{fileId}")]
        [HttpGet]
        public HttpResponseMessage ReturnFile(Guid fileId)
        {
            var returnFile = session.Query<File>().SingleOrDefault(file => file.Id == fileId);
            if (returnFile == null)
            {
                return new HttpResponseMessage(HttpStatusCode.NoContent);
            }
            else
            {
                var result = new HttpResponseMessage(HttpStatusCode.OK);
                var stream = new FileStream(returnFile.FilePath.LocalPath, FileMode.Open);
                result.Content = new StreamContent(stream);
                result.Content.Headers.ContentType = new MediaTypeHeaderValue("mp3");
                return result;
            }
        }

        [Route("api/Files/New//{fileId}")]
        [HttpPost]
        public void DownLoadFileToDataBase(HttpPostedFileBase fileUpload, Guid? fileId)
        {

            if (fileUpload != null)
            {
                var directory = @"C:\files\";
                //var fileExt = System.IO.Path.GetExtension(fileUpload.FileName).Substring(1);
                var fileName = Path.GetFileName(fileUpload.FileName);
                fileUpload.SaveAs(Path.Combine(directory, fileName));
            }
        }
    }
}