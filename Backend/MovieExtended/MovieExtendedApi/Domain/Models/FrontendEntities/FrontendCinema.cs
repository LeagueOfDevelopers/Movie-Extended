using System.Collections.Generic;

namespace Domain.Models.FrontendEntities
{
    public class FrontendCinema
    {
        public FrontendCinema(string name , string address)
        {
            Name = name;
            Address = address;
        }
        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string Address { get; protected set; }

        public virtual ISet<FrontendMovie> Movie { get; set; }

    }
}
