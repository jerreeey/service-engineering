using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;

namespace Service.CurrencyConverterLogic
{
    public class CurrencyConverter
    {

        public decimal Convert(decimal amount, decimal fromRate, decimal toRate)
        {

            if (fromRate <= 0 || toRate <= 0)
            {
                throw new InvalidDataException("The rate cannot be 0 or negative.");
            }

            var exchamgeRate = decimal.Divide(toRate, fromRate);

            return decimal.Floor((exchamgeRate * amount) * 10000) / 10000;
        }

    }
}