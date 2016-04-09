using System.Collections.Generic;

namespace Domain.Models.FrontendEntities
{
    class FrontendCinema
    {
        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string Address { get; protected set; }

        public virtual ISet<FrontendMovie> Movie { get; set; }

    }
}
