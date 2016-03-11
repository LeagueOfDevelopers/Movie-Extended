using System;
using System.Web.Http;
using Domain.Models;
using Domain.Repository;

namespace FrontendService.Controllers.WebClient
{
    public class QrCodeController : ApiController
    {
        private readonly ICompanyRepository _companyRepository;
        private readonly IMovieRepository _movieRepository;
        private readonly IQrCodeGenerator _qrCodeGenerator;
        private readonly IQrCodeRepository _qrCodeRepository;

        public QrCodeController(IQrCodeGenerator qrCodeGenerator,
            IQrCodeRepository qrCodeRepository,
            ICompanyRepository companyRepository,
            IMovieRepository movieRepository)
        {
            _qrCodeGenerator = qrCodeGenerator;
            _qrCodeRepository = qrCodeRepository;
            _companyRepository = companyRepository;
            _movieRepository = movieRepository;
        }

        [Route("api/QrCode/Generate/{companyId}/{movieId}")]
        [HttpGet]
        public string GetCodeForQr(int companyId, int movieId)
        {
            if (!_companyRepository.Exists(companyId) || !_movieRepository.Exists(movieId))
            {
                // todo change to http response
                throw new Exception("not found");
            }
            var qrCodeFingerprint = _qrCodeGenerator.Generate(movieId, companyId);
            _qrCodeRepository.SaveQrCodeFingeprint(qrCodeFingerprint);
            return qrCodeFingerprint;
        }
    }
}