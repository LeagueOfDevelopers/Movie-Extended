using System;
using System.Collections.Generic;
using System.IO;
using System.Web;
using Domain.Models;
using Domain.Models.Entities;
using NHibernate.Util;

namespace Infrastructure
{
    class FileManager:IFileManager
    {
        public FileManager()
        {
            CreateFolders();
        }

        public void CreateFolders()
        {
            folders.ForEach(pair =>
            {
                if (!Directory.Exists(pair.Value))
                {
                    Directory.CreateDirectory(pair.Value);
                }
            } );
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
    }
}
