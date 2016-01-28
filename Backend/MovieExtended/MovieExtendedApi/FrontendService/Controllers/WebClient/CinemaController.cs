using System;
using System.Collections.Generic;
using System.Web.Http;
using System.Web.Routing;
using Domain.Models;
using Domain.VisitorRepository;
using Extended_Movie.Visitor_Repository;
using Infrastructure.VisitorRepository;
using Newtonsoft.Json;

namespace FrontendService.Controllers.WebClient
{
    public class CinemaController:ApiController
    {
        private readonly ICinemaRepository cinemaRepository;

        public CinemaController(ICinemaRepository cinemaRepository)

        {

            this.cinemaRepository = cinemaRepository;

        }

        [Route("api/Cinema/All")]
        [HttpGet]

        IEnumerable<Cinema> GetAllCinemas()
        {
            return cinemaRepository.GetAllCinemas();
        }

        [Route("api/Cinema/New/{json}")]
        [HttpGet]
        public void SaveNewCinema(string json)
        {
            var newCinema = JsonConvert.DeserializeObject<Cinema>(json);
            cinemaRepository.SaveCinemaData(newCinema);
        }

        [Route("api/Cinema/GetByCinemaId/{cinemaId}")]
        [HttpGet]

        public Cinema GetCinemaByCinemaId(int cinemaId)
        {
            return cinemaRepository.GetCinemaByCinemaId(cinemaId);
        }

        [Route("api/Cinema/GetCinemaByCompanyId/{companyId}")]
        [HttpGet]

        public IEnumerable<Cinema> GetCinemaByCompanyId(int companyId)
        {
            return cinemaRepository.GetCinemaByCompanyId(companyId);
        }

        [Route("api/Cinema/DeleteByCinemaId/{cinemaId}")]
        [HttpGet]
        public void DeleteCinemaByCinemaId(int cinemaId)
        {
            cinemaRepository.DeleteCinemaByCinemaId(cinemaId);
        }

        [Route("api/Cinema/DeleteByCompany/{companyId}")]
        [HttpGet]
        public void DeleteCinemaByCompanyId(int companyId)
        {
            cinemaRepository.DeleteCinemaByCompanyId(companyId);
        }
    }
}