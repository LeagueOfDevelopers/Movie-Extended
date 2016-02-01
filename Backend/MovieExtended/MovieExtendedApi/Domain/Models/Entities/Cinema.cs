namespace Domain.Models.Entities
{
    public class Cinema
    {
        public Cinema(int id, string name, string address, int companyId)
        {
            Id = id;
            Name = name;
            Address = address;
            CompanyId = companyId;
        }

        public Cinema(string name ,string address, int companyId)
        {
            Name = name;
            Address = address;
            CompanyId = companyId;
        }

        protected Cinema()
        {

        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string Address { get; protected set; }

        public virtual int CompanyId { get; set; }
    }
}
