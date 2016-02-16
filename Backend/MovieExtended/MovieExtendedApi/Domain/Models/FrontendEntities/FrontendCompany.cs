using System;
using Domain.Models.Entities;

namespace Domain.Models.FrontendEntities
{
    public class FrontendCompany
    {
        public FrontendCompany(int id, string name, Uri website)
        {
            Id = id;
            Name = name;
            Website = website;
        }

        public FrontendCompany(string name, Uri website)
        {
            Name = name;
            Website = website;
        }

        protected FrontendCompany()
        {
        }

        public FrontendCompany(Company company)
        {
            Id = company.Id;
            Name = company.Name;
            Website = company.Website;
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Uri Website { get; protected set; }
    }
}