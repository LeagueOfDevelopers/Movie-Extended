using System;

namespace Domain.Models
{
    public class Cinema
    {
        public Cinema(string id, string name, string address, string companyId)
        {
            Id = id;
            Name = name;
            Address = address;
            CompanyId = companyId;
        }

        protected Cinema()
        {

        }

        public virtual Guid? Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string Address { get; protected set; }

        public virtual Guid CompanyId { get; set; }
    }
}
