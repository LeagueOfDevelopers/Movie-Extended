using System;
using System.Collections.Generic;

namespace Domain.Models.FrontendEntities
{
   public class FrontendCompany
    {
        public FrontendCompany(int id,string name , Uri website )
        {
            Id = id;
            Name = name;
            Website = website;
        }
        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Uri Website { get; protected set; }

        public virtual ISet<FrontendCinema> Cinema { get; set; }


    }
}
