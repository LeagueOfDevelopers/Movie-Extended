using Domain.Models.Entities;
using NHibernate.Mapping.ByCode;
using NHibernate.Mapping.ByCode.Conformist;

namespace Domain.Mappings
{
    public class LanguageMapping :ClassMapping<Language>
    {
        public LanguageMapping()
        {
            Id(model => model.Id, mapper =>
            {
                mapper.Column("Id");
                mapper.Generator(Generators.EnhancedTable);
            });
            Property(model => model.Name, mapper => mapper.Column("Name"));
            ManyToOne(model => model.Movie, mapper =>
            {
                mapper.Column("Movie");
                
            });
            ManyToOne(model => model.TrackFile, mapper =>
            {
                mapper.Column("TrackFile");
                
            });
        }
    }
}