using CurrencyConverterWebServiceTests;
using Service.CurrencyConverterLogic;
using System.Net.Http;
using System.Web.Services;

namespace Service
{
    /// <summary>
    /// Summary description for WebService1
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class WebService1 : System.Web.Services.WebService
    {



        [WebMethod]
        public decimal ConvertCurrency(decimal amount, string from, string to)
        {

            IHttpClient httpClient = new HttpClientWrapper();
            CurrencyConverter currencyConverter = new CurrencyConverter();
            ReferenceRateProvider referenceRateProvider = new ReferenceRateProvider(httpClient);

            decimal fromRate = 0;
            decimal toRate = 0;

            fromRate = referenceRateProvider.GetRateForCurrency(from);
            toRate = referenceRateProvider.GetRateForCurrency(to);

            return currencyConverter.Convert(amount, fromRate, toRate);

        }
    }
}
