using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    class TokenGenerator : ITokenGenerator
    {
        public Token Generate(Movie movie, Cinema cinema)
        {
            if (movie == null) throw new ArgumentNullException(nameof(movie));
            if (cinema == null) throw new ArgumentNullException(nameof(cinema));
            
            var tokenValue = CreateMd5(movie.Id.ToString() + 
                cinema.Id.ToString() + Constants.TokenGenerationSecret);

            return new Token(tokenValue);
        }

        public static string CreateMd5(string input)
        {
            // Use input string to calculate MD5 hash
            var md5 = MD5.Create();
            var inputBytes = Encoding.ASCII.GetBytes(input);
            var hashBytes = md5.ComputeHash(inputBytes);

            // Convert the byte array to hexadecimal string
            var sb = new StringBuilder();
            for (int i = 0; i < hashBytes.Length; i++)
            {
                sb.Append(hashBytes[i].ToString("X2"));
            }
            return sb.ToString();
        }


    }
}
