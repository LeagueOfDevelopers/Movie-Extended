

using Domain.Models.Entities;

namespace Domain.Models.FrontendEntities
{
    public class FrontendCinema
    {
        public FrontendCinema(int id, string name, string address, FrontendCompany company)
        {
            Id = id;
            Name = name;
            Address = address;
            Company = company;
        }

        public FrontendCinema(string name ,string address, FrontendCompany company)
        {
            Name = name;
            Address = address;
            Company = company;
        }

        protected FrontendCinema()
        {

        }

        public FrontendCinema(Cinema cinema)
        {
            Id = cinema.Id;
            Name = cinema.Name;
            Address = cinema.Address;
            Company=new FrontendCompany(cinema.Company);
        }
        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string Address { get; protected set; }

        public virtual FrontendCompany Company { get; set; }
    }
}
