import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'https://app-carrental-230415231716.azurewebsites.net/cars';
class CarService {
 async getAvailableCars(pickUpDate,pickUpHour,returnDate,returnHour, currency) {
    return await axios.get(API_URL + '?pickupdate=' + pickUpDate + '&pickuphour=' + pickUpHour+ '&returndate=' + returnDate +'&returnhour=' + returnHour+'&currency='+currency, {
        headers: authHeader()
    }).then((response) => {
      return response.data})
  }
}

export default new CarService();