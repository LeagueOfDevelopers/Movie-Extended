using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    class CodeGenerator : ICodeGenerator
    {
        public string Generate(Token token)
        {
            if(token == null) throw new ArgumentNullException(nameof(token));

            return GetSha1(token.Value + Constants.CodeGenerationSecret);
        }

        private byte[] GetBytes(string input)
        {
            return Encoding.UTF8.GetBytes(input);
        }

        private string GetSha1(string input)
        {
            byte[] key = GetBytes(input);
            var sha1 = SHA1.Create();
            var hash = sha1.ComputeHash(key);
            var resultString = ConvertToString(hash);
            return resultString;
        }

        private string ConvertToString(byte[] input)
        {
            return Encoding.Default.GetString(input); 
        }
    }
}
