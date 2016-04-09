using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web;
using System.Xml.Linq;
using Domain.Models;
using Domain.Models.Entities;
using NHibernate.Util;


namespace Infrastructure
{
   public class FileManager:IFileManager
    {
        public FileManager()
        {
            CreateFolders();
            ReadExtensions();
        }

        private void CreateFolders()
        {
            folders.ForEach(pair =>
            {
                if (!Directory.Exists(pair.Value))
                {
                    Directory.CreateDirectory(pair.Value);
                }
            } );
        }

       private void ReadExtensions()
       {
            extensions = new List<string>();
           var document  = XDocument.Load(HttpContext.Current.Server.MapPath(@"~\extensions.xml"));
           var extensionString = document.Root.Value;
            extensionString.Split(',').ForEach(s => extensions.Add(s));

       }
        public HttpResponseMessage GetAnyFile(string filepath)
        {

            var responseMessage = new HttpResponseMessage(HttpStatusCode.OK);
            var stream = new FileStream(filepath, FileMode.Open,
                FileAccess.Read);

            responseMessage.Content = new StreamContent(stream);


            responseMessage.Content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
            responseMessage.Content.Headers.ContentDisposition = new ContentDispositionHeaderValue("attachment")
            {
                FileName = Path.GetFileName(filepath)

            };
            return responseMessage;
        }

       public void DeleteFile(string filepath)
       {
           Directory.Delete(filepath);
       }

       public bool CheckExtension(string filename)
       {
           return extensions.Contains(Path.GetExtension(filename));
       }

       public string CreateTrackPath(string filename)
       {
           var path = folders[FileType.Track];
           return Path.Combine(path, filename);
       }

       public string CreateImagePath(string filename)
       {
            var path = folders[FileType.Poster];
            return Path.Combine(path, filename);
        }

       public string CreateSubtitlePath(string filename)
       {
            var path = folders[FileType.Subtitles];
            return Path.Combine(path, filename);
        }

       public void SaveFileAs(HttpPostedFile file, string filepath)
       {
           file.SaveAs(filepath);
       }


       private Dictionary<FileType, string> folders = new Dictionary<FileType, string>
        {
            {
                FileType.Track, HttpContext.Current.Server.MapPath("~/AudioTrack")
            },
            {
                FileType.Subtitles, HttpContext.Current.Server.MapPath("~/SubTitles")
            } ,
            {
                FileType.Poster, HttpContext.Current.Server.MapPath("~/Poster")
            
            }
        };

       public List<string> extensions;
    }
}
