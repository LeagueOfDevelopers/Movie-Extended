namespace Domain.Models.Entities
{
    public class Cinema
    {
        public Cinema(int id, string name, string address, Company company)
        {
            Id = id;
            Name = name;
            Address = address;
            Company = company;
        }

        public Cinema(string name ,string address, Company company)
        {
            Name = name;
            Address = address;
            Company = company;
        }

        protected Cinema()
        {

        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string Address { get; protected set; }

        public virtual Company Company { get; set; }
    }
}
