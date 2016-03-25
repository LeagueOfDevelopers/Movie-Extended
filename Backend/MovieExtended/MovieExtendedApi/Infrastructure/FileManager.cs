using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Web;
using System.Xml;
using System.Xml.Linq;
using System.Xml.Serialization;
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

       private void ReadExtensions()
       {
            extensions = new List<string>();
           var document  = XDocument.Load(HttpContext.Current.Server.MapPath(@"\extensions.xml"));
           var extensionString = document.Root.Value;
            extensionString.Split(',').ForEach(s => extensions.Add(s));

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
