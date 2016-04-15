using System.Collections.Generic;
using System.Web.Http;
using Domain.Models.Entities;
using Domain.Repository;

namespace FrontendService.Controllers.WebClient
{
    public class CinemaController : ApiController
    {
        private readonly ICinemaRepository _cinemaRepository;

        public CinemaController(ICinemaRepository cinemaRepository)
        {
            _cinemaRepository = cinemaRepository;
        }

        [Route("cinema/all")]
        [HttpGet]
        public IEnumerable<Cinema> GetAllCinemas()
        {
            return _cinemaRepository.GetAllCinemas();
        }

        [Route("cinema/new/{companyId}")]
        [HttpPost]
        public void SaveNewCinema([FromBody] Cinema cinema)
        {
            _cinemaRepository.SaveCinemaData(cinema);
        }

        [Route("cinema/get/{cinemaId}")]
        [HttpGet]
        public Cinema GetCinemaByCinemaId(int cinemaId)
        {
            return _cinemaRepository.GetCinemaByCinemaId(cinemaId);
        }

        //[Route("cinema/get/{companyId}")]
        //[HttpGet]
        //public IEnumerable<Cinema> GetCinemaByCompanyId(int companyId)
        //{
        //    return _cinemaRepository.GetCinemaByCompanyId(companyId);
        //}

        [Route("cinema/delete/{cinemaId}")]
        [HttpPost]
        public void DeleteCinemaByCinemaId(int cinemaId)
        {
            _cinemaRepository.DeleteCinemaByCinemaId(cinemaId);
        }

        //[Route("cinema/DeleteByCompany/{companyId}")]
        //[HttpPost]
        //public void DeleteCinemaByCompanyId(int companyId)
        //{
        //    _cinemaRepository.DeleteCinemaByCompanyId(companyId);
        //}

        [Route("create/movie/{cinemaId}")]
        [HttpPost]
        public void CreateMovie([FromBody] Movie movie, int cinemaId)
        {
            
        }
    }
}