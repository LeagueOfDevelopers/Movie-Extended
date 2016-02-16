using System.Collections.Generic;
using System.Web.Http;
using Domain.Models.Entities;
using Domain.VisitorRepository;

namespace FrontendService.Controllers.WebClient
{
    public class CinemaController : ApiController
    {
        private readonly ICinemaRepository _cinemaRepository;

        public CinemaController(ICinemaRepository cinemaRepository)
        {
            _cinemaRepository = cinemaRepository;
        }

        [Route("api/Cinema/All")]
        [HttpGet]
        public IEnumerable<Cinema> GetAllCinemas()
        {
            return _cinemaRepository.GetAllCinemas();
        }

        [Route("api/Cinema/New")]
        [HttpPost]
        public void SaveNewCinema([FromBody] Cinema cinema)
        {
            _cinemaRepository.SaveCinemaData(cinema);
        }

        [Route("api/Cinema/GetByCinemaId/{cinemaId}")]
        [HttpGet]
        public Cinema GetCinemaByCinemaId(int cinemaId)
        {
            return _cinemaRepository.GetCinemaByCinemaId(cinemaId);
        }

        [Route("api/Cinema/GetCinemaByCompanyId/{companyId}")]
        [HttpGet]
        public IEnumerable<Cinema> GetCinemaByCompanyId(int companyId)
        {
            return _cinemaRepository.GetCinemaByCompanyId(companyId);
        }

        [Route("api/Cinema/DeleteByCinemaId/{cinemaId}")]
        [HttpPost]
        public void DeleteCinemaByCinemaId(int cinemaId)
        {
            _cinemaRepository.DeleteCinemaByCinemaId(cinemaId);
        }

        [Route("api/Cinema/DeleteByCompany/{companyId}")]
        [HttpPost]
        public void DeleteCinemaByCompanyId(int companyId)
        {
            _cinemaRepository.DeleteCinemaByCompanyId(companyId);
        }
    }
}