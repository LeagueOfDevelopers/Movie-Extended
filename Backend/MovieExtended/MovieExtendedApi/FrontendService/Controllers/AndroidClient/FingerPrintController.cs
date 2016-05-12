﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Domain.FingerPrinting;
using Domain.Models;
using Journalist;

namespace FrontendService.Controllers.AndroidClient
{
    public class FingerPrintController : ApiController
    {
        private readonly IFingerPrintKeeper _fingerPrintKeeper;
        private readonly IFileManager _fileManager;

        public FingerPrintController(IFingerPrintKeeper fingerPrintKeeper)
        {
            Require.NotNull(fingerPrintKeeper,nameof(fingerPrintKeeper));
            _fingerPrintKeeper = fingerPrintKeeper;
        }

        [HttpGet]
        [Route("query/time/exists/{movieId}")]
        public bool ExistsQueryTime(int movieId)
        {
            return _fingerPrintKeeper.IfTimeExists(movieId);
        }

        [HttpPost]
        [Route("query/time/get")]
        public double QueryWithTimeSequence()
        {
            var request = HttpContext.Current.Request;
            var allKeys = request.Files.AllKeys;
            var files = request.Files.GetMultiple(allKeys[0]);
            var audio = files[0];
            if (!_fileManager.CheckExtension(audio.FileName))
                throw new Exception("Not supported");
            
        }

    }
}
