using System;
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

        public FingerPrintController(IFingerPrintKeeper fingerPrintKeeper , IFileManager fileManager)
        {
            Require.NotNull(fingerPrintKeeper,nameof(fingerPrintKeeper));
            _fingerPrintKeeper = fingerPrintKeeper;
            Require.NotNull(fileManager,nameof(fileManager));
            _fileManager = fileManager;
        }

        [HttpGet]
        [Route("time/exists/{movieId}")]
        public bool ExistsTime(int movieId)
        {
            return _fingerPrintKeeper.IfTimeExists(movieId);
        }

        [HttpPost]
        [Route("time/create")]
        public double QueryWithTimeSequence()
        {
            var request = HttpContext.Current.Request;
            var allKeys = request.Files.AllKeys;
            var files = request.Files.GetMultiple(allKeys[0]);
            var audio = files[0];
            if (!_fileManager.CheckExtension(audio.FileName))
                throw new Exception("Not supported");
            var snippetway =_fileManager.SaveSnippet(audio);
            return _fingerPrintKeeper.QueryWithTimeInformation(snippetway);
        }

        [HttpGet]
        [Route("time/current/{movieId}")]
        public double GetCurrentMovieTime(int movieId)
        {
            return _fingerPrintKeeper.GetMovieTime(movieId);
        }
    }
}
