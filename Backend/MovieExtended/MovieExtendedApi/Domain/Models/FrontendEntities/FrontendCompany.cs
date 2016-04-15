using System;
using System.Collections.Generic;

namespace Domain.Models.FrontendEntities
{
    class FrontendCompany
    {
        public FrontendCompany(string name , Uri website )
        {
            Name = name;
            Website = website;
        }
        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Uri Website { get; protected set; }

        public virtual ISet<FrontendCinema> Cinema { get; set; }


    }
}
