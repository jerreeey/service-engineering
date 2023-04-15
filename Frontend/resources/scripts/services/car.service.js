import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'https://6422e31877e7062b3e255693.mockapi.io/api/v1/cars';
class CarService {
  getAvailableCars(pickUpDate,pickUpHour,returnDate,returnHour, currency) {
    axios.get(API_URL + '?pickUpDate=' + pickUpDate + '?pickUpHour=' + pickUpHour+ '?returnDate=' + returnDate +'?returnHour=' + returnHour+'?currency='+currency, {
        headers: authHeader()
    }).then((response) => console.log(response.data))
  }
}

export default new CarService();